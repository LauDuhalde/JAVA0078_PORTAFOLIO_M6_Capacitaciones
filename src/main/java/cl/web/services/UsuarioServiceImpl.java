package cl.web.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.web.dto.EmpleadoCreateDTO;
import cl.web.dto.EmpleadoDTO;
import cl.web.dto.InstructorCreateDTO;
import cl.web.dto.InstructorDTO;
import cl.web.dto.UsuarioCreateDTO;
import cl.web.dto.UsuarioDTO;
import cl.web.entities.Usuario;
import cl.web.mappers.EmpleadoMapper;
import cl.web.mappers.InstructorMapper;
import cl.web.mappers.UsuarioMapper;
import cl.web.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper uMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmpleadoServiceImpl empleadoService;
    private final EmpleadoMapper eMapper;
    private final InstructorServiceImpl instructorService;
    private final InstructorMapper iMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              UsuarioMapper uMapper,
                              PasswordEncoder passwordEncoder,
                              EmpleadoServiceImpl empleadoService,
                              InstructorServiceImpl instructorService,
                              EmpleadoMapper eMapper,
                              InstructorMapper iMapper) {
        this.usuarioRepository = usuarioRepository;
        this.uMapper = uMapper;
        this.passwordEncoder = passwordEncoder;
		this.empleadoService = empleadoService;
		this.instructorService = instructorService;
		this.eMapper = eMapper;
		this.iMapper = iMapper;
		;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(authority)
        );
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
    	logger.debug("SERVICE CREAR USUARIO");
        Usuario usuario = uMapper.toEntity(dto);
        // Seguridad: siempre encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario guardado = usuarioRepository.save(usuario);
        logger.debug("ID Usuario guardado " + guardado.getId());
        
     // 2. Según rol crear empleado o instructor
        if (dto.getRol().equalsIgnoreCase("EMPLEADO") && dto.getEmpleado() != null) {
        	EmpleadoCreateDTO cDto = new EmpleadoCreateDTO();
        	cDto.setUsuarioId(guardado.getId());
        	cDto.setNombre(dto.getEmpleado().getNombre());
        	cDto.setApellido(dto.getEmpleado().getApellido());
        	cDto.setArea(dto.getEmpleado().getArea());
        	
            empleadoService.crearEmpleado(cDto);

        } else if (dto.getRol().equalsIgnoreCase("ADMIN") && dto.getInstructor() != null) {
        	InstructorCreateDTO cDto = new InstructorCreateDTO();
        	cDto.setUsuarioId(guardado.getId());
        	cDto.setNombre(dto.getInstructor().getNombre());
        	cDto.setApellido(dto.getInstructor().getApellido());
        	cDto.setEspecialidad(dto.getInstructor().getEspecialidad());
            
            instructorService.crearInstructor(cDto);
        }
        
        return uMapper.toDTO(guardado);
    }

    @Override
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return uMapper.toDTO(usuario);
    }

    @Override
    public UsuarioDTO obtenerPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return uMapper.toDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(uMapper::toDTO)
                .toList();
    }
}
