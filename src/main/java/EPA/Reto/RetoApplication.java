package EPA.Reto;

import EPA.Reto.Model.M_Venta;
import EPA.Reto.Repository.R_Venta;
import EPA.Reto.Service.S_Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class RetoApplication implements CommandLineRunner
{
	@Autowired
	S_Venta sVenta;

	public static void main(String[] args) {
		SpringApplication.run(RetoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		sVenta.Crear_Vista_Ventas_Por_Producto();
	}
}
