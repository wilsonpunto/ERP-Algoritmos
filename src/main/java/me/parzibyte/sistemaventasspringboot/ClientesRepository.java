package me.parzibyte.sistemaventasspringboot;



import org.springframework.data.repository.CrudRepository;

public interface ClientesRepository extends CrudRepository<Cliente, Integer> {

    Cliente findByNit(String nit);
}
