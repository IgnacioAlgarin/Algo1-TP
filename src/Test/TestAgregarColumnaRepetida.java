package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestAgregarColumnaRepetida {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna con etiqueta repetida:");

        // Datos iniciales para la primera columna
        List<Number> datosEdad = Arrays.asList(25, 30, 35, 40);
        tabla.agregarColumna(datosEdad, "Edad");
        System.out.println("Columna 'Edad' agregada.");

        // Intento de agregar una columna con la misma etiqueta "Edad"
        List<Number> datosRepetidos = Arrays.asList(50, 60, 70, 80);
        tabla.agregarColumna(datosRepetidos, "Edad");
        System.out.println("Intento de agregar columna 'Edad' nuevamente.");

        // Visualizar la tabla para verificar el estado
        System.out.println("Estado actual de la tabla después de intentar agregar columna repetida:");
        tabla.visualizar();
    }
}
