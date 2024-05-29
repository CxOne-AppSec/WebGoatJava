package com.example.WebGoatJava.Dto;

import com.example.WebGoatJava.Entity.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario user = new Usuario();
        user.setId(rs.getInt("id"));
        user.setNombre(rs.getString("nombre"));
        user.setNombreUsuario(rs.getString("nombre_usuario"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPregunta(rs.getString("pregunta"));
        user.setRespuesta(rs.getString("respuesta"));
        user.setRol(rs.getString("rol"));
        // Map other properties if available in the result set
        return user;
    }
}