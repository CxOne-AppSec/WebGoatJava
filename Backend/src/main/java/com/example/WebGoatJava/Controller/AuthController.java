package com.example.WebGoatJava.Controller;

import com.example.WebGoatJava.Dto.JwtDto;
import com.example.WebGoatJava.Dto.UserRowMapper;
import com.example.WebGoatJava.Entity.Usuario;
import com.example.WebGoatJava.JWT.JwtUtil;
import com.example.WebGoatJava.Service.EmailService;
import com.example.WebGoatJava.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Map;

import static com.example.WebGoatJava.JWT.JwtUtil.extractUser;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Origin", "Content-Type", "Accept", "Authorization"})
public class AuthController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MD5hashUtil MD5HashUtil;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emaelService;

    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody Usuario loginUsuario) {
        if (loginUsuario.getNombreUsuario().isEmpty() || loginUsuario.getPassword().isEmpty()) {
            return new ResponseEntity(new Mensaje("Los campos no pueden ser nulos"), HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = MD5HashUtil.getMD5Hash(loginUsuario.getPassword());
        String query = "SELECT * FROM usuario WHERE nombre_usuario='" + loginUsuario.getNombreUsuario() + "' AND password='" + hashedPassword + "'";
        try{
                Usuario users = (Usuario) jdbcTemplate.queryForObject(query, new UserRowMapper());
                String jwt = jwtUtil.generateToken(users);
                JwtDto jwtDto = new JwtDto(jwt);
                logger.info("Inicio de sesión exitoso");
                return new ResponseEntity<>(jwtDto, HttpStatus.OK);
            } catch (EmptyResultDataAccessException ex){
            return new ResponseEntity(new Mensaje("Los campos incorrectos"), HttpStatus.BAD_REQUEST);
        }
    }
  @PostMapping("/nuevo")
    public ResponseEntity<?>nuevo(@RequestBody Usuario usuario){
      if (usuarioService.existsByNombreUsuario(usuario.getNombreUsuario()))
         return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
      if (usuarioService.existsByEmail(usuario.getEmail()))
          return new ResponseEntity(new Mensaje("El Email ya existe"), HttpStatus.BAD_REQUEST);
      Usuario nuevousuario =
              new Usuario(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(),
                      MD5hashUtil.getMD5Hash(usuario.getPassword()), usuario.getPregunta(), usuario.getRespuesta(), usuario.getRol());
      if(nuevousuario.getRol() == null || nuevousuario.getRol().isEmpty()){
         nuevousuario.setRol("ROL_USER"); }
      usuarioService.save(nuevousuario);
      return new ResponseEntity(new Mensaje("Usuario Creado"), HttpStatus.CREATED);
  }
  @PostMapping("/renovar")
    public ResponseEntity<?>renovar(@RequestBody Usuario usuario){
      try {
      if (usuarioService.existsByPregunta(usuario.getPregunta())) {
          if (usuarioService.existsByRespuesta(usuario.getRespuesta())) {
              Usuario user = usuarioService.getByNombreUsuario(usuario.getNombreUsuario());
              if (user != null) {
                  user.setPassword(MD5hashUtil.getMD5Hash(usuario.getPassword()));
                  usuarioService.cambiarpass(user);
                  return new ResponseEntity(new Mensaje("contraseña actualizada"), HttpStatus.OK); }
             }
          return new ResponseEntity(new Mensaje("Campos incorrectos"), HttpStatus.BAD_REQUEST);
         }
      } catch (EmptyResultDataAccessException e) {
              return new ResponseEntity(new Mensaje("Error al renovar la contraseña"), HttpStatus.INTERNAL_SERVER_ERROR); }
      return null;
  }
    @PostMapping("/cambiarpass")
    public ResponseEntity<?>actualizar(@RequestHeader("Authorization") String tokenActual, @RequestBody Map<String, String> request){

        String password = request.get("password");
        String nuevaContrasena = request.get("nuevaPassword");
        String token = tokenActual.replace("Bearer ", "");
        String username = extractUser(token);
        Usuario user = usuarioService.getByNombreUsuario(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            user.setPassword(passwordEncoder.encode(nuevaContrasena));
            usuarioService.cambiarpass(user);
            return new ResponseEntity(new Mensaje("contraseña actualizada"), HttpStatus.OK);
        }
        return new ResponseEntity(new Mensaje("No se logro actualizar, intentelo de nuevo"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(@RequestHeader("Authorization") String tokenActual) {
        String token = tokenActual.replace("Bearer ", "");
        String username = extractUser(token);
        Usuario usuario = usuarioService.getByNombreUsuario(username);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

}
