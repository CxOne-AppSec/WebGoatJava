package com.example.WebGoatJava.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

@Controller
public class CookieController {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/setCookie")
    public ResponseEntity<String> setCookie(HttpServletResponse response) {
        try {
            Settings cookieVulnerable = new Settings("Cookie vulnerable a Deserealizacion");
            byte[] cookieData = serialize(cookieVulnerable);
            Cookie cookie = new Cookie("vulnerableCookie", new String(java.util.Base64.getEncoder().encode(cookieData)));
            response.addCookie(cookie);
            return new ResponseEntity(new Mensaje("Cookie establecida correctamente."), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(new Mensaje("Error al establecer la cookie."), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getCookie")
    public ResponseEntity<String> getCookie(@CookieValue(value = "vulnerableCookie", defaultValue = "") String cookieValue) {
        if (!cookieValue.isEmpty()) {
            try {
                byte[] cookieData = Base64.getDecoder().decode(cookieValue);
                Settings cookie = (Settings)deserialize(cookieData);
                String message = cookie.getMessage();
                return new ResponseEntity(new Mensaje("Esto es lo que tiene tu cookie: " + message), HttpStatus.OK);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity(new Mensaje("Error al deserializar la cookie."), HttpStatus.BAD_REQUEST);

            }
        } else {
            return new ResponseEntity(new Mensaje("La cookie no está presente."), HttpStatus.BAD_REQUEST);
        }
    }
    private byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(buffer)) {
            stream.writeObject(object);
        }
        return buffer.toByteArray();
    }

    private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream buffer = new ByteArrayInputStream(bytes);
        try (ObjectInputStream stream = new ObjectInputStream(buffer)) {
            return stream.readObject();
        }
    }

}
@Getter
@Setter
class Settings implements Serializable{
    private String message;
    public Settings(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

