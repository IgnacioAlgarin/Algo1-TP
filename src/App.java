import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Archivo.Archivo;
import Columna.Columna;
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
        
        Tabla tabla2 = tabla.copia_p();
        datofila.add(4);
        tabla.agregarfila(datofila);
        Tabla concatenada = tabla.concatenar(tabla2);
        Tabla tabla3 = new Tabla(tabla);
        tabla.agregarfila(datofila);
        System.err.println("tabla1");
        tabla.visualizar();
        System.err.println("tabla2");
        tabla2.visualizar();

        System.err.println("tabla3");
        tabla3.visualizar();

        //Test Concatenar
        System.out.println("Prueba concatenar:");
        concatenada.visualizar();

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
        
        tablaArchivo.buscarDatosPorColumna(0.1,"Columna_0");
        tablaArchivo.buscarDatosPorColumna(0.1,"Columna_1");
        tablaArchivo.buscarDatosPorColumna(0.1,"Columna_8");
        tablaArchivo.buscarDatosPorColumna(true,"Columna_2");
        tablaArchivo.visualizar(); 

        // Test completar NA
        System.out.println("\nTEST remplazar null con NA y rellenar datos faltantes\n");

        tablaArchivo.mostrarCuadroInformacion();
        tablaArchivo.reemplazarNullConNA();
        tablaArchivo.visualizar();
        tablaArchivo.mostrarCuadroInformacion(); 
        tablaArchivo.rellenarDatosFaltantes("Columna_2",1); 
        tablaArchivo.visualizar();
        tablaArchivo.rellenarDatosFaltantes("Columna_2",true); 
        tablaArchivo.visualizar(); 
        tablaArchivo.mostrarCuadroInformacion(); 
        
        tabla.mostrarCuadroInformacion(); 
        tabla3.mostrarCuadroInformacion();
         


        // Test modificar dato 
         tablaArchivo.modificarDato("Columna_0", 2,0.3);
         tablaArchivo.modificarDato("Columna_0", 20,0.3);
         tablaArchivo.modificarDato("Columna_8", 2,0.3);        
         tablaArchivo.visualizar();
         tablaArchivo.modificarDato("Columna_8", 3,false); 
         tablaArchivo.modificarDato("Columna_0", 30,4);
         tablaArchivo.modificarDato("Columna_2", 3,"chau");
         tablaArchivo.modificarDato("Columna_2", 3,true);
         tablaArchivo.modificarDato("Columna_0", 3,4);
         tablaArchivo.modificarDato("Columna_1", 3,"chau");
         tablaArchivo.visualizar();

        // Test eliminar por fila y columna
        System.out.println("\n\nTEST ELIMINAR FILA Y COLUMNA");
        // Agregar columna de texto con etiqueta "Nombre"
        List<String> nombres = Arrays.asList("Ana", "Luis", "Marta", "Juan", "Pedro", "Sofía", "Lucía", "Carlos");
        tabla.agregarColumna(nombres, "Nombre");

        // Agregar columna booleana con etiqueta "Activo"
        List<Boolean> activo = Arrays.asList(true, false, true, true, false, true, true ,false);
        tabla.agregarColumna(activo, "Activo");

        // Mostrar la tabla inicial
        System.out.println("Tabla inicial:");
        tabla.visualizar();

        // Test: Eliminar una columna por etiqueta
        System.out.println("\nEliminando la columna 'Activo'...");
        tabla.eliminarColumna("Activo");
        tabla.visualizar();

        System.out.println("Antes de eliminar la columna en el índice 0:");
        tabla.mostrarEtiquetasColumnas();

        // Test: Eliminar una columna por índice
        System.out.println("\nEliminando la columna en el índice 0...");
        tabla.eliminarColumna(0); 
        tabla.visualizar();

        // Test: Eliminar una fila por índice
        System.out.println("\nEliminando la fila en el índice 0...");
        tabla.eliminarFila(0);
        tabla.visualizar();
        
        //Test: Ordenamiento
        System.out.println("Antes de ordenar por 'Edad' ascendente y 'Nombre' descendente:");
        tabla.visualizar();
        
        List<String> etiquetas = Arrays.asList("Edad", "Nombre");
        List<Boolean> orden = Arrays.asList(true, false);  // Edad ascendente, Nombre descendente
        
        tabla.ordenarTabla(etiquetas, orden);
        
        System.out.println("Después de ordenar:");
        tabla.visualizar();

        // Test: Ordenamiento con índices
        System.out.println("Antes de ordenar por 'Edad' ascendente y '0' descendente:");
        tabla.visualizar();
        List<Object> etiquetas2 = Arrays.asList("Edad", 0); // "Edad" y el índice 0 como etiquetas
        List<Boolean> orden2 = Arrays.asList(false, false);  // Orden ascendente para "Edad", descendente para 1
        System.out.println("Después de ordenar:");
        tabla.ordenarTabla(etiquetas2, orden2);
        tabla.visualizar();

        // Test: Filtrado
        System.out.println("inicia test filtrado de tabla");

        Tabla tablaAFiltrar = new Tabla();
        tablaAFiltrar = archivo.importar(",", false);
        tablaAFiltrar.visualizar();

        System.out.println("testeo filtrar");
        tablaAFiltrar = tablaAFiltrar.filtrar(tablaAFiltrar, "Columna_0", '>', 0.1);
        tablaAFiltrar.visualizar();
        
    }
}
