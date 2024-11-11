package Test;

import Tabla.Tabla;
import java.util.List;
import java.util.Arrays;

public class TestAgregarFila {
    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar nuevas filas.");

        // Datos de prueba
        List<Number> datosNumeros = Arrays.asList(1, 3, 5);
        List<String> datosStrings = Arrays.asList("Prueba1", "Prueba2", "Prueba3");
        List<Boolean> datosBooleans = Arrays.asList(true, false, true);
        List<Object> datosFila1 = Arrays.asList(7, "Prueba4", null);
        List<Object> datosFila2 = Arrays.asList(9, null, true);

        // Agregamos columnas
        tabla.agregarColumna(datosNumeros, "Numeros");
        tabla.agregarColumna(datosStrings, "Strings");
        tabla.agregarColumna(datosBooleans, "Booleanos");

        System.out.println("Visualizamos tabla");
        tabla.visualizar();

        // Agregamos filas
        tabla.agregarfila("Fila 4", datosFila1);
        tabla.agregarfila(datosFila2);

        System.out.println("Visualizamos tabla despues de agregar 2 nuevas filas");
        tabla.visualizar();

    }

}
