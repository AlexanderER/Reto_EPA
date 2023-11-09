package EPA.Reto.Console;

import EPA.Reto.Service.S_Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class I_Menu_Imp implements I_Menu
{
    @Autowired
    S_Venta sVenta;

    @Override
    public void Desplegar_Menu()
    {
        try
        {
            int iOpcion_indicada = 0;
            Scanner scanner      = new Scanner(System.in);

            while (true) // No doy la opcion de salida para mantenerlo siempre abierto
            {
                System.out.println("********************************************************************");
                System.out.println("******************************[ MENU ]******************************\n");
                System.out.println("   1) Crear o Actualizar Colección Ventas por Producto");
                System.out.println("   2) Crear o Actualizar Monto Total en las Ventas");
                System.out.println("\n********************************************************************");

                try
                {
                    iOpcion_indicada = scanner.nextInt();

                    if(iOpcion_indicada < 1 || iOpcion_indicada > 2)
                    {
                        System.out.println("   [ Alerta ] Favor indicar una opción válida.");
                        iOpcion_indicada = 0;
                    }
                }
                catch (Exception sx)
                {
                   scanner.nextLine();
                   System.out.println("   [ Alerta ] El valor indicado no es un número.");
                    iOpcion_indicada = 0;
                }

                //------------------------------------------------------------------------------------------------------------

                switch (iOpcion_indicada)
                {
                    case 1: sVenta.Crear_Vista_Ventas_Por_Producto(); break;
                    case 2: sVenta.Actualizar_Total_Ventas();         break;
                }
            } // Fin del Ciclo
        }
        catch (Exception ex)
        {
            System.out.println("Se genero un error inesperado en la ejecución del Aplicación de Consola" +
                               "\n" +
                               "Detalle del error: " + ex.getMessage());

            System.out.println("|||||||||||||||||||||||");
            System.out.println("|| Saliendo del Menu ||");
            System.out.println("|||||||||||||||||||||||");
        }
    }

}
