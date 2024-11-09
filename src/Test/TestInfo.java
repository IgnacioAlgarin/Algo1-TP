package Test;
import Tabla.Tabla;

public class TestInfo {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        
    }
    public static void testInfoTabla(Tabla tabla) {
        System.out.println("Inicia test Info tabla");
        tabla.visualizar();
        System.out.println("\nInfo tabla");   
        tabla.mostrarCuadroInformacion();

        
    }    
        
}
