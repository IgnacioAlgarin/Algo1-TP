package Test;
import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestCopiaProfunda {
    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("\nProbando copiar de manera profunda otra tabla.");

        //Datos de prueba
        List<Number> datosNumeros = Arrays.asList(2, 4, 6, 8);
        List<String> datosStrings = Arrays.asList("Prueba1", "Prueba2", "Prueba3", "Prueba4");
        List<Boolean> datosBooleans = Arrays.asList(true, false, true, true);

        //Agregamos columnas
        tabla.agregarColumna(datosNumeros, "Numeros");
        tabla.agregarColumna(datosStrings, "Strings");
        tabla.agregarColumna(datosBooleans, "Booleanos");

        System.out.println("\nVisualizamos nuestra tabla original");
        tabla.visualizar();

        //Copia profunda en una nueva tabla

        Tabla tabla_copia = tabla.copia_p();

        System.out.println("\nVisualizamos la copia profunda de nuestra tabla original");
        tabla_copia.visualizar();

        //Comprobamos con equals
        System.out.print("Comprobamos por referencia: ");
        System.out.println((tabla_copia == tabla));

        //Comprobamos con equals
        System.out.print("\nComprobamos por equals: ");
        System.out.println(tabla_copia.equals(tabla));




    }
}
