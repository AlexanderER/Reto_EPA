package EPA.Reto.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Document("CO_Ventas")
public class M_Venta
{
    //------------------------------------------------------------ (Variables)
    private String           id;

    @NotNull(message = "[Factura] [saleDate] Campo Requerido: Fecha Factura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date        saleDate;

    @Valid
    @NotEmpty(message = "[Factura] [items] Campo Vacío: La Factura debe contener al menos un Producto.")
    @NotNull(message = "[Factura] [items] Campo Requerido: La Factura debe contener al menos un Producto.")
    private List<M_Producto> items;

    @NotBlank(message = "[Factura] [storeLocation] Campo Requerido: Ubicación Tienda.")
    private String           storeLocation;

    @Valid
    @NotNull(message = "[Factura] [customer] Campo Requerido: La Factura debe poseer información del Cliente.")
    private M_Cliente        customer;

    @NotNull(message = "[Factura] [couponUsed] Campo Requerido: Utilizo Cupon.")
    private boolean          couponUsed;

    @NotBlank(message = "[Factura] [purchaseMethod] Campo Requerido: Metodo de Pago.")
    private String           purchaseMethod;

    private BigDecimal       total_Amount;   // Variable agregada para cumplir con el Punto 2 del Reto


    //------------------------------------------------------------(Constructor)
    public M_Venta(Date saleDate, List<M_Producto> items, String storeLocation, M_Cliente customer, boolean couponUsed, String purchaseMethod)
    {
        super();
        this.saleDate       = saleDate;
        this.items          = items;
        this.storeLocation  = storeLocation;
        this.customer       = customer;
        this.couponUsed     = couponUsed;
        this.purchaseMethod = purchaseMethod;
    }
}
