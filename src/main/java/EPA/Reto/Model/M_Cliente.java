package EPA.Reto.Model;

import lombok.Data;

@Data
public class M_Cliente
{
    //------------------------------------------------------------ (Variables)
    private String gender;
    private int    age;
    private String email;
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
