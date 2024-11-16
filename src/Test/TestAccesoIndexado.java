package Test;

import Tabla.Tabla;
import java.util.List;

public class TestAccesoIndexado {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("\nProbando acceso indexado a filas, columnas y celdas:");
        System.out.println("Tabla: \n");
        tabla.visualizar();
        
        // Acceso a una fila completa
        System.out.println("\nAcceso a la fila con etiqueta 'mundo':");
        List<Object> datosFila = tabla.obtenerFilaPorEtiqueta("mundo");
        System.out.println("Fila 'mundo': " + datosFila);

        // Acceso a una columna completa
        System.out.println("\nAcceso a la columna con etiqueta 'Edad':");
        List<?> datosColumna = tabla.obtenerColumnaPorEtiquetaIndex("Edad");
        System.out.println("Columna 'Edad': " + datosColumna);

        // Acceso a una celda específica
        System.out.println("\nAcceso a la celda en la intersección de 'mundo' y 'Edad':");
        Object datoCelda = tabla.obtenerCelda("mundo", "Edad");
        System.out.println("Celda ('mundo', 'Edad'): " + datoCelda);
    }
}
