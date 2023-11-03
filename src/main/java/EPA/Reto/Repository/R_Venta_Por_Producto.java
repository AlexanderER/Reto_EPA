package EPA.Reto.Repository;

import EPA.Reto.Model.M_Venta_Por_Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface R_Venta_Por_Producto extends MongoRepository<M_Venta_Por_Producto, String>
{
}
