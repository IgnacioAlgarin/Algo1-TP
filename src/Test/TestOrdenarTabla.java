package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestOrdenarTabla {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando ordenar tabla por 'Edad' ascendente y 'Nombre' descendente:");

        // Definir las etiquetas de columnas y el orden deseado
        List<String> etiquetas = Arrays.asList("Edad", "Nombre");
        List<Boolean> orden = Arrays.asList(true, false);  // Edad ascendente, Nombre descendente

        try {
            tabla.ordenarTabla(etiquetas, orden);
            System.out.println("Tabla ordenada:");
            tabla.visualizar();
        } catch (Exception e) {
            System.out.println("Error al ordenar la tabla: " + e.getMessage());
        }
    }
}
