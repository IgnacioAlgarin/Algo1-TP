package Test;

import Tabla.Tabla;
import excepciones.EtiquetaEnUsoException;
import java.util.Arrays;
import java.util.List;

public class TestColumnaNumerica {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna de tipo Numérica con etiqueta específica:");

        // Datos de prueba: columna de tipo numérico
        List<Number> datosPuntajes = Arrays.asList(85, 90, 78, 88);

        // Intento de agregar la columna con etiqueta "Puntaje"
        try {
            tabla.agregarColumna(datosPuntajes, "Puntaje");
            System.out.println("Columna numérica 'Puntaje' agregada correctamente.");
        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error: La etiqueta 'Puntaje' ya está en uso. Detalles: " + e.getMessage());
        }

        // Visualizar la tabla para verificar que la columna se agregó correctamente
        System.out.println("Estado de la tabla después de agregar la columna numérica:");
        tabla.visualizar();
    }
}
