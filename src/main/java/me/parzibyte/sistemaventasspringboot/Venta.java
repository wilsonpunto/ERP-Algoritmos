package me.parzibyte.sistemaventasspringboot;

import javax.persistence.*;
import java.util.Set;

// La anotación @Entity indica que esta clase es una entidad JPA
@Entity
public class Venta {
    // La anotación @Id y @GeneratedValue indica que este campo es la clave primaria y su valor se generará automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fechaYHora;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
        

    // La anotación @OneToMany indica que hay una relación de uno a muchos entre Venta y ProductoVendido
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<ProductoVendido> productos;

    // Constructor predeterminado que inicializa la fecha y hora de la venta con el valor actual
    public Venta() {
        this.fechaYHora = Utiles.obtenerFechaYHoraActual();
    }

    // Método getter para obtener el id de la venta
    public Integer getId() {
        return id;
    }

    // Método setter para establecer el id de la venta
    public void setId(Integer id) {
        this.id = id;
    }

    // Método para calcular y devolver el total de la venta, que es la suma de los totales de todos los productos vendidos en esta venta
    public Float getTotal() {
        Float total = 0f;
        for (ProductoVendido productoVendido : this.productos) {
            total += productoVendido.getTotal();
        }
        return total;
    }

    // Método getter para obtener la fecha y hora de la venta
    public String getFechaYHora() {
        return fechaYHora;
    }

    // Método setter para establecer la fecha y hora de la venta
    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    // Método getter para obtener los productos vendidos en esta venta
    public Set<ProductoVendido> getProductos() {
        return productos;
    }

    // Método setter para establecer los productos vendidos en esta venta
    public void setProductos(Set<ProductoVendido> productos) {
        this.productos = productos;
    }
    
}