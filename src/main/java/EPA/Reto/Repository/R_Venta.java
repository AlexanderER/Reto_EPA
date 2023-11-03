package EPA.Reto.Repository;

import EPA.Reto.Model.M_Venta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface R_Venta extends MongoRepository<M_Venta, String>
{
}
