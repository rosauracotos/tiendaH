package tiendaRopaHombre.Alpha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendaRopaHombre.Alpha.models.Productos;
import tiendaRopaHombre.Alpha.models.StockTallas;

@Repository
public interface StockTallasRepository extends JpaRepository<StockTallas, Integer> {

    // Método personalizado para eliminar todas las tallas de un producto
    void deleteByProducto(Productos producto);
}
