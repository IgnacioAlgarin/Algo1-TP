import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Archivo.Archivo;
import Columna.Columna;
import Tabla.Tabla;
import excepciones.*;

public class TestFiltro {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        System.out.println("inicia test filtrado de tabla");
        Archivo archivo = new Archivo("datos_prueba.csv", "src/");
        Tabla tablaAFiltrar = new Tabla();
        tablaAFiltrar = archivo.importar(",", false);
        tablaAFiltrar.visualizar();

        System.out.println("testeo filtrar");
        tablaAFiltrar = tablaAFiltrar.filtrar(tablaAFiltrar, "Columna_0", '>', 0.1);
        tablaAFiltrar.visualizar();

        tablaAFiltrar = archivo.importar(",", false);
        tablaAFiltrar = tablaAFiltrar.filtrar(tablaAFiltrar,"Columna_0 > 1 or Columna_1 = hola");
        tablaAFiltrar.visualizar();
    }
}
