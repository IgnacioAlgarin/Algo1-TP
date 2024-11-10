package Test;

import Tabla.Tabla;
import java.util.List;

public class TestAccesoIndexado {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando acceso indexado:");

        // Acceder a una fila completa
        System.out.print("Accediendo a la fila con etiqueta 'mundo': ");
        try {
            List<Object> datosFila = tabla.obtenerFilaPorEtiqueta("mundo");
            System.out.println(datosFila);
        } catch (Exception e) {
            System.out.println("Error al acceder a la fila: " + e.getMessage());
        }

        // Acceder a una columna completa
        System.out.print("Accediendo a la columna 'Edad': ");
        try {
            List<?> datosColumna = tabla.obtenerColumnaPorEtiquetaIndex("Edad");
            System.out.println(datosColumna);
        } catch (Exception e) {
            System.out.println("Error al acceder a la columna: " + e.getMessage());
        }

        // Acceder a una celda específica
        System.out.print("Accediendo a la celda ('mundo', 'Edad'): ");
        try {
            Object datoCelda = tabla.obtenerCelda("mundo", "Edad");
            System.out.println(datoCelda);
        } catch (Exception e) {
            System.out.println("Error al acceder a la celda: " + e.getMessage());
        }
    }
}
