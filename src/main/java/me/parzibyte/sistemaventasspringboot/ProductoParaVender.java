package me.parzibyte.sistemaventasspringboot;

public class ProductoParaVender extends Producto {
    // La cantidad del producto que se va a vender
    private Float cantidad;

    // Constructor con todos los campos, incluyendo el id del producto
    public ProductoParaVender(String nombre, String codigo, Float precio, Float existencia, Integer id, Float cantidad, String proveedor) {
        super(nombre, codigo, precio, existencia, id, proveedor);
        this.cantidad = cantidad;
    }

    // Constructor sin el id del producto
    public ProductoParaVender(String nombre, String codigo, Float precio, Float existencia, Float cantidad, String proveedor) {
        super(nombre, codigo, precio, existencia, proveedor);
        this.cantidad = cantidad;
    }

    // Método para incrementar la cantidad del producto en 1
    public void aumentarCantidad() {
        this.cantidad++;
    }

    // Método getter para obtener la cantidad del producto
    public Float getCantidad() {
        return cantidad;
    }

    // Método para obtener el total, que es el precio del producto multiplicado por la cantidad
    public Float getTotal() {
        return this.getPrecio() * this.cantidad;
    }
    
}