package Test;
import Tabla.Tabla;

public class TestBuscarDato {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        
    }
    public static void testBuscarDato(Tabla tabla) {
        System.out.println("Inicia test buscar dato");
        tabla.visualizar();

        tabla.buscarDato("Hola"); 
        tabla.buscarDato(1.2); 
        tabla.buscarDato(true);   
        tabla.buscarDato("otra cosa");
        
        tabla.buscarDatosPorColumna(1.3456,1);
        tabla.buscarDatosPorColumna(false,"Perro");
        tabla.buscarDatosPorColumna(30,"Edad");
        tabla.buscarDatosPorColumna(true,"Columna_2");





        
    }    
        
}
