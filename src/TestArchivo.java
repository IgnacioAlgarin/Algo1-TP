import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Archivo.Archivo;
import Columna.Columna;
import Tabla.Tabla;
import excepciones.*;

public class TestArchivo {
    public static void main(String[] args) throws Exception {
        
        // Test lectura archivo
        System.out.println("inicio test archivo");
        Archivo archivo = new Archivo("datos_prueba.csv", "src/");
        
        
        // List<Object[]> datos = archivo.parseCSV(",", false);

        // System.out.println("Contenido del archivo CSV:");
        // for (Object[] fila : datos) {
        //     System.out.println(Arrays.toString(fila));
        // }

        Tabla tablaArchivo = new Tabla();
        tablaArchivo = archivo.importar(",", false);
        System.out.println("tabla importada");
        tablaArchivo.visualizar();

       //Test exportar archivo
        Archivo archivo2 = new Archivo("prueba_exportar.csv", "src/");
        archivo2.exportar(tablaArchivo, "src/");

    }
}
