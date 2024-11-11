package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestEliminarColumna {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando eliminar columna de la tabla:");

        System.out.println("Estado inicial de la tabla:");
        tabla.visualizar();

        // Eliminar la columna con etiqueta "Edad"
        tabla.eliminarColumna("Edad");
        System.out.println("Columna 'Edad' eliminada.");

        // Visualizar la tabla para verificar que la columna se eliminó correctamente
        System.out.println("Estado de la tabla después de eliminar la columna:");
        tabla.visualizar();
    }
}
