package EPA.Reto.Controller;

import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import EPA.Reto.Service.S_Venta;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class C_Venta
{
    @Autowired
    S_Venta sVenta;

    @RequestMapping(value = "Ventas")
    public String Prueba()
    {
        return "Genial funciona";
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "Ventas/Top_Productos_Vendidos/{p_iTop}/{p_sFechaInicio}/{p_sFechaFin}")
    public List<M_Venta_Por_Producto> Top_Productos_Vendidos(@PathVariable int p_iTop,
                                                             @PathVariable String p_sFechaInicio,
                                                             @PathVariable String p_sFechaFin)
    {
        List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

        try
        {
            // Defino el Formato de la Fecha
            SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            // Obtengo la Fecha con el Formato Date
            Date d_FechaInicio = sdf_FormatoFecha.parse(p_sFechaInicio);
            Date d_FechaFin    = sdf_FormatoFecha.parse(p_sFechaFin);

            l_Venta_Por_Producto = sVenta.Obtener_Top_Productos_Vendidos(p_iTop, d_FechaInicio, d_FechaFin);
        }
        catch (Exception ex)
        {
        }

        return l_Venta_Por_Producto;
    }





}
