package tiendaRopaHombre.Alpha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendaRopaHombre.Alpha.models.Carrito;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito , Integer> {
    Optional<Carrito> findByClienteIdAndProductoId(Integer clienteId, Integer productoId);
}
