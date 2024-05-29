package com.example.WebGoatJava.Dto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
public class dtoProducto {
    //El NotBlank sirve para que el atributo no vaya vacio
    @NotBlank
    private int precio;
    @Min(50)
    private String servicio;
    @NotBlank
    private String categoria;
    @NotBlank
    private String descripcion;
    private String foto;

    private String url;

    public dtoProducto() {
    }

    public dtoProducto(@Min(50) int precio, String servicio, String categoria, String descripcion, String foto, String url) {
        this.precio = precio;
        this.servicio = servicio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.foto = foto;
        this.url = url;
    }

    public String getUrl() { return url;}

    public void setUrl(String url) {this.url = url;}

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

