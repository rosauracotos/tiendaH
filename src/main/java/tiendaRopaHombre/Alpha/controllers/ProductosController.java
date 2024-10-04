package tiendaRopaHombre.Alpha.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tiendaRopaHombre.Alpha.models.Productos;
import tiendaRopaHombre.Alpha.service.ProductoService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/listar")
    @ResponseBody
    public ArrayList<Productos> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<String> crearProducto(@RequestBody Productos producto) {
        // El servicio se encargará de guardar tanto el producto como las tallas relacionadas
        Productos nuevoProducto = productoService.guardarProductoConTallas(producto);
        // Verifica si el producto fue creado exitosamente
        if (nuevoProducto != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public ResponseEntity<Productos> actualizaProducto(@RequestBody Productos productos, @PathVariable Integer id) {
        Productos productoActualizado = productoService.actualizarProducto(productos, id);

        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Productos> obtenerProductosPorId(@PathVariable("id") Integer id){
        return this.productoService.obtenerPorId(id);
    }


    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") Integer id) {
        boolean ok = this.productoService.eleminarProducto(id);
        if (ok) {
            return new ResponseEntity<>("Se eliminó el producto con id " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo eliminar el producto con id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        // Obtener la lista de productos
        ArrayList<Productos> productos = productoService.obtenerProductos();


        // Imprimir los productos en la consola
        System.out.println("Lista de productos: " + productos);


        // Agregar la lista al modelo para pasarla a la vista index.html
        model.addAttribute("productos", productos);

        // Retornar la vista index.html
        return "index";
    }

    @GetMapping("")
    public String mostrarProductos(Model model) {
        // Obtener la lista de productos
        ArrayList<Productos> productos = productoService.obtenerProductos();

        // Agregar la lista de productos al modelo para pasarla a la vista
        model.addAttribute("productos", productos);

        // Retornar la vista productos.html
        return "productos";
    }


    @GetMapping("/detalle/{id}")
    public String mostrarDetalleProducto(@PathVariable("id") Integer id, Model model) {
        Optional<Productos> productoOpt = productoService.obtenerPorId(id);
        if (productoOpt.isPresent()) {
            Productos producto = productoOpt.get();
            model.addAttribute("producto", producto);  // Pasamos el producto al modelo
            return "detalleProducto";  // Retorna la vista detalle-producto.html
        } else {
            return "error";  // Si no se encuentra el producto, retornamos una página de error
        }
    }


    @GetMapping(path = "/eliminar/{id}")
    public String eliminarPorIdPage(@PathVariable("id") Integer id) {
        this.productoService.eleminarProducto(id);
        return "redirect:/productos";
    }

    // Método para mostrar la página de actualización de productos
    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizarProducto(@PathVariable("id") Integer id, Model model) {
        Optional<Productos> productoOpt = productoService.obtenerPorId(id);
        if (productoOpt.isPresent()) {
            Productos producto = productoOpt.get();
            model.addAttribute("producto", producto);  // Añade el producto al modelo
            model.addAttribute("stockTallas", producto.getStockTallas());  // Añade la lista de tallas
            return "actualizarProducto";  // Retorna la vista del formulario de actualización
        } else {
            return "redirect:/productos";  // Redirige al listado de productos si no se encuentra el producto
        }
    }

    // Método para procesar la actualización del producto
    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@ModelAttribute Productos producto, @PathVariable Integer id) {
        productoService.actualizarProducto(producto, id); // Llama al servicio para actualizar el producto
        return "redirect:/productos"; // Redirige al listado de productos después de la actualización
    }


    // Método para mostrar el formulario de creación de un nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        Productos nuevoProducto = new Productos();
        nuevoProducto.setStockTallas(new ArrayList<>());  // Inicializamos la lista de tallas vacía
        model.addAttribute("producto", nuevoProducto);
        return "nuevoProducto";  // Retorna la vista nuevoProducto.html
    }

    // Método para procesar la creación de un nuevo producto
    @PostMapping("/nuevo")
    public String crearNuevoProducto(@ModelAttribute Productos producto) {
        productoService.guardarProductoConTallas(producto);
        return "redirect:/productos";


    }
}
