package tiendaRopaHombre.Alpha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendaRopaHombre.Alpha.models.Productos;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Integer> {
}
