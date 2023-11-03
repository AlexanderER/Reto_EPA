package EPA.Reto.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("CO_Ventas")
public class M_Venta
{
    //------------------------------------------------------------ (Variables)
    private String           id;
    private Date             saleDate;
    private List<M_Producto> items;
    private String           storeLocation;
    private M_Cliente        customer;
    private boolean          couponUsed;
    private String           purchaseMethod;


    //------------------------------------------------------------(Constructor)
    public M_Venta(String id, Date saleDate, List<M_Producto> items, String storeLocation, M_Cliente customer, boolean couponUsed, String purchaseMethod)
    {
        super();
        this.id             = id;
        this.saleDate       = saleDate;
        this.items          = items;
        this.storeLocation  = storeLocation;
        this.customer       = customer;
        this.couponUsed     = couponUsed;
        this.purchaseMethod = purchaseMethod;
    }
}