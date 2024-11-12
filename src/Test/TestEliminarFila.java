package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestEliminarFila {

    public static void ejecutarPrueba(Tabla tabla) {
        int id = 3;
        System.out.println("Probando eliminar fila de la tabla mediante su ID " + id);

        System.out.println("Estado inicial de la tabla:");
        tabla.visualizar();

        tabla.eliminarFila(id);

        // Visualizar la tabla para verificar que la fila se eliminó correctamente
        System.out.println("Estado de la tabla después de eliminar la fila:");
        tabla.visualizar();
    }
}
