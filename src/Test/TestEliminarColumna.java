package Test;

import Tabla.Tabla;

public class TestEliminarColumna {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando eliminar columna 'Nombre':");

        try {
            // Intentamos eliminar la columna con etiqueta "Nombre"
            tabla.eliminarColumna("Nombre");
            System.out.println("Columna 'Nombre' eliminada exitosamente.");
            tabla.visualizar();
        } catch (Exception e) {
            System.out.println("Error al eliminar la columna: " + e.getMessage());
        }
    }
}
