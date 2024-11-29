package tiendaRopaHombre.Alpha.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.models.Productos;
import tiendaRopaHombre.Alpha.service.ClienteService;
import tiendaRopaHombre.Alpha.service.ProductoService;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String home(Model model) {

        ArrayList<Productos> productos = productoService.obtenerProductos();
        System.out.println("Lista de productos: " + productos);

        model.addAttribute("productos", productos);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        // Retorna la vista login.html
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Añade un objeto cliente vacío al modelo para enlazar con el formulario
        model.addAttribute("cliente", new Cliente());
        return "crearCuenta";
    }

    @PostMapping("/registro")
    public String crearClienteFormulario(@ModelAttribute Cliente cliente, Model model) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        if (nuevoCliente != null) {
            model.addAttribute("mensajeExito", "Cliente registrado con éxito"); // Marca de éxito
        } else {
            model.addAttribute("mensajeError", "Hubo un problema al registrar el cliente"); // Marca de error
        }
        return "login"; // Retorna la vista del formulario
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }




}
