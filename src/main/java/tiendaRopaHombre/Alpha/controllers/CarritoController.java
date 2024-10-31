package tiendaRopaHombre.Alpha.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tiendaRopaHombre.Alpha.models.Carrito;
import tiendaRopaHombre.Alpha.service.CarritoService;

import java.util.ArrayList;

@Controller
@RequestMapping("/carrito")
public class CarritoController {


    @Autowired
    private CarritoService carritoService;

    @GetMapping("/listar")
    @ResponseBody
    public ArrayList<Carrito> obtenerCarrito() {
        return carritoService.obtenerCarrito();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarProducto(@RequestParam Integer clienteId, @RequestParam Integer productoId, @RequestParam int cantidad) {
        Carrito carrito = carritoService.agregarProductoAlCarrito(clienteId, productoId, cantidad);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(@RequestParam Integer clienteId, @RequestParam Integer productoId) {
        carritoService.eliminarProductoDelCarrito(clienteId, productoId);
        return ResponseEntity.noContent().build();
    }
}
