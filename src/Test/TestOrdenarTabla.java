package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestOrdenarTabla {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando ordenar tabla por columnas 'Edad' y 'Nombre':");

        // Datos de prueba
        List<Number> datosEdades = Arrays.asList(30, 25, 40, 35);
        List<String> datosNombres = Arrays.asList("Carlos", "Ana", "Luis", "Juan");

        // Agregar columnas
        tabla.agregarColumna(datosEdades, "Edad");
        tabla.agregarColumna(datosNombres, "Nombre");

        System.out.println("Estado inicial de la tabla:");
        tabla.visualizar();

        // Ordenar por 'Edad' ascendente y 'Nombre' descendente
        List<String> columnas = Arrays.asList("Edad", "Nombre");
        List<Boolean> orden = Arrays.asList(true, false); // true = ascendente, false = descendente

        tabla.ordenarTabla(columnas, orden);
        System.out.println("Estado de la tabla después de ordenar por 'Edad' ascendente y 'Nombre' descendente:");
        tabla.visualizar();
    }
}
