package EPA.Reto.Controller;

import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import EPA.Reto.Service.S_Venta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Ventas")
@Tag(name = "Facturas", description = "Metodos para el Manejo de Facturas")
public class C_Venta
{
    @Autowired
    S_Venta sVenta;


    //................................................................................................................................................................
    //................................................................................................................................................................

    @Operation(hidden = true)
    @RequestMapping(method = RequestMethod.GET,
                    value = "")
    public String Prueba()
    {
        return "Prueba Conexión";
    }

    //................................................................................................................................................................
    //................................................................................................................................................................

    @Operation(summary = "Top de Ventas por Producto",
               description = "Este metodo retorna una lista de los productos mas vendidos(desde 1 hasta 10 maximo) en un rango de Fechas.",
               responses = {
                            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = M_Venta_Por_Producto.class), mediaType = "application/json") }),
                            @ApiResponse(responseCode = "404",  content = { @Content(schema = @Schema(), mediaType = "String") })
                           })

    @GetMapping(value = "/Top_Productos_Vendidos/{p_iTop}/{p_sFechaInicio}/{p_sFechaFin}")
    public ResponseEntity<?> Top_Productos_Vendidos(@Parameter(description = "Numero de registros que desea visualizar (Desde 1 hasta máximo 10)", required = true) @PathVariable int p_iTop,
                                                    @Parameter(description = "Fecha Inicial de Busqueda. Formato: yyyy-MM-dd", required = true)                     @PathVariable String p_sFechaInicio,
                                                    @Parameter(description = "Fecha Final de Busqueda. Formato: yyyy-MM-dd", required = true)                       @PathVariable String p_sFechaFin)
    {
        List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

        try
        {
            // Defino el Formato de la Fecha
            SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");


            // Inicializo las variables de Fecha
            Date d_FechaInicio = new Date();
            Date d_FechaFin    = new Date();

            try
            {
                // Obtengo la Fecha con el Formato Date
                d_FechaInicio = sdf_FormatoFecha.parse(p_sFechaInicio);
                d_FechaFin    = sdf_FormatoFecha.parse(p_sFechaFin);
            }
            catch (Exception ex)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Formato o la Fecha indicada no es correcto" +
                                                                        "\nEl Formato correcto es yyyy-MM-dd");
            }

            // Efectuo algunas validaciones con respecto los datos recibidos
            if(p_iTop <= 0 || p_iTop > 10)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unicamente se permite consultar el producto más vendido o máximo los 10 productos más vendidos");
            }

            if(d_FechaInicio.compareTo(d_FechaFin) >= 0)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El parametro de la Fecha Fin debe ser Mayor a la Fecha de Inicio");
            }

            l_Venta_Por_Producto = sVenta.Obtener_Top_Productos_Vendidos(p_iTop, d_FechaInicio, d_FechaFin);
            return ResponseEntity.status(HttpStatus.OK).body(l_Venta_Por_Producto);
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error procesando la solicitud. " + ex.getMessage());
        }
    }

    //................................................................................................................................................................
    //................................................................................................................................................................

    @Operation(summary = "Obtiene la Lista Completa de Ventas por Producto",
               description = "Este metodo retorna la lista Completa de los productos mas vendidos.")
    @GetMapping(value = "/Total_Productos_Vendidos")
    public ResponseEntity<List<M_Venta_Por_Producto>> Total_Productos_Vendidos()
    {
        List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

        try
        {
            l_Venta_Por_Producto = sVenta.Obtener_Total_Productos_Vendidos();
        }
        catch (Exception ex)
        {
        }

        return ResponseEntity.ok(l_Venta_Por_Producto);
    }

    //................................................................................................................................................................
    //................................................................................................................................................................

    @Operation(summary = "Paginación Facturas",
               description = "Este metodo retorna una lista de Facturas de la Pagina y Cantidad por indicada",
               responses = {
                            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = M_Venta.class), mediaType = "application/json") }),
                            @ApiResponse(responseCode = "404",  content = { @Content(schema = @Schema(), mediaType = "String") })
                           })
    @GetMapping(value = "/Pagina/{p_iPagina}/{p_iCantidad}")
    public ResponseEntity<?> Obtener_Pagina(@Parameter(description = "Numero de Pagina a Consultar. Va desde 1 hasta N", required = true)       @PathVariable int p_iPagina,
                                            @Parameter(description = "Numero de Registros por Pagina. Debe ser como minimo 1", required = true) @PathVariable int p_iCantidad)
    {
        try
        {
            // No puede ser pagina 0
            if(p_iPagina <= 0)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El número de pagina indicado debe ser mayor a Cero.");
            }

            if(p_iCantidad <= 0)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Cantidad de Registros indicado debe ser mayor a Cero.");
            }

            // Como el indice de pagina trabaja con base cero le resto uno
            int iPaginaBuscada = p_iPagina - 1;

            Page<M_Venta> pagina = sVenta.Obtener_Facturas_Por_Pagina(iPaginaBuscada, p_iCantidad);

            int iPagina        =  pagina.getPageable().getPageNumber();
            int itamano        =  pagina.getPageable().getPageSize();
            long lElementos    =  pagina.getTotalElements();
            long lTotalPaginas = pagina.getTotalPages();

            // TotalPagina debe ser igual o menor a p_iCantidadOriginal
            if(p_iPagina <= lTotalPaginas)
            {
                return ResponseEntity.status(HttpStatus.OK).body(pagina.getContent());
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pagina indicada no existe" +
                        "\nTotal de Elementos: " + lElementos +
                        "\nTotal de Paginas: " + lTotalPaginas);
            }
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error procesando la solicitud. " + ex.getMessage());
        }
    }

    //................................................................................................................................................................
    //................................................................................................................................................................
    @Operation(summary = "Crear Factura",
               description = "Este metodo se encarga de Crear una nueva Factura en la BD")
    @PostMapping(value = "/Crear")
    public ResponseEntity<M_Venta> Crear_Factura(@RequestBody M_Venta p_Venta)
    {
        M_Venta venta_Creada = null;
        try
        {
            venta_Creada = sVenta.Crear_Factura(p_Venta);
        }
        catch (Exception ex)
        {
        }

        return ResponseEntity.ok(venta_Creada);
    }

    //................................................................................................................................................................
    //................................................................................................................................................................

}
