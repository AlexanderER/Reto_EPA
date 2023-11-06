package EPA.Reto.Service;

import EPA.Reto.Model.M_Producto;
import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class S_Venta_Imp implements S_Venta
{
    //------------------------------------------------------------ (Variables)
    @Autowired
    R_Venta rVenta;

    @Autowired
    R_Venta_Por_Producto rVentaPorProducto;

    //------------------------------------------------------------ (Metodos)
    @Override
    public void Crear_Vista_Ventas_Por_Producto()
    {
        System.out.println("..........................................................................");
        System.out.println("[Inicio Proceso: Creación Vista de Ventas Por Producto]");

        try
        {
            // Defino un Diccionario de Tipo Mapa
            Map<String, Integer> Mapa_Ventas = new HashMap<>();

            // Obtengo la Lista con todas las Ventas de la BD
            List<M_Venta> l_Ventas = rVenta.findAll();

            // Recorro la Lista de Facturas
            for (M_Venta venta : l_Ventas)
            {
                // Obtengo las Lista de Productos de esa Factura
                List<M_Producto> l_Productos = venta.getItems();

                // Recorro la la Lista de Productos por Factura
                for (M_Producto producto : l_Productos)
                {
                    // Obtengo el Nombre del Producto
                    String sNombre = producto.getName();

                    // Inserto o actualizo con el metodo Put acumulando uno(Representando una venta)
                    Mapa_Ventas.put(sNombre, Mapa_Ventas.getOrDefault(sNombre, 0) + 1);

                }   // Fin Ciclo Productos

            }   // Fin Ciclo Facturas

            // Guardar en BD el resultado
            this.Guardar_Vista(Mapa_Ventas, l_Ventas);
        }
        catch (Exception ex)
        {
            System.out.println("Error Creando la Vista de Ventas Por Producto: " + ex.getMessage());
        }

        System.out.println("[Fin Proceso: Creación Vista de Ventas Por Producto]");
        System.out.println("..........................................................................");
    }

    private void Guardar_Vista(Map<String, Integer> p_Mapa, List<M_Venta> p_lVentas)
    {
        try
        {
            // Limpiar Vista BD
            rVentaPorProducto.deleteAll();

            // Creo lista para almacenar los datos del Mapa
            List<M_Venta_Por_Producto> l_Venta_Por_Producto = new ArrayList<>();

            // Recorro el Mapa creado
            for(Map.Entry<String, Integer> row_Map : p_Mapa.entrySet())
            {
                // Inicializo un Objeto de la Vista
                M_Venta_Por_Producto venta_por_producto = new M_Venta_Por_Producto();

                // Seteo el Nombre y Cantidad de veces que ha sido vendido al Objeto de la Vista
                venta_por_producto.setProductName(row_Map.getKey());
                venta_por_producto.setQuantity(row_Map.getValue());

                // Agrego el Objeto a la lista
                l_Venta_Por_Producto.add(venta_por_producto);
            }

            // Guardo la lista en la BD
            List<M_Venta_Por_Producto> l_Inserted = rVentaPorProducto.saveAll(l_Venta_Por_Producto);

            // Imprimir Resultado
            //-----------------------------------------------------------------------------------------------------
            if(l_Inserted != null)
            {
                if(l_Inserted.size() == l_Venta_Por_Producto.size())
                {
                    System.out.println("Vista creada exitosamente." +
                            "\nTotal de Facturas: " + p_lVentas.size() +
                            "\nTotal de Productos distintos: " + l_Inserted.size());
                }
                else
                {
                    System.out.println("Vista Creada con Errores." +
                            "\nTotal de Facturas: " + p_lVentas.size() +
                            "\nTotal de Productos distintos: " + p_Mapa.size() +
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

}
