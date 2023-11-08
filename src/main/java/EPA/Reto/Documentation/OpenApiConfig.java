package EPA.Reto.Documentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig
{
    @Value("${EPA.Server.Desarrollo}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI()
    {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL del Servidor de Desarrollo");

        Contact contact = new Contact();
        contact.setEmail("info@epa.com");
        contact.setName("EPA");
        contact.setUrl("https://cr.epaenlinea.com/contact");

        //License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Documentación API´s Reto EPA")
                .version("1.0")
                .contact(contact)
                .description("Los metodos expuestos brindan para el Modulo de Ventas y su Reporteria.")//.termsOfService("https://www.bezkoder.com/terms")
                //.license(mitLicense);
                ;

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
