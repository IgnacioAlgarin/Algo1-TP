package Test;

import Tabla.Tabla;

public class TestEliminarFila {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando eliminar fila con índice 0:");

        try {
            // Intentamos eliminar la fila con el índice 0
            tabla.eliminarFila(0);
            System.out.println("Fila con índice 0 eliminada exitosamente.");
            tabla.visualizar();
        } catch (Exception e) {
            System.out.println("Error al eliminar la fila: " + e.getMessage());
        }
    }
}
