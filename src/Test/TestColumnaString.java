package Test;

import Tabla.Tabla;
import java.util.Arrays;
import java.util.List;

public class TestColumnaString {

    public static void ejecutarPrueba(Tabla tabla) {
        System.out.println("Probando agregar columna de tipo string adicional:");
        System.out.println("Tabla inicial:\n");
        tabla.visualizar();

        // Agregar una columna de tipo string con algunos valores nulos
        List<String> nuevaColumnaString = Arrays.asList("A", "B", null, "D");
        tabla.agregarColumna(nuevaColumnaString, "Letras");

        // Visualizar la tabla después de agregar la columna
        System.out.println("Columna string adicional agregada:");
        tabla.visualizar();
    }
}
