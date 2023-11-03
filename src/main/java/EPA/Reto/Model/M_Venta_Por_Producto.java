package EPA.Reto.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("CO_Ventas_Por_Producto")
public class M_Venta_Por_Producto
{
    //------------------------------------------------------------ (Variables)
    // No especifico la Etiqueta Id para que este sea autogenerado
    private String id;
    private String productName;
    private int    quantity;


    //------------------------------------------------------------ (Constructor)
    public M_Venta_Por_Producto()
    {
        super();
    }

}
