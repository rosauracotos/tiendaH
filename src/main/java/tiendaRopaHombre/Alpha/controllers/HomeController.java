package tiendaRopaHombre.Alpha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tiendaRopaHombre.Alpha.models.Productos;
import tiendaRopaHombre.Alpha.service.ProductoService;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String home(Model model) {
        // Obtener la lista de productos
        ArrayList<Productos> productos = productoService.obtenerProductos();

        // Imprimir los productos en la consola para verificar
        System.out.println("Lista de productos: " + productos);

        // Agregar la lista de productos al modelo
        model.addAttribute("productos", productos);

        // Retornar la vista index.html
        return "index";
    }
}
