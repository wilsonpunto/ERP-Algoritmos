package me.parzibyte.sistemaventasspringboot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Debes especificar el nombre")
    @Size(min = 1, max = 50, message = "El nombre debe medir entre 1 y 50")
    private String nombre;

    @NotNull(message = "Debes especificar el apellido")
    @Size(min = 1, max = 50, message = "El apellido debe medir entre 1 y 50")
    private String apellido;

    @NotNull(message = "Debes especificar el NIT")
    @Size(min = 1, max = 20, message = "El NIT debe medir entre 1 y 20")
    private String nit;

    @NotNull(message = "Debes especificar la fecha de registro")
    private Date fechaRegistro;

    public Cliente() {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nit = nit;
        this.fechaRegistro = new Date();
        this.id = id;
    }
   
  // Getter para el nombre
    public String getNombre() {
        return nombre;
    }

    // Setter para el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para el apellido
    public String getApellido() {
        return apellido;
    }

    // Setter para el apellido
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Getter para el NIT
    public String getNit() {
        return nit;
    }

    // Setter para el NIT
    public void setNit(String nit) {
        this.nit = nit;
    }

    // Getter para la fecha de registro
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    // Setter para la fecha de registro
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
 // Getter para el ID
 public Integer getId() {
    return id;
}

// Setter para el ID
public void setId(Integer id) {
    this.id = id;
}}
