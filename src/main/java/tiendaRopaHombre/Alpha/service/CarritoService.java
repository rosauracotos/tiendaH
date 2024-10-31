package tiendaRopaHombre.Alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaRopaHombre.Alpha.models.Carrito;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.repository.CarritoRepository;
import tiendaRopaHombre.Alpha.repository.ClienteRepository;
import tiendaRopaHombre.Alpha.repository.ProductoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public ArrayList<Carrito> obtenerCarrito(){
        return (ArrayList<Carrito>) carritoRepository.findAll();
    }


    public Carrito agregarProductoAlCarrito(Integer clienteId, Integer productoId, int cantidad) {
        // Verificar si el producto ya está en el carrito del cliente
        Optional<Carrito> carritoExistente = carritoRepository.findByClienteIdAndProductoId(clienteId, productoId);

        if (carritoExistente.isPresent()) {
            // Si existe, actualiza la cantidad
            Carrito carrito = carritoExistente.get();
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            return carritoRepository.save(carrito);
        } else {
            // Si no existe, crea una nueva entrada
            Carrito carrito = new Carrito();
            carrito.setCliente(clienteRepository.findById(clienteId).orElseThrow());
            carrito.setProducto(productoRepository.findById(productoId).orElseThrow());
            carrito.setCantidad(cantidad);
            carrito.setFechaRegistro(LocalDateTime.now());
            return carritoRepository.save(carrito);
        }
    }


    public void eliminarProductoDelCarrito(Integer clienteId, Integer productoId) {
        Carrito carrito = carritoRepository.findByClienteIdAndProductoId(clienteId, productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));
        carritoRepository.delete(carrito);
    }


}
