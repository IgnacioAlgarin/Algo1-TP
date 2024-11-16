package Test;

import java.util.Arrays;
import java.util.List;
import Tabla.Tabla;

public class TestConcatenar {
    public static void ejecutarPrueba(Tabla tabla1, Tabla tabla2) {
        System.out.println("Probando concatenar dos tablas.");

        // Datos de prueba
        List<Number> datosNumeros1 = Arrays.asList(1, 3, 5);
        List<Number> datosNumeros2 = Arrays.asList(2, 4, 6, 8);
        List<String> datosStrings1 = Arrays.asList("Prueba1", "Prueba2", "Prueba3");
        List<String> datosStrings2 = Arrays.asList("Prueba4", "Prueba5", "Prueba6", "Prueba7");
        List<Boolean> datosBooleans1 = Arrays.asList(true, false, true);
        List<Boolean> datosBooleans2 = Arrays.asList(false, true, false, false);
        // Agregamos columnas
        tabla1.agregarColumna(datosNumeros1, "Numeros");
        tabla1.agregarColumna(datosStrings1, "Strings");
        tabla1.agregarColumna(datosBooleans1, "Booleanos");
        tabla2.agregarColumna(datosNumeros2, "Numeros");
        tabla2.agregarColumna(datosStrings2, "Strings");
        tabla2.agregarColumna(datosBooleans2, "Booleanos");

        System.out.println("\nVisualizamos tabla1:");
        tabla1.visualizar();

        System.out.println("\nVisualizamos tabla2:");
        tabla2.visualizar();

        Tabla tabla_concatenada = tabla1.concatenar(tabla2);
        System.out.println("\nTabla concatenada:");
        tabla_concatenada.visualizar();

    }

}
