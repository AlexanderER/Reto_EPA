package EPA.Reto.Controller;

import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import EPA.Reto.Service.S_Venta;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Ventas")
public class C_Venta
{
    @Autowired
    S_Venta sVenta;

    @RequestMapping(method = RequestMethod.GET,
                    value = "")
    public String Prueba()
    {
        return "Genial funciona";
    }

    @GetMapping(value = "/Top_Productos_Vendidos/{p_iTop}/{p_sFechaInicio}/{p_sFechaFin}")
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

    @GetMapping(value = "/Total_Productos_Vendidos")
    public  List<M_Venta_Por_Producto> Total_Productos_Vendidos()
    {
        List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

        try
        {
            l_Venta_Por_Producto = sVenta.Obtener_Total_Productos_Vendidos();
        }
        catch (Exception ex)
        {
        }

        return l_Venta_Por_Producto;
    }


    @GetMapping(value = "/Pagina/{p_iPagina}/{p_iCantidad}")
    public List<M_Venta> Obtener_Pagina(@PathVariable int p_iPagina,
                                        @PathVariable int p_iCantidad)
    {
        // No puede ser pagina 0

        p_iPagina = p_iPagina - 1;

        Page<M_Venta> pagina = sVenta.Obtener_Facturas_Por_Pagina(p_iPagina, p_iCantidad);

        int iPagina =  pagina.getPageable().getPageNumber();
        int itamano =  pagina.getPageable().getPageSize();
        long iElementos =  pagina.getTotalElements();
        long totalPaginas = pagina.getTotalPages();

        // TotalPagina debe ser igual o menor a p_iCantidadOriginal

        return pagina.getContent();
    }

    @PostMapping(value = "/Crear")
    public void Crear_Factura(@RequestBody M_Venta p_Venta)
    {
        sVenta.Crear_Factura(p_Venta);
    }



}
