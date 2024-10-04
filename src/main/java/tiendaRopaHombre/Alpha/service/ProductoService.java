package tiendaRopaHombre.Alpha.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaRopaHombre.Alpha.models.Productos;
import tiendaRopaHombre.Alpha.models.StockTallas;
import tiendaRopaHombre.Alpha.repository.ProductoRepository;
import tiendaRopaHombre.Alpha.repository.StockTallasRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private StockTallasRepository stockTallasRepository;


    public ArrayList<Productos> obtenerProductos(){
        return (ArrayList<Productos>) productoRepository.findAll();
    }

    public Productos guardarProductoConTallas(Productos producto) {
        // Asegúrate de que la relación sea correcta antes de guardar
        if (producto.getStockTallas() != null) {
            for (StockTallas talla : producto.getStockTallas()) {
                talla.setProducto(producto);  // Establece la relación entre producto y tallas
            }
        }

        // Guarda el producto junto con las tallas
        return productoRepository.save(producto);
    }

    @Transactional
    public Productos actualizarProducto(Productos productos, Integer id){

        Optional<Productos> productosBDOpt = productoRepository.findById(id);
        if (productosBDOpt.isPresent()) {
            Productos productosBD = productosBDOpt.get();

            // Actualizamos los atributos del producto
            productosBD.setNombre(productos.getNombre());
            productosBD.setDescripcion(productos.getDescripcion());
            productosBD.setPrecio(productos.getPrecio());
            productosBD.setImagen_url(productos.getImagen_url());

            // Eliminar las tallas anteriores del producto
            stockTallasRepository.deleteByProducto(productosBD);  // Elimina todas las tallas asociadas al producto

            // Agregar las nuevas tallas
            if (productos.getStockTallas() != null) {
                for (StockTallas talla : productos.getStockTallas()) {
                    talla.setProducto(productosBD);  // Establecemos la relación correcta
                    productosBD.getStockTallas().add(talla);  // Añadimos las nuevas tallas
                }
            }

            // Guardar los cambios en la base de datos
            return productoRepository.save(productosBD);
        } else {
            return null;
        }

    }

    public Optional<Productos> obtenerPorId(Integer id){
        return productoRepository.findById(id);
    }


    public boolean eleminarProducto(Integer id){

        try{
            productoRepository.deleteById(id);
            return true;
        } catch (Exception err){
            return false;
        }


}

}
