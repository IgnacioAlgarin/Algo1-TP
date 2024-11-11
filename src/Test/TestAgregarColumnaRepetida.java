package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestAgregarColumnaRepetida {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna con etiqueta repetida:");

        // Intento de agregar una columna con la misma etiqueta "Edad"
        List<Number> datosRepetidos = Arrays.asList(50, 60, 70, 80);
        tabla.agregarColumna(datosRepetidos, "Edad");
        System.out.println("Intento de agregar columna 'Edad' nuevamente.");

        // Visualizar la tabla para verificar el estado
        System.out.println("Estado actual de la tabla después de intentar agregar columna repetida:");
        tabla.visualizar();
    }
}
