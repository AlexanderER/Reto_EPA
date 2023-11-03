package EPA.Reto.Model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class M_Producto
{
    //------------------------------------------------------------ (Variables)
    private String       name;
    private List<String> tags;
    private BigDecimal   price;
    private int          quantity;


    //------------------------------------------------------------(Constructor)
    public M_Producto(String name, List<String> tags, BigDecimal price, int quantity)
    {
        super();
        this.name     = name;
        this.tags     = tags;
        this.price    = price;
        this.quantity = quantity;
    }
}
