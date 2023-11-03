package EPA.Reto.Service;

import EPA.Reto.Model.M_Producto;
import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Repository.R_Venta;
import EPA.Reto.Repository.R_Venta_Por_Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        try
        {
            System.out.println("...........................................................");
            System.out.println("[Creando Vista de Ventas Por Producto]");

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
            this.Guardar_Vista(Mapa_Ventas);

            System.out.println("Total de Facturas: " + l_Ventas.size());
            System.out.println("Total de Productos distintos: " + Mapa_Ventas.size());
            System.out.println("...........................................................");
        }
        catch (Exception ex)
        {
            System.out.println("Error Creando la Vista de Ventas Por Producto: " + ex.getMessage());
        }
    }

    private void Guardar_Vista(Map<String, Integer> p_Mapa)
    {
        try
        {
            //------------------------------------------------------------------------------------------- (Limpiar Vista BD)
            rVentaPorProducto.deleteAll();


            //------------------------------------------------------------------------------------------- (Guardar Vista BD)
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
            rVentaPorProducto.saveAll(l_Venta_Por_Producto);
            //-------------------------------------------------------------------------------------------
        }
        catch (Exception ex)
        {
            System.out.println("Error Guardando la Vista de Ventas Por Producto en BD: " + ex.getMessage());
        }
    }

}
