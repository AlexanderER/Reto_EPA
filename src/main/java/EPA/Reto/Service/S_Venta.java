package EPA.Reto.Service;

import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface S_Venta
{
    //------------------------------------------------------------ (Metodos Consola)
    void Crear_Vista_Ventas_Por_Producto();

    void Actualizar_Total_Ventas();


    //------------------------------------------------------------ (Metodos Web)
    List<M_Venta_Por_Producto> Obtener_Top_Productos_Vendidos(int p_iTop, Date p_dFecha_Inicio, Date p_dFecha_Fin);

    List<M_Venta_Por_Producto> Obtener_Total_Productos_Vendidos();

    Page<M_Venta> Obtener_Facturas_Por_Pagina(int p_iPagina, int p_iTotal_Por_Pagina);

    M_Venta Crear_Factura(M_Venta p_Venta);
}
