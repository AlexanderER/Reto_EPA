package EPA.Reto.Repository;

import EPA.Reto.Model.M_Venta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface R_Venta extends MongoRepository<M_Venta, String>
{
    // Metodo Personalizado para buscar facturas en un rango de Fecha
    List<M_Venta> findBySaleDateBetween(Date p_sFecha_Incio, Date p_sFecha_Fin);
}
