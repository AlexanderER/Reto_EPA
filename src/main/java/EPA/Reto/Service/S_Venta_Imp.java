package EPA.Reto.Service;

import EPA.Reto.Model.M_Producto;
import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class S_Venta_Imp implements S_Venta
{
    //------------------------------------------------------------ (Variables)
    @Autowired
    R_Venta rVenta;

    @Autowired
    R_Venta_Por_Producto rVentaPorProducto;

    //------------------------------------------------------------ (Metodos Comunes)
    private List<M_Venta_Por_Producto> Generar_Lista_Ventas_Por_Producto(List<M_Venta> p_lVentas)
    {
        // Defino un Diccionario de Tipo Mapa
        Map<String, Integer> Mapa_Ventas = new HashMap<>();

        // Defino la Lista que retornare con el resultado Final
        List<M_Venta_Por_Producto> l_Lista_Final = new ArrayList<>();

        try
        {
            ////////////
            // Paso 1 //
            ////////////

            // Recorro la Lista de Facturas
            for (M_Venta venta : p_lVentas)
            {
                // Obtengo las Lista de Productos de esa Factura
                List<M_Producto> l_Productos = venta.getItems();

                // Recorro la la Lista de Productos por Factura
                for (M_Producto producto : l_Productos)
                {
                    // Obtengo el Nombre del Producto
                    String sNombre = producto.getName();

                    // Inserto o actualizo con el metodo Put acumulando uno(Representando una venta)
                    //Mapa_Ventas.put(sNombre, Mapa_Ventas.getOrDefault(sNombre, 0) + 1); // Comento ya que no es por apariciones sino por sumatoria de unidades
                    Mapa_Ventas.put(sNombre, Mapa_Ventas.getOrDefault(sNombre, 0) + producto.getQuantity());

                }   // Fin Ciclo Productos

            }   // Fin Ciclo Facturas



            ////////////
            // Paso 2 //
            ////////////

            // Recorro el Mapa creado
            for(Map.Entry<String, Integer> row_Map : Mapa_Ventas.entrySet())
            {
                // Inicializo un Objeto de la Vista
                M_Venta_Por_Producto venta_por_producto = new M_Venta_Por_Producto();

                // Seteo el Nombre y Cantidad de veces que ha sido vendido al Objeto de la Vista
                venta_por_producto.setProductName(row_Map.getKey());
                venta_por_producto.setQuantity(row_Map.getValue());

                // Agrego el Objeto a la lista
                l_Lista_Final.add(venta_por_producto);
            }


            ////////////
            // Paso 3 //
            ////////////

            // Ordeno la Lista de forma descendente por la Cantidad de veces que ha sido vendido
            Comparator<M_Venta_Por_Producto> comparadorPorCantidad = Comparator.comparing(M_Venta_Por_Producto::getQuantity).reversed();
            l_Lista_Final.sort(comparadorPorCantidad);
        }
        catch (Exception ex)
        {
            //throw new Exception("Error generando la Lista con cantidad de veces que ha sido vendido un producto. " + ex.getMessage());
        }

        return l_Lista_Final;
    }


    //------------------------------------------------------------ (Metodos Consola)
    @Override
    public void Crear_Vista_Ventas_Por_Producto()
    {
        System.out.println("..........................................................................");
        System.out.println("[Inicio Proceso: Creación Vista de Ventas Por Producto]");

        try
        {
            // Obtengo la Lista con todas las Ventas de la BD
            List<M_Venta> l_Ventas = rVenta.findAll();

            // Creo Variable que recibira la Lista con el resumen de las ventas
            List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

            l_Venta_Por_Producto = this.Generar_Lista_Ventas_Por_Producto(l_Ventas);

            // Guardar en BD el resultado
            this.Guardar_Vista(l_Ventas, l_Venta_Por_Producto);
        }
        catch (Exception ex)
        {
            System.out.println("Error Creando la Vista de Ventas Por Producto: " + ex.getMessage());
        }

        System.out.println("[Fin Proceso: Creación Vista de Ventas Por Producto]");
        System.out.println("..........................................................................");
    }

    private void Guardar_Vista(List<M_Venta> p_lVentas, List<M_Venta_Por_Producto> p_lVenta_Por_Producto)
    {
        try
        {
            // Limpiar Vista BD
            rVentaPorProducto.deleteAll();

            // Guardo la lista en la BD
            List<M_Venta_Por_Producto> l_Inserted = rVentaPorProducto.saveAll(p_lVenta_Por_Producto);

            // Imprimir Resultado
            //-----------------------------------------------------------------------------------------------------
            if(l_Inserted != null)
            {
                if(l_Inserted.size() == p_lVenta_Por_Producto.size())
                {
                    System.out.println("Vista creada exitosamente." +
                            "\nTotal de Facturas: " + p_lVentas.size() +
                            "\nTotal de Productos distintos: " + l_Inserted.size());
                }
                else
                {
                    System.out.println("Vista Creada con Errores." +
                            "\nTotal de Facturas: " + p_lVentas.size() +
                            "\nTotal de Productos distintos: " + p_lVenta_Por_Producto.size() +
                            "\nTotal de Productos insertados: " + l_Inserted.size());
                }
            }
            else
            {
                System.out.println("Error Creando la Vista de Ventas Por Producto.");
            }
            //-----------------------------------------------------------------------------------------------------
        }
        catch (Exception ex)
        {
            System.out.println("Error Guardando la Vista de Ventas Por Producto en BD: " + ex.getMessage());
        }
    }

    @Override
    public void Actualizar_Total_Ventas()
    {
        System.out.println("..........................................................................");
        System.out.println("[Inicio Proceso: Actualización Monto Total de la Coleccion de Ventas]");

        try
        {
            // Obtengo la Lista con todas las Ventas de la BD
            List<M_Venta> l_Ventas = rVenta.findAll();

            // Recorro la Lista de Facturas
            for (M_Venta venta : l_Ventas)
            {
                // Obtengo las Lista de Productos de esa Factura
                List<M_Producto> l_Productos = venta.getItems();

                BigDecimal bd_Total_Factura = BigDecimal.ZERO;

                // Recorro la la Lista de Productos por Factura
                for (M_Producto producto : l_Productos)
                {
                    int iCantidad        = producto.getQuantity();
                    BigDecimal bd_Precio = producto.getPrice();

                    // Acumulo el precio de la factura
                    bd_Total_Factura = bd_Total_Factura.add(bd_Precio.multiply(BigDecimal.valueOf(iCantidad)));

                }   // Fin Ciclo Productos

                // Seteo el Total Calculado en el campo requerido
                venta.setTotal_Amount(bd_Total_Factura);

            }   // Fin Ciclo Facturas

            // Guarda las facturas actualizadas en la base de datos
            List<M_Venta> l_Updated = rVenta.saveAll(l_Ventas);

            // Imprimir el resultado
            //-----------------------------------------------------------------------------------------------------
            if(l_Updated != null)
            {
                if(l_Ventas.size() == l_Updated.size())
                {
                    System.out.println("Actualización realizada exitosamente." +
                                       "\nTotal de Facturas: " + l_Ventas.size() +
                                       "\nFacturas actualizadas: " + l_Updated.size());
                }
                else
                {
                    System.out.println("Actualización realizada con Errores." +
                                       "\nTotal de Facturas: " + l_Ventas.size() +
                                       "\nFacturas actualizadas: " + l_Updated.size());
                }
            }
            else
            {
                System.out.println("Error Actualizando el Monto Total en la Coleccion de Ventas.");
            }
            //-----------------------------------------------------------------------------------------------------
        }
        catch (Exception ex)
        {
            System.out.println("Error Actualizando el Monto Total en la Coleccion de Ventas. " + ex.getMessage());
        }

        System.out.println("[Fin Proceso: Actualización Monto Total de la Coleccion de Ventas]");
        System.out.println("..........................................................................");
    }


    //------------------------------------------------------------ (Metodos Web)
    @Override
    public List<M_Venta_Por_Producto> Obtener_Top_Productos_Vendidos(int p_iTop, Date p_dFecha_Inicio, Date p_dFecha_Fin)
    {
        // Creo Variable que retornara el Top de los Productos
        List<M_Venta_Por_Producto> l_Top_Productos = new ArrayList<>();

        try
        {
            // Creo Variable que recibira la Lista con el resumen de las ventas
            List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

            // Obtengo la Lista con todas las Ventas de la BD entre un rango de Fechas
            List<M_Venta> l_Ventas = rVenta.findBySaleDateBetween(p_dFecha_Inicio, p_dFecha_Fin);

            l_Venta_Por_Producto = this.Generar_Lista_Ventas_Por_Producto(l_Ventas);

            // Valido que el Top N, no sea mayor a la cantidad de la lista.
            // En ese caso setea como top el numero de elementos de la lista.
            if(p_iTop > l_Venta_Por_Producto.size())
            {
                p_iTop = l_Venta_Por_Producto.size();
            }

            // Guardo el Top de Productos en la lista que sera retornada
            l_Top_Productos = l_Venta_Por_Producto.subList(0, p_iTop);
        }
        catch (Exception ex)
        {
            System.out.println("Error Generando el Top de veces que ha sido vendido un producto: " + ex.getMessage());
        }

        return l_Top_Productos;
    }

    @Override
    public List<M_Venta_Por_Producto> Obtener_Total_Productos_Vendidos()
    {
        // Creo Variable que retornara el Top de los Productos
        List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

        try
        {
            // Recreo la Vista en caso de que hayan cambios en ventas para reflejar los mismos
            this.Crear_Vista_Ventas_Por_Producto();

            // Obtengo la Lista de la Vista con todos los registros
            l_Venta_Por_Producto = rVentaPorProducto.findAll();
        }
        catch (Exception ex)
        {
            System.out.println("Error Generando lista Completa de veces que ha sido vendido un producto. " + ex.getMessage());
        }

        return l_Venta_Por_Producto;
    }

    @Override
    public Page<M_Venta> Obtener_Facturas_Por_Pagina(int p_iPagina, int p_iTotal_Por_Pagina)
    {
        Page<M_Venta> pPagina = null;

        try
        {
            pPagina = rVenta.findAll(PageRequest.of(p_iPagina, p_iTotal_Por_Pagina));
        }
        catch (Exception ex)
        {
            System.out.println("Error Generando la pagina solicitada. " + ex.getMessage());
        }

        return  pPagina;
    }

    @Override
    public M_Venta Crear_Factura(M_Venta p_Venta)
    {
        M_Venta venta_Creada = null;

        try
        {
            venta_Creada = rVenta.save(p_Venta);
        }
        catch (Exception ex)
        {
            System.out.println("Error Creando la Factura. " + ex.getMessage());
        }

        return venta_Creada;
    }


}
