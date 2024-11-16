package Test;
import Tabla.Tabla;

public class TestInfo {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        
    }
    public static void testInfoTabla(Tabla tabla) {
        System.out.println("\nInicia test Info tabla\n");
        tabla.visualizar();
        System.out.println("\nInfo tabla");   
        tabla.mostrarCuadroInformacion();

        
    }    
        
}
