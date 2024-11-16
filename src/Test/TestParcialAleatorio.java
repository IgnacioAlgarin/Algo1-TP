package Test;

import Tabla.Tabla;
import java.util.List;
import java.util.Arrays;

public class TestParcialAleatorio {
    public static void ejecutarPrueba(Tabla tabla) {

        System.out.println("\nProbando visualizar tabla.");
        // Datos de prueba
        List<Number> datosNumeros1 = Arrays.asList(null, 3, 5, 7, 9, 11, 13, 15);
        List<String> datosStrings1 = Arrays.asList("Prueba1", null, "Prueba3", "Prueba4", "Prueba5", "Prueba6",
                "Prueba7", "Prueba8");
        List<Boolean> datosBooleans1 = Arrays.asList(true, false, true, null, false, true, true, null);
        List<Number> datosNumeros2 = Arrays.asList(2, 4, 6, 8, null, 12, 14, 16);
        List<String> datosStrings2 = Arrays.asList("Test1", "Test2", "Test3", "Test4", null, "Test6", "Test7", null);
        List<Boolean> datosBooleans2 = Arrays.asList(false, true, false, false, true, false, null, true);
        List<String> etiqFilas = Arrays.asList("Fila 1", "Fila 2", "Fila 3", "Fila 4", "Fila 5", "Fila 6", "Fila 7",
                "Fila 8");
        List<String> etiqFila = Arrays.asList("Fila 2", "Fila 3", "Fila 6", "Fila 8");
        List<String> etiqColumna = Arrays.asList("Strings2", "Bool1", "Numeros1");
        // Agregamos columnas
        tabla.agregarColumna(datosNumeros1, "Numeros1", etiqFilas);
        tabla.agregarColumna(datosStrings1, "Strings1");
        tabla.agregarColumna(datosBooleans1, "Bool1");
        tabla.agregarColumna(datosNumeros2, "Numeros2");
        tabla.agregarColumna(datosStrings2, "Strings2");
        tabla.agregarColumna(datosBooleans2, "Bool2");

        System.out.println("\nVisualizamos tabla completa.");
        tabla.visualizar();

        System.out.println("Visualizamos tabla de manera parcial.");
        tabla.visualizarParcial(etiqFila, etiqColumna);

        System.out.println("Visualizamos tabla de manera aleatoria.");
        tabla.visualizarAleatorio(25);

        System.out.println("Visualizamos las primeras 3 filas.");
        tabla.head(3);

        System.out.println("Visualizamos las ultimas 2 filas.");
        tabla.tail(2);

    }
}
