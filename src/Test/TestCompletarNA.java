package Test;

import Tabla.Tabla;

public class TestCompletarNA {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando completar valores 'null' con 'NA' en la tabla:");

        // Muestra la tabla antes de reemplazar null
        System.out.println("Tabla antes de completar NA:");
        tabla.visualizar();

        // Reemplazar valores null con NA
        try {
            tabla.reemplazarNullConNA();
            System.out.println("Tabla después de completar NA:");
            tabla.visualizar();
        } catch (Exception e) {
            System.out.println("Error al completar valores NA: " + e.getMessage());
        }
    }
}
