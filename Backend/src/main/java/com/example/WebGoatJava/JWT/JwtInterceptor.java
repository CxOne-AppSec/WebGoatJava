package com.example.WebGoatJava.JWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private final JwtUtil jwtUtil;
    private final static Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Autowired
    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Obtiene el token de autorización del encabezado
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", ""); // Quita "Bearer " del token

           if (token != null && jwtUtil.validateToken(token)) { //Lo comentamos para que no valide el token y logremos acceder modificando el token
                logger.info("El token existe y es valido");
                // Si el token es válido, permite la solicitud
                return true;
            }
        }
        // Si el token no es válido o no está presente, devuelve una respuesta no autorizada
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}


