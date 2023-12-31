package me.parzibyte.sistemaventasspringboot;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;

<<<<<<< HEAD
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
=======
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

>>>>>>> 5f2ad0bb1aa8421814968835f1fe1397dd136ea6
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
<<<<<<< HEAD
=======
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.springframework.http.MediaType;

>>>>>>> 5f2ad0bb1aa8421814968835f1fe1397dd136ea6

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;


@SuppressWarnings("ALL")
@Controller
@RequestMapping(path = "/ventas")
public class VentasController {

    
    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    ServletContext servletContext;

    

    @GetMapping(value = "/")
    public String mostrarVentas(Model model) {
        model.addAttribute("ventas", ventasRepository.findAll());
        return "ventas/ver_ventas";
    }
    @PostMapping(path = "/pdf")
<<<<<<< HEAD
    public String buscarProducto(@ModelAttribute Venta ventas, RedirectAttributes redirectAttrs,HttpServletRequest request, HttpServletResponse response) throws IOException {
=======
    public String buscarProducto(@ModelAttribute Venta venta, RedirectAttributes redirectAttrs,HttpServletRequest request, HttpServletResponse response) throws IOException {
>>>>>>> 5f2ad0bb1aa8421814968835f1fe1397dd136ea6
        int ventaId = Integer.parseInt(request.getParameter("id"));
        Optional<Venta> ventaOptional = ventasRepository.findById(ventaId);

if (ventaOptional.isPresent()) {
<<<<<<< HEAD

        Venta venta = ventasRepository.findById(ventaId).get();
        Set<ProductoVendido> productos = venta.getProductos();
        Cliente cliente = venta.getCliente();
        Float total = venta.getTotal();
        String fechayhora = venta.getFechaYHora();

        
        // Creacion de pdf por iText:
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfOutputStream));


    Document document = new Document(pdfDocument);



    Integer fechaYHora = null;
    for (ProductoVendido producto : productos) {
        document.add(new Paragraph("Producto: " + producto.getNombre()));
        document.add(new Paragraph("Cantidad: " + producto.getCantidad()));
        document.add(new Paragraph("Total: " + producto.getTotal()));
    }

    document.add(new Paragraph("Nit: " + cliente.getNit()));
    document.add(new Paragraph("Nombres: " + cliente.getNombre()));
    document.add(new Paragraph("Apellido    : " + cliente.getApellido()));
    document.add(new Paragraph(String.valueOf(total)));
    document.add(new Paragraph(fechayhora));


    // Cerrar el documento
    document.close();
=======
        Venta ventaFromDatabase = ventaOptional.get();
        
        // Aquí debes implementar la lógica para generar el PDF basado en la venta
        // Puedes usar iText u otra biblioteca para generar el PDF
        
        // Por ejemplo, si estás utilizando iText para crear un PDF simple:
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfOutputStream));
        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Factura de Venta"));
        // agregar toda la m aca
        document.close();
>>>>>>> 5f2ad0bb1aa8421814968835f1fe1397dd136ea6
        
        // Configurar la respuesta HTTP para devolver el PDF generado
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura.pdf");
        
        // Escribir el PDF en el flujo de salida de la respuesta
        ServletOutputStream servletOutputStream = response.getOutputStream();
        pdfOutputStream.writeTo(servletOutputStream);
        servletOutputStream.flush();
        
        return "redirect:/ventas"; // Redirige a la página de ventas después de generar el PDF
    } else {
        // Manejar el caso en el que no se encuentra la venta
        redirectAttrs.addFlashAttribute("mensajeError", "Venta no encontrada");
        return "redirect:/ventas";
    }
        


    }


}


    



