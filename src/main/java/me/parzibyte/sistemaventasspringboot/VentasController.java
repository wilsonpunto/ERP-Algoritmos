package me.parzibyte.sistemaventasspringboot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.springframework.http.MediaType;


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
    public String buscarProducto(@ModelAttribute Venta venta, RedirectAttributes redirectAttrs,HttpServletRequest request, HttpServletResponse response) throws IOException {
        int ventaId = Integer.parseInt(request.getParameter("id"));
        Optional<Venta> ventaOptional = ventasRepository.findById(ventaId);

if (ventaOptional.isPresent()) {
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


    



