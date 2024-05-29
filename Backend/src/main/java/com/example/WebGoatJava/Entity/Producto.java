package com.example.WebGoatJava.Entity;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    //los valores no deben ir en nulo por eso se declara el @NotNull
    @NotNull
    private int precio;
    @NotNull
    private String servicio;
    @NotNull
    private String categoria;
    @NotNull
    private String descripcion;
    private String foto;
    private String url;
    public Producto() {
    }
    public Producto(int precio, String servicio, String categoria, String descripcion, String foto, String url) {
        this.precio = precio;
        this.servicio = servicio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.foto = foto;
        this.url = url;
    }

    public String getUrl() {return url;}

    public void setUrl(String url) { this.url = url;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getFoto() {return foto;}
    public void setFoto(String foto) {this.foto = foto;}
}
