package EPA.Reto;

import EPA.Reto.Console.I_Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RetoApplication implements CommandLineRunner
{
	@Autowired
	I_Menu iMenu;

	public static void main(String[] args) {
		SpringApplication.run(RetoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		iMenu.Desplegar_Menu();
	}
}
