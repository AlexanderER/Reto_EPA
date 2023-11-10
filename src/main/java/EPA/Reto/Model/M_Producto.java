package EPA.Reto.Model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;

@Data
public class M_Producto
{
    //------------------------------------------------------------ (Variables)
    @NotEmpty(message = "[Producto] [name] Campo requerido: Nombre del Producto")
    private String       name;

    // Permito que sea Nulo o Vacio
    private List<String> tags;

    @DecimalMin(value = "0.01", inclusive = true, message = "[Producto] [price] El Precio del Producto debe ser mayor a 0.01")
    @DecimalMax(value = "9999999", inclusive = true, message = "[Producto] [price] El Precio del Producto debe ser menor a 9999999")
    @Digits(integer = 7, fraction = 2, message = "[Producto] [price] El Formato del Precio debe ser 7 digitos enteros y 2 decimales")
    private BigDecimal   price;

    @NotNull(message = "[Producto] [quantity] Campo requerido: Cantidad")
    @Range(min = 1, max = 1000000, message = "[Producto] [quantity] La Cantidad del Producto debe estar entre 1 y 1 000 000") // Rango de tipo int 2147483647
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
