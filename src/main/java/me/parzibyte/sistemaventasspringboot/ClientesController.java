package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Controlador para manejar las solicitudes relacionadas con los clientes
@Controller
@RequestMapping(path = "/clientes")
public class ClientesController {

    // Conexión de dependencias del repositorio de clientes
    @Autowired
    private ClientesRepository clientesRepository;

    // Método para manejar la solicitud GET a /clientes/agregar
    @GetMapping(value = "/agregar")
    public String agregarCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/agregar_cliente";
    }

    // Método para manejar la solicitud GET a /clientes/mostrar
    @GetMapping(value = "/mostrar")
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes/ver_clientes";
    }

    // Método para manejar la solicitud POST a /clientes/eliminar
    @PostMapping(value = "/eliminar")
    public String eliminarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Eliminado correctamente")
                .addFlashAttribute("clase", "warning");
        clientesRepository.deleteById(cliente.getId());
        return "redirect:/clientes/mostrar";
    }

    // Método para manejar la solicitud POST a /clientes/editar/{id}
    @PostMapping(value = "/editar/{id}")
    public String actualizarCliente(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (cliente.getId() != null) {
                return "clientes/editar_cliente";
            }
            return "redirect:/clientes/mostrar";
        }
        Cliente posibleClienteExistente = clientesRepository.findByNit(cliente.getNit());

        if (posibleClienteExistente != null && !posibleClienteExistente.getId().equals(cliente.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un cliente con ese NIT")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/clientes/agregar";
        }
        clientesRepository.save(cliente);
        redirectAttrs
                .addFlashAttribute("mensaje", "Editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/clientes/mostrar";
    }

    // Método para manejar la solicitud GET a /clientes/editar/{id}
    @GetMapping(value = "/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("cliente", clientesRepository.findById(id).orElse(null));
        return "clientes/editar_cliente";
    }

    // Método para manejar la solicitud POST a /clientes/agregar
    @PostMapping(value = "/agregar")
    public String guardarCliente(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "clientes/agregar_cliente";
        }
        if (clientesRepository.findByNit(cliente.getNit()) != null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un cliente con ese NIT")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/clientes/agregar";
        }
        clientesRepository.save(cliente);
        redirectAttrs
                .addFlashAttribute("mensaje", "Agregado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/clientes/agregar";
    }
}
