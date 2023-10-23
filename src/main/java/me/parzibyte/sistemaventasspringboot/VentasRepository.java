package me.parzibyte.sistemaventasspringboot;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface VentasRepository extends CrudRepository<Venta, Integer> {
  
}