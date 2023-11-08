package EPA.Reto;

import EPA.Reto.Model.M_Cliente;
import EPA.Reto.Model.M_Producto;
import EPA.Reto.Model.M_Venta;
import EPA.Reto.Model.M_Venta_Por_Producto;
import EPA.Reto.Service.S_Venta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class RetoApplicationTests {

	@Autowired
	S_Venta sVenta;

	@Test
	@DisplayName("Prueba: Lista Completa")
	void Test_Obtener_Total_Prouctos_Vendidos()
	{
		System.out.println("Iniciando Prueba: Test_Obtener_Total_Prouctos_Vendidos");

		List<M_Venta_Por_Producto> l_Resultado = new ArrayList<>();
		l_Resultado = sVenta.Obtener_Total_Productos_Vendidos();

		assert(!l_Resultado.isEmpty());
	}

	@Test
	@DisplayName("Prueba: Top 5")
	void Test_Top5_Prouctos_Vendidos()
	{
		System.out.println("Iniciando Prueba: Test_Top5_Prouctos_Vendidos");

		int iCantidad = 5;
		String sFechaInicio = "1980-1-1";
		String sFechaFin    = "2023-1-1";

		SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date dFecha_Inicio = null;
		Date dFecha_Fin    = null;

		try
		{
			dFecha_Inicio = sdf_FormatoFecha.parse(sFechaInicio);
			dFecha_Fin = sdf_FormatoFecha.parse(sFechaFin);
		}
		catch (Exception ex)
		{
		}

		List<M_Venta_Por_Producto> l_Resultado = new ArrayList<>();
		l_Resultado = sVenta.Obtener_Top_Productos_Vendidos(iCantidad, dFecha_Inicio, dFecha_Fin);

		assert(l_Resultado.size() == 5);
	}

	@Test
	@DisplayName("Prueba: Top Productos Vendidos (Error Formato Fecha de Inicio)")
	void Test_Top_Prouctos_Vendidos_Error_Fecha_Inicio()
	{
		System.out.println("Iniciando Prueba: Test_Top_Prouctos_Vendidos_Error_Fecha_Inicio");

		int iCantidad = 5;
		String sFechaInicio = "1980.1.1";
		String sFechaFin    = "2023-1-1";

		SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date dFecha_Inicio = null;
		Date dFecha_Fin    = null;

		try
		{
			dFecha_Inicio = sdf_FormatoFecha.parse(sFechaInicio);
			dFecha_Fin = sdf_FormatoFecha.parse(sFechaFin);
		}
		catch (Exception ex)
		{
		}

		List<M_Venta_Por_Producto> l_Resultado = new ArrayList<>();
		l_Resultado = sVenta.Obtener_Top_Productos_Vendidos(iCantidad, dFecha_Inicio, dFecha_Fin);

		assert(l_Resultado.isEmpty());
	}

	@Test
	@DisplayName("Prueba: Top Productos Vendidos (Error Formato Fecha de Fin)")
	void Test_Top_Prouctos_Vendidos_Error_Fecha_Fin()
	{
		System.out.println("Iniciando Prueba: Test_Top_Prouctos_Vendidos_Error_Fecha_Fin");

		int iCantidad = 5;
		String sFechaInicio = "1980-1-1";
		String sFechaFin    = "2023.1.1";

		SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date dFecha_Inicio = null;
		Date dFecha_Fin    = null;

		try
		{
			dFecha_Inicio = sdf_FormatoFecha.parse(sFechaInicio);
			dFecha_Fin = sdf_FormatoFecha.parse(sFechaFin);
		}
		catch (Exception ex)
		{
		}

		List<M_Venta_Por_Producto> l_Resultado = new ArrayList<>();
		l_Resultado = sVenta.Obtener_Top_Productos_Vendidos(iCantidad, dFecha_Inicio, dFecha_Fin);

		assert(l_Resultado.isEmpty());
	}

	@Test
	@DisplayName("Prueba: Top Productos Vendidos (Error Rango Incorrecto)")
	void Test_Top_Prouctos_Vendidos_Error_Rango_Incorrecto()
	{
		System.out.println("Iniciando Prueba: Test_Top_Prouctos_Vendidos_Error_Rango_Incorrecto");

		int iCantidad       = 5;
		String sFechaInicio = "2023-12-31";
		String sFechaFin    = "2023-1-1";

		SimpleDateFormat sdf_FormatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date dFecha_Inicio = null;
		Date dFecha_Fin    = null;

		try
		{
			dFecha_Inicio = sdf_FormatoFecha.parse(sFechaInicio);
			dFecha_Fin = sdf_FormatoFecha.parse(sFechaFin);
		}
		catch (Exception ex)
		{
		}

		List<M_Venta_Por_Producto> l_Resultado = new ArrayList<>();
		l_Resultado = sVenta.Obtener_Top_Productos_Vendidos(iCantidad, dFecha_Inicio, dFecha_Fin);

		assert(l_Resultado.isEmpty());
	}

	@Test
	@DisplayName("Prueba: Obtener la primer Pagina de Facturas")
	void Test_Obtener_Primera_Pagina_Facturas()
	{
		System.out.println("Iniciando Prueba: Test_Obtener_Primera_Pagina_Facturas");

		int iPagina   = 0;
		int iCantidad = 10;

		Page<M_Venta> pagina = sVenta.Obtener_Facturas_Por_Pagina(iPagina, iCantidad);

		int iPaginaObtenida =  pagina.getPageable().getPageNumber();
		int itamano         =  pagina.getPageable().getPageSize();
		long lElementos     =  pagina.getTotalElements();
		long lTotalPaginas  = pagina.getTotalPages();

		assert (!pagina.isEmpty());
		assert (iPagina == iPaginaObtenida);
		assert (iCantidad == pagina.getContent().size());
	}

	@Test
	@DisplayName("Prueba: Obtener Pagina de Facturas fuera de Rango")
	void Test_Obtener_Pagina_Facturas_Fuera_De_Rango()
	{
		System.out.println("Iniciando Prueba: Test_Obtener_Pagina_Facturas_Fuera_De_Rango");

		int iPagina   = 9999;
		int iCantidad = 100;

		Page<M_Venta> pagina = sVenta.Obtener_Facturas_Por_Pagina(iPagina, iCantidad);

		int iPaginaObtenida =  pagina.getPageable().getPageNumber();
		int itamano         =  pagina.getPageable().getPageSize();
		long lElementos     =  pagina.getTotalElements();
		long lTotalPaginas  = pagina.getTotalPages();

		assert (pagina.getContent().isEmpty());
	}

	@Test
	@DisplayName("Prueba: Crear Factura")
	void Test_Creacion_Factura()
	{
		System.out.println("Iniciando Prueba: Test_Creacion_Factura");

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

		M_Venta factura_Creada = sVenta.Crear_Factura(factura);


		assert (!factura_Creada.getId().isEmpty());
	}

}
