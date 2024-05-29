package com.example.WebGoatJava.Controller;

import com.example.WebGoatJava.Dto.dtoProducto;
import com.example.WebGoatJava.Entity.Producto;
import com.example.WebGoatJava.Entity.Usuario;
import com.example.WebGoatJava.JWT.JwtUtil;
import com.example.WebGoatJava.Service.ProductoService;
import com.example.WebGoatJava.Service.UsuarioService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
//Mapear las direcciones web(get, post, put, delate)
@RequestMapping(value = "/producto")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Origin", "Content-Type", "Accept", "Authorization"})
public class ProductoController {
        // private final static Logger logger = LoggerFactory.getLogger();
        @Autowired
        ProductoService serviceProducto;
        @Autowired
        UsuarioService serviceUsuario;
        @Value("${fotodirectory}")
        private String ruta;
        public String downloadPath = "C:/Users/appse/OneDrive/Desktop/WebGoatJava/Backend/WebGoatJava/src/main/webapp/WEB-INF/jsp";
        List<String> extensionesPermitidas = Arrays.asList("jpg", "jpeg", "png");
        @GetMapping("/lista")
        public ResponseEntity<List<Producto>> List() {
            List<Producto> list = serviceProducto.list();
            System.out.println("Peticion desde el front");
            return new ResponseEntity(list, HttpStatus.OK);
        }
         @GetMapping("/users")
        public ResponseEntity<List<Usuario>> User(@RequestHeader("Authorization") String tokenActual) {
             String token = tokenActual.replace("Bearer ", "");
             String rolUsuario = JwtUtil.extractRol(token);
             if("ROL_ADMIN".equals(rolUsuario)){
                List<Usuario> users = serviceUsuario.users();
                System.out.println("Peticion desde el front");
             return new ResponseEntity(users, HttpStatus.OK); }
             return new ResponseEntity(new Mensaje("No cuentas con permisos, para esta acción"), HttpStatus.BAD_REQUEST);
    }

        //responder mediante los detalles
        @GetMapping("/detail/{id}")
        public ResponseEntity<Producto> getById(@PathVariable("id") int id) {
            if (!serviceProducto.existsById(id)) {
                return new ResponseEntity(new Mensaje("El ID No existe"), HttpStatus.NOT_FOUND);
            }
            Producto producto = serviceProducto.getOne(id);
            return new ResponseEntity(producto, HttpStatus.OK);
        }

        @PostMapping(value = "/create", consumes = {"multipart/mixed", "multipart/form-data", "application/json", "text/plain", "application/x-www-form-urlencoded"})
        public ResponseEntity<?> create(@ModelAttribute dtoProducto dtoproducto, @RequestParam(name = "file", required = false) MultipartFile foto) {
            if (StringUtils.isEmpty(dtoproducto.getServicio())) {
                return new ResponseEntity(new Mensaje("El servicio es obligatorio"), HttpStatus.BAD_REQUEST); }
            if (dtoproducto.getPrecio() < 100) {
                return new ResponseEntity(new Mensaje("El precio mínimo en de $50"), HttpStatus.BAD_REQUEST); }
            if (serviceProducto.existsByServicio(dtoproducto.getServicio()))
                return new ResponseEntity<>(new Mensaje("El servicio ya existe"), HttpStatus.BAD_REQUEST);
            if (foto.getSize() > 2097152) {
                return new ResponseEntity<>(new Mensaje("El tamaño del archivo excede el límite permitido."), HttpStatus.BAD_REQUEST);
            }
            try {
                String originalFileName = foto.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFileName);
                if (!extensionesPermitidas.contains(extension.toLowerCase())) {
                    return new ResponseEntity(new Mensaje("Extensión de archivo no permitida."), HttpStatus.BAD_REQUEST);
                }
                 String nuevoNombre = UUID.randomUUID().toString() + "." + extension;
                 String filePath = ruta + "/" + nuevoNombre;
                 File dest = new File(filePath);
                 foto.transferTo(dest);
                 Producto producto = new Producto(dtoproducto.getPrecio(), dtoproducto.getServicio(), dtoproducto.getCategoria(), dtoproducto.getDescripcion(), dtoproducto.getFoto(), dtoproducto.getUrl());
                 producto.setFoto(nuevoNombre);
                 serviceProducto.save(producto);
                 return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al crear el producto " + e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @PutMapping("/update/{id}")
        public ResponseEntity<?> update(@PathVariable("id") int id, @ModelAttribute dtoProducto dtoproducto, @RequestParam(name = "file", required = false) MultipartFile foto) {
            if (!serviceProducto.existsById(id)){
                return new ResponseEntity(new Mensaje("El ID No existe"), HttpStatus.NOT_FOUND); }
            if (dtoproducto.getPrecio() < 100){
                return new ResponseEntity(new Mensaje("El precio mínimo en de $100"), HttpStatus.BAD_REQUEST); }
            try {
                String fileName = StringUtils.cleanPath(foto.getOriginalFilename());
                String filePath = ruta + "/" + fileName;
                File dest = new File(filePath);
                foto.transferTo(dest);
                Producto producto = serviceProducto.getOne(id);
                producto.setPrecio(dtoproducto.getPrecio());
                producto.setServicio(dtoproducto.getServicio());
                producto.setCategoria(dtoproducto.getCategoria());
                producto.setDescripcion(dtoproducto.getDescripcion());
                producto.setFoto(fileName);
                producto.setUrl(dtoproducto.getUrl());
                serviceProducto.update(producto);
                return new ResponseEntity(new Mensaje("Producto actualizado"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al actualizar el producto " + e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> delete(@RequestHeader("Authorization") String tokenActual, @PathVariable("id") int id) {
            String token = tokenActual.replace("Bearer ", "");
            String rolUsuario = JwtUtil.extractRol(token);
            if(!"ROL_ADMIN".equals(rolUsuario)){
                return new ResponseEntity(new Mensaje("No tienes permiso para eliminar productos"), HttpStatus.FORBIDDEN);
            }
            if (!serviceProducto.existsById(id))
                return new ResponseEntity(new Mensaje("El ID No existe"), HttpStatus.NOT_FOUND);
            serviceProducto.delete(id);
            return new ResponseEntity(new Mensaje("Se elimino el producto"), HttpStatus.OK);
        }
    }

