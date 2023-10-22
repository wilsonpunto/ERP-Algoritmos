package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;

// Controlador para manejar las solicitudes relacionadas con la venta de productos
@Controller
@RequestMapping(path = "/vender")
public class VenderController {
    // Inyección de dependencias de los repositorios
    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private VentasRepository ventasRepository;
    @Autowired
    private ProductosVendidosRepository productosVendidosRepository;
    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping(value = "/agregar_cliente")  
    public String agregarCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "redirect:/vender/";  
    }

    @GetMapping(value = "/mostrar_clientes")  
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "redirect:/vender/"; 
    }

    @PostMapping(value = "/eliminar_cliente")  
    public String eliminarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttrs) {
        redirectAttrs
                .addFlashAttribute("mensaje", "Eliminado correctamente")
                .addFlashAttribute("clase", "warning");
        clientesRepository.deleteById(cliente.getId());
        return "redirect:/vender/";  
    }
    @PostMapping(value = "/agregar_cliente")
    public String guardarCliente(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "redirect:/vender/";
        }
        if (clientesRepository.findByNit(cliente.getNit()) != null) {
                              
            return "redirect:/vender/";
        }
        clientesRepository.save(cliente);
        
        return "redirect:/vender/";
    }
  

    // Método para manejar la solicitud POST a /vender/quitar/{indice}
    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return "redirect:/vender/";
    }

    // Método para limpiar el carrito
    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
    }

    // Método para manejar la solicitud GET a /vender/limpiar
    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta cancelada")
                .addFlashAttribute("clase", "info");
        return "redirect:/vender/";
    }

    // Método para manejar la solicitud POST a /vender/terminar
    @PostMapping(value = "/terminar")
    public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        // Si no hay carrito o está vacío, regresamos inmediatamente
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/vender/";
        }
        Venta v = ventasRepository.save(new Venta());
        // Recorrer el carrito
        for (ProductoParaVender productoParaVender : carrito) {
            // Obtener el producto fresco desde la base de datos
            Producto p = productosRepository.findById(productoParaVender.getId()).orElse(null);
            if (p == null) continue; // Si es nulo o no existe, ignoramos el siguiente código con continue
            // Le restamos existencia
            p.restarExistencia(productoParaVender.getCantidad());
            // Lo guardamos con la existencia ya restada
            productosRepository.save(p);
            // Creamos un nuevo producto que será el que se guarda junto con la venta
            ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidad(), productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getCodigo(), v);
            // Y lo guardamos
            productosVendidosRepository.save(productoVendido);
        }

        // Al final limpiamos el carrito
        this.limpiarCarrito(request);
        // e indicamos una venta exitosa
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta realizada correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/vender/";
    }

    // Método para manejar la solicitud GET a /vender/
    @GetMapping(value = "/")
    public String interfazVender(Model model, HttpServletRequest request) {
        model.addAttribute("producto", new Producto());
        float total = 0;
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender p: carrito) total += p.getTotal();
        model.addAttribute("total", total);
        return "vender/vender";
    }

    // Método para obtener el carrito de la sesión
    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    // Método para guardar el carrito en la sesión
    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    // Método para manejar la solicitud POST a /vender/agregar
    @PostMapping(value = "/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        // Obtenemos el carrito de la sesión
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        // Buscamos el producto por su código
        Producto productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(producto.getCodigo());
        // Si el producto no existe, redirigimos al usuario con un mensaje de error
        if (productoBuscadoPorCodigo == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con el código " + producto.getCodigo() + " no existe")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        // Si el producto no tiene existencias, redirigimos al usuario con un mensaje de error
        if (productoBuscadoPorCodigo.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto está agotado")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        // Verificamos si el producto ya está en el carrito
        boolean encontrado = false;
        for (ProductoParaVender productoParaVenderActual : carrito) {
            // Si el producto ya está en el carrito, aumentamos la cantidad
            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }
        // Si el producto no está en el carrito, lo agregamos
        if (!encontrado) {
            carrito.add(new ProductoParaVender(productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(), productoBuscadoPorCodigo.getPrecio(), productoBuscadoPorCodigo.getExistencia(), productoBuscadoPorCodigo.getId(), 1f, null));
        }
        // Guardamos el carrito en la sesión
        this.guardarCarrito(carrito, request);
        // Redirigimos al usuario a la página de venta
        return "redirect:/vender/";
    }
}
