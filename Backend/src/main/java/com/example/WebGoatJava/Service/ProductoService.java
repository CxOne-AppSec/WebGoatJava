package com.example.WebGoatJava.Service;

import com.example.WebGoatJava.Entity.Producto;
import com.example.WebGoatJava.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//mantener la persistencia de los datos de la logica a la bd
@Transactional
public class ProductoService {
    @Autowired
    ProductoRepository iRepositoryProducto;

    public List<Producto> list(){
        return iRepositoryProducto.findAll();
    }
    //Buscar productos en particular

    //Buscar productos en particular
    public Producto getOne(int id){
        return iRepositoryProducto.getOne(id);
    }
    //Obtener producto por servicio
    public Producto getByServicio(String servicio){

        return iRepositoryProducto.getByServicio(servicio);
    }
    //guardar productos creados
    public void save(Producto producto){
        iRepositoryProducto.save(
                producto.getPrecio(),
                producto.getServicio(),
                producto.getCategoria(),
                producto.getDescripcion(),
                producto.getFoto(),
                producto.getUrl()
        );
    }
    public void update(Producto producto){
        iRepositoryProducto.update(
                producto.getPrecio(),
                producto.getServicio(),
                producto.getCategoria(),
                producto.getDescripcion(),
                producto.getFoto(),
                producto.getUrl(),
                producto.getId()
        );
    }
    //eliminar un producto
    public void delete(int id){
        iRepositoryProducto.delete(id);
    }
    //permite identificar si un producto existe atraves del id
    public boolean existsById(int id){
        return iRepositoryProducto.existsById(id) > 0;
    }
    //permite identificar un producto si existe atraves del servicio
    public boolean existsByServicio(String servicio){
        return iRepositoryProducto.existsByServicio(servicio) > 0;
    }
}

