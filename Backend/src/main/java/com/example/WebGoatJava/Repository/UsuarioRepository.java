package com.example.WebGoatJava.Repository;

import com.example.WebGoatJava.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT * FROM usuario WHERE nombre_usuario = ?1", nativeQuery = true)
        //@Query(value = "SELECT * FROM usuario WHERE nombre_usuario = ?1", nativeQuery = true)
    Usuario findByNombreUsuario(String nombreUsuario);
    @Query(value = "SELECT * FROM usuario", nativeQuery = true)
    List<Usuario> findusers();
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE nombre_usuario = ?1", nativeQuery = true)
    long existeNombreUsuario(String nombreUsuario);
    @Query(value = "SELECT * FROM usuario WHERE id = ?1", nativeQuery = true)
    Usuario getOne(int id);
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE email = ?1", nativeQuery = true)
    long existeEmail(String email);
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE respuesta = ?1", nativeQuery = true)
    long existeRespuesta(String respuesta);
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE pregunta = ?1", nativeQuery = true)
    long existePregunta(String pregunta);
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE id = ?1", nativeQuery = true)
    long existsById(int id);
    @Modifying
    @Query(value = "INSERT INTO usuario (nombre, nombre_usuario, email, password, pregunta, respuesta, rol) VALUES (?1,?2,?3,?4,?5,?6,?7)", nativeQuery = true)
    void save(String nombre, String nombre_usuario, String email, String password,String pregunta, String respuesta, String rol);
    @Modifying
    @Query(value = "UPDATE usuario SET password = ?1 WHERE id = ?2", nativeQuery = true)
    void cambiarpass(String password, int id);

}