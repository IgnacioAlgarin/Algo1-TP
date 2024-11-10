package Test;

import Tabla.Tabla;
import excepciones.EtiquetaEnUsoException;
import java.util.Arrays;
import java.util.List;

public class TestColumnaBoolConEtiqueta {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna booleana con etiqueta específica:");

        // Datos de prueba: columna booleana
        List<Boolean> datosBooleanos = Arrays.asList(true, false, true, null);

        // Agregar la columna con etiqueta "Activo"
        try {
            tabla.agregarColumna(datosBooleanos, "Activo");
            System.out.println("Columna booleana 'Activo' agregada correctamente.");
        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error: La etiqueta 'Activo' ya está en uso. Detalles: " + e.getMessage());
        }

        // Visualizar la tabla para verificar que la columna se agregó correctamente
        System.out.println("Estado de la tabla después de agregar la columna booleana:");
        tabla.visualizar();
    }
}
