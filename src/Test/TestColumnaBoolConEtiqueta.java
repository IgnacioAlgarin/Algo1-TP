package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestColumnaBoolConEtiqueta {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna booleana con etiqueta específica:");
        System.out.println("Tabla inicial:\n");
        tabla.visualizar();

        // Datos de prueba: columna booleana
        List<Boolean> datosBooleanos = Arrays.asList(true, false, true, null);

        // Agregar la columna con etiqueta "Activo"
        tabla.agregarColumna(datosBooleanos, "Activo");
        System.out.println("Columna booleana 'Activo' agregada.");

        // Visualizar la tabla para verificar que la columna se agregó correctamente
        System.out.println("Estado de la tabla después de agregar la columna booleana:");
        tabla.visualizar();
    }
}
