package tiendaRopaHombre.Alpha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.service.ClienteService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/listar")
    @ResponseBody
    public ArrayList<Cliente> obtenerCliente() {
        return clienteService.obtenerClientes();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
        // El servicio se encargará de guardar tanto el producto como las tallas relacionadas
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        // Verifica si el producto fue creado exitosamente
        if (nuevoCliente != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> actualizaCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
        Cliente clienteActualizado = clienteService.editarCliente(cliente, id);

        if (clienteActualizado != null) {
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detalle/{id}")
    @ResponseBody
    public Optional<Cliente> obtenerClientePorId(@PathVariable("id") Integer id) {
        return this.clienteService.obtenerPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") Integer id) {
        boolean ok = this.clienteService.eleminarCliente(id);
        if (ok) {
            return new ResponseEntity<>("Se eliminó el cliente con id " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo eliminar el cliente con id " + id, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/mantenimiento")
    public String listarClientes(Model model) {
        ArrayList<Cliente> clientes = clienteService.obtenerClientes();
        model.addAttribute("clientes", clientes);
        return "mantenimientoClientes"; // Nombre de la vista HTML para el mantenimiento de clientes
    }

    // Método para mostrar la página de actualización de clientes
    @GetMapping("/editar/{id}")
    public String mostrarFormularioActualizarCliente(@PathVariable("id") Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteService.obtenerPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            model.addAttribute("cliente", cliente);  // Añade el cliente al modelo
            return "editarCliente";  // Retorna la vista del formulario de actualización
        } else {
            return "redirect:/clientes/mantenimiento";  // Redirige al listado de clientes si no se encuentra el cliente
        }
    }

    // Método para procesar la actualización del cliente
    @PostMapping("/actualizar/{id}")
    public String actualizarCliente(@ModelAttribute Cliente cliente, @PathVariable Integer id) {
        clienteService.editarCliente(cliente, id); // Llama al servicio para actualizar el cliente
        return "redirect:/clientes/mantenimiento"; // Redirige al listado de clientes después de la actualización
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarClientePorIdPage(@PathVariable("id") Integer id) {
        clienteService.eleminarCliente(id); // Llama al servicio para eliminar el cliente
        return "redirect:/clientes/mantenimiento"; // Redirige al listado de clientes después de eliminar
    }













}
