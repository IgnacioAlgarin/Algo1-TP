package Test;
import Tabla.Tabla;

public class TestBuscarDato {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        
    }
    public static void testBuscarDato(Tabla tabla) {
        System.out.println("Inicia test buscar dato");
        System.out.println("Tabla inicial:\n");
        tabla.visualizar();

        System.out.println("Busco 'Hola'");
        tabla.buscarDato("Hola");
        System.out.println("\nBusco 1.2"); 
        tabla.buscarDato(1.2);
        System.out.println("\nBusco true");
        tabla.buscarDato(true);   
        System.out.println("\nBusco un dato que no existe, 'otra cosa'");
        tabla.buscarDato("otra cosa");
        
        System.out.println("\nBusco 1.3456 en Columna 1");
        tabla.buscarDatosPorColumna(1.3456,1);
        System.out.println("\nBusco false en Columna Perro");
        tabla.buscarDatosPorColumna(false,"Perro");
        System.out.println("\nBusco 30 en Columna Edad");
        tabla.buscarDatosPorColumna(30,"Edad");
        System.out.println("\nBusco true en Columna_2, que no existe");
        tabla.buscarDatosPorColumna(true,"Columna_2");





        
    }    
        
}
