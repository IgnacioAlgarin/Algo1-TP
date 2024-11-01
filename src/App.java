import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Archivo.Archivo;
import Tabla.Tabla;
import excepciones.*;

public class App {
    public static void main(String[] args) throws Exception {
        Tabla tabla = new Tabla();

        

        //test columna numerica *************************
        List<Number> datos1 = new ArrayList<>();
        datos1.add(1);
        datos1.add(1.2);
        datos1.add(1.3456);
        datos1.add(null);
        List<String> etiqueta = Arrays.asList ("hola","mi","pequeño","mundo");
        tabla.agregarColumna(datos1, 1, etiqueta);
        //TEST COLUMNA STRING
        List<String> datos2 = Arrays.asList ("Hola","mundo",null,"NAaa");
        tabla.agregarColumna(datos2);
        //TEST COLUMNA BOOL CON ETIQUETA
        List<Boolean> datos3 = Arrays.asList(true,false,false,null);
        tabla.agregarColumna(datos3, "etiqueta");
        //TEST CON ETIQUETA REPETIDA numerica
        List<String> datos4 = Arrays.asList ("Hola","mundo2","nA","NA");
        try {
            tabla.agregarColumna(datos4,1);
        } catch (EtiquetaEnUsoException e) {
        System.out.println(e.getMessage());
        }
        //TEST CON ETIQUETA REPETIRA STRING
        try {
            tabla.agregarColumna(datos4,"etiqueta");
        } catch (EtiquetaEnUsoException e) {
        System.out.println(e.getMessage());
        }
        //TEST CON NULL 
        List<Object> datos5 = Arrays.asList (null,null,null,null);
        tabla.agregarColumna(datos5);
        List<Object> datofila = new ArrayList<>();
        datofila.add(1);
        datofila.add("hola");
        datofila.add(true);
        datofila.add("hola");
        tabla.agregarfila( datofila);
        tabla.agregarfila("nuevo");
        tabla.visualizar();

        List<Number> edades = Arrays.asList(25, 30, 7, 9, 90, 50);
        tabla.agregarColumna(edades, "Edad");

        tabla.mostrarResumenOperaciones("Edad"); 

        // Test lectura archivo
        System.out.println("inicio test archivo");
        Archivo archivo = new Archivo("datos_prueba.csv", "src/");
        List<Object[]> datos = archivo.parseCSV(",", false);

        System.out.println("Contenido del archivo CSV:");
        for (Object[] fila : datos) {
            System.out.println(Arrays.toString(fila));
        }

        Tabla tablaArchivo = new Tabla();
        tablaArchivo = archivo.importar(",", false);
        System.out.println("tabla importada");
        tablaArchivo.visualizar();

        //Test exportar archivo
        Archivo archivo2 = new Archivo("prueba_exportar.csv", "src/");
        archivo2.exportar(tablaArchivo, "src/");
        
        // Test buscar dato
        tablaArchivo.buscarDato("hola"); 
        tablaArchivo.buscarDato(0.1); 
        tablaArchivo.buscarDato(true);   
        tablaArchivo.buscarDato("otra cosa");
        
        tablaArchivo.buscarDatoC(0.1,"Columna_0");
        tablaArchivo.buscarDatoC(0.1,"Columna_1");
        tablaArchivo.buscarDatoC(0.1,"Columna_8");
        
        tablaArchivo.buscarDatosCRepetidos(true,"Columna_2"); 
    }
}
