package Test;

import Tabla.Tabla;

public class TestCompletarNA {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando completar NA y rellenar datos faltantes:");
        System.out.println("Tabla inicial:\n");
        tabla.visualizar();
        
        // Llama al método para reemplazar todos los valores null con NA en la tabla
        tabla.reemplazarNullConNA();

        // Visualizar el estado de la tabla después de reemplazar null con NA
        tabla.visualizar();
    }
}
