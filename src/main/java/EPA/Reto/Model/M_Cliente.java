package EPA.Reto.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class M_Cliente
{
    //------------------------------------------------------------ (Variables)
    @NotEmpty(message = "[Cliente] [gender] Campo requerido: Género")
    private String gender;

    @NotNull(message = "[Cliente] [age] Campo requerido: Edad")
    @Range(min = 1, max = 100, message = "[Cliente] [age] El Rango de Edad debe ser entre 1 y 100")
    private int    age;

    @NotEmpty(message = "[Cliente] [email] Campo requerido: Correo")
    @Email(message = "[Cliente] [email] El Formato de Correo no es correcto")
    private String email;

    @NotNull(message = "[Cliente] [satisfaction] Campo requerido: Nivel Satisfacción")
    @Range(min = 1, max = 10, message = "[Cliente] [satisfaction] El Nivel de Satisfacción debe ser entre 1 y 10")
    private int    satisfaction;


    //------------------------------------------------------------(Constructor)
    public M_Cliente(String gender, int age, String email, int satisfaction)
    {
        super();
        this.gender       = gender;
        this.age          = age;
        this.email        = email;
        this.satisfaction = satisfaction;
    }
}
