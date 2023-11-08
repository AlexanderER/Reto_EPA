package EPA.Reto;

import EPA.Reto.Model.M_Cliente;
import EPA.Reto.Model.M_Producto;
import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Enpoints_Test
{
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void Test_Guardar_Factura()
    {
        System.out.println("Iniciando Prueba Web: Test_Guardar_Factura" +
                           "\nhttp://localhost:8085/Ventas/Crear");

        List<String> l_tags = new ArrayList<>();
        l_tags.add("a");
        l_tags.add("b");

        List<M_Producto> l_productos = new ArrayList<>();
        M_Producto producto_a = new M_Producto("A", l_tags, new BigDecimal("1.12"), 21);
        M_Producto producto_b = new M_Producto("B", l_tags, new BigDecimal("2.12"), 31);

        l_productos.add(producto_a);
        l_productos.add(producto_b);

        M_Venta factura = new M_Venta(new Date(),
                l_productos ,
                "Costa Rica",
                new M_Cliente("M", 30, "alexer1515@gmail.com", 10),
                false,
                "Efectivo"
        );

        ResponseEntity<M_Venta> respuesta = testRestTemplate.postForEntity("http://localhost:8085/Ventas/Crear", factura, M_Venta.class);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        M_Venta venta_Creada = respuesta.getBody();

        assertNotNull(venta_Creada);
        assert (!venta_Creada.getId().isEmpty());
    }

    @Test
    @Order(2)
    void Listar_Total_Ventas_Por_Producto()
    {
        System.out.println("Iniciando Prueba Web: Listar_Total_Ventas_Por_Producto" +
                           "\nhttp://localhost:8085/Total_Productos_Vendidos");

        ResponseEntity<M_Venta_Por_Producto[]> respuesta = testRestTemplate.getForEntity("http://localhost:8085/Ventas/Total_Productos_Vendidos", M_Venta_Por_Producto[].class);
        List<M_Venta_Por_Producto> l_Productos = Arrays.asList(respuesta.getBody());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assert (!l_Productos.isEmpty());
    }

    @Test
    @Order(3)
    void Listar_Top_Ventas_Por_Producto()
    {
        System.out.println("Iniciando Prueba Web: Listar_Top_Ventas_Por_Producto" +
                           "\nhttp://localhost:8085/Top_Productos_Vendidos/{p_iTop}/{p_sFechaInicio}/{p_sFechaFin}");

        int iCantidad       = 5;
        String sFechaInicio = "1980-1-1";
        String sFechaFin    = "2023-12-1";

        ResponseEntity<M_Venta_Por_Producto[]> respuesta = testRestTemplate.getForEntity("http://localhost:8085/Ventas/Top_Productos_Vendidos/" + iCantidad + "/" + sFechaInicio + "/" + sFechaFin, M_Venta_Por_Producto[].class);
        List<M_Venta_Por_Producto> l_Productos = Arrays.asList(respuesta.getBody());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assert (!l_Productos.isEmpty());
        assert(l_Productos.size() == 5);
    }

    @Test
    @Order(4)
    void Obtener_Pagina()
    {
        System.out.println("Iniciando Prueba Web: Obtener_Pagina" +
                "\nhttp://localhost:8085//Pagina/{p_iPagina}/{p_iCantidad}");

        int iPagina   = 1;
        int iCantidad = 10;

        ResponseEntity<M_Venta[]> respuesta = testRestTemplate.getForEntity("http://localhost:8085/Ventas/Pagina/" + iPagina + "/" + iCantidad, M_Venta[].class);
        List<M_Venta> l_Productos = Arrays.asList(respuesta.getBody());


        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assert (!l_Productos.isEmpty());
        assert(l_Productos.size() == 10);
    }


}
