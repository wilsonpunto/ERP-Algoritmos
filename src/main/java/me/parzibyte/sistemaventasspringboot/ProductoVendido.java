package me.parzibyte.sistemaventasspringboot;

import javax.persistence.*;

// indica que esta clase es una entidad JPA
@Entity
public class ProductoVendido {
    // La anotación @Id y @GeneratedValue indica que este campo es la clave primaria y su valor se generará automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float cantidad, precio;
    private String nombre, codigo;
    // La anotación @ManyToOne y @JoinColumn indica que hay una relación de muchos a uno entre ProductoVendido y Venta
    @ManyToOne
    @JoinColumn
    private Venta venta;

    // Constructor con todos los campos
    public ProductoVendido(Float cantidad, Float precio, String nombre, String codigo, Venta venta) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombre = nombre;
        this.codigo = codigo;
        this.venta = venta;
    }

    // Constructor predeterminado
    public ProductoVendido() {
    }

    // Método para calcular y devolver el total, que es la cantidad del producto multiplicada por su precio
    public Float getTotal() {
        return this.cantidad * this.precio;
    }

    // Método getter para obtener la venta a la que pertenece este producto vendido
    public Venta getVenta() {
        return venta;
    }

    // Método setter para establecer la venta a la que pertenece este producto vendido
    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    // Método getter para obtener el precio del producto vendido
    public Float getPrecio() {
        return precio;
    }

    // Método setter para establecer el precio del producto vendido
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    // Método getter para obtener la cantidad del producto vendido
    public Float getCantidad() {
        return cantidad;
    }

    // Método setter para establecer la cantidad del producto vendido
    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    // Método getter para obtener el nombre del producto vendido
    public String getNombre() {
        return nombre;
    }

    // Método setter para establecer el nombre del producto vendido
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método getter para obtener el código del producto vendido
    public String getCodigo() {
        return codigo;
    }

    // Método setter para establecer el código del producto vendido
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}