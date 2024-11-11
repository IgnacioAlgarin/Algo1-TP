package Test;
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
        System.out.println("inicio test archivo\n");
        
        
        // List<Object[]> datos = archivo.parseCSV(",", false);

        // System.out.println("Contenido del archivo CSV:");
        // for (Object[] fila : datos) {
        //     System.out.println(Arrays.toString(fila));
        // }

        Tabla tablaArchivo = new Tabla();
        tablaArchivo = Archivo.importar("src/datos_prueba.csv",",", false);
        System.out.println("tabla importada\n");
        tablaArchivo.visualizar();

       //Test exportar archivo
       System.out.println("Test exportar archivo\n");
        Archivo.exportar(tablaArchivo, "src/prueba_exportar.csv");

        //Test con archivo de 1000 filas generado aleatoriamente

        System.out.println("Generamos un df de 1000 filas y nueve columnas \n");
        Tabla tablaPrueba1 = Archivo.importar("src/df_1000filas.csv", ",", true) ;
        // tablaPrueba1.visualizar();
        tablaPrueba1.mostrarCuadroInformacion();

        //Test errores
        System.out.println("Test importar con nombre incorrecto");

        Tabla tablaPrueba2 = Archivo.importar("src/df_000filas.csv", ",", true);
        tablaPrueba2.visualizarResumen();

        System.out.println("Test importar tabla grande visualizar");
        tablaPrueba2 = Archivo.importar("src/df_1000filas.csv", ",", true);
        tablaPrueba2.visualizar();
        tablaPrueba2.visualizarResumen();
    }
}
