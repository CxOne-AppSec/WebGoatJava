package com.example.WebGoatJava.Repository;

import com.example.WebGoatJava.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    @Query( value = "SELECT * FROM producto WHERE servicio = ?1", nativeQuery = true)
    Producto getByServicio(String servicio);
    @Modifying
    @Query(value = "INSERT INTO producto (precio, servicio, categoria, descripcion, foto, url) VALUES (?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    void save(int precio, String servicio, String categoria, String descripcion, String foto, String url);

    @Modifying
    @Query(value = "UPDATE producto SET precio = ?1, servicio = ?2, categoria = ?3, descripcion = ?4, foto = ?5, url = ?6 WHERE id = ?7", nativeQuery = true)
    void update(int precio, String servicio, String categoria, String descripcion, String foto, String url, int id);

    // Busca el ID
    @Modifying
    @Query(value = "DELETE FROM producto WHERE id = ?1", nativeQuery = true)
    void delete(int id);
    @Query(value = "SELECT * FROM producto WHERE id = ?1", nativeQuery = true)
    Producto getOne(int id);

    @Query(value = "SELECT * FROM producto", nativeQuery = true)
    List<Producto> findAll();


    @Query(value = "SELECT COUNT(*) FROM producto WHERE id = ?1", nativeQuery = true)
    long existsById(int id);

    //mètodo que verifica si existe un producto apartir del servicio
    @Query(value = "SELECT COUNT(*) FROM producto WHERE servicio = ?1", nativeQuery = true)
    long existsByServicio(String servicio);
}