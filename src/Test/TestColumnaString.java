package Test;

import Tabla.Tabla;
import excepciones.EtiquetaEnUsoException;
import java.util.Arrays;
import java.util.List;

public class TestColumnaString {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna de tipo String con etiqueta específica:");

        // Datos de prueba: columna de tipo String
        List<String> datosNombres = Arrays.asList("Juan", "Ana", "Luis", null);

        // Intento de agregar la columna con etiqueta "Nombre"
        try {
            tabla.agregarColumna(datosNombres, "Nombre");
            System.out.println("Columna de tipo String 'Nombre' agregada correctamente.");
        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error: La etiqueta 'Nombre' ya está en uso. Detalles: " + e.getMessage());
        }

        // Visualizar la tabla para verificar que la columna se agregó correctamente
        System.out.println("Estado de la tabla después de agregar la columna de tipo String:");
        tabla.visualizar();
    }
}
