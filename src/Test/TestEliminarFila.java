package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestEliminarFila {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando eliminar fila de la tabla:");

        System.out.println("Estado inicial de la tabla:");
        tabla.visualizar();

        // Eliminar la fila en el índice 1
        tabla.eliminarFila(1);

        // Visualizar la tabla para verificar que la fila se eliminó correctamente
        System.out.println("Estado de la tabla después de eliminar la fila:");
        tabla.visualizar();
    }
}
