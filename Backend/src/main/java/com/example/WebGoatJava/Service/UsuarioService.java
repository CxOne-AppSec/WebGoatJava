package com.example.WebGoatJava.Service;

import com.example.WebGoatJava.Entity.Usuario;
import com.example.WebGoatJava.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public boolean existsById(int id){
        return usuarioRepository.existsById(id) > 0;
    }
    public Usuario getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    public List<Usuario> users(){
        return usuarioRepository.findusers();
    }
    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existeNombreUsuario(nombreUsuario) > 0;
    }
    public Usuario getOne(int id){
        return usuarioRepository.getOne(id);
    }
    public boolean existsByEmail(String email){
        return usuarioRepository.existeEmail(email) > 0;
    }
    /*public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }*/
    public void save(Usuario usuario) {
        usuarioRepository.save(
                usuario.getNombre(),
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getPregunta(),
                usuario.getRespuesta(),
                usuario.getRol()
       );
    }
    public boolean existsByPregunta(String pregunta){ return usuarioRepository.existePregunta(pregunta) > 0; }

    public boolean existsByRespuesta(String respuesta){ return usuarioRepository.existeRespuesta(respuesta) > 0; }
    public void cambiarpass (Usuario usuario){
        usuarioRepository.cambiarpass(usuario.getPassword(), usuario.getId()); }
}
