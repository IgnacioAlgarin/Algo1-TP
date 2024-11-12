package Test;
import Tabla.Tabla;

public class TestModificarDato {
    public static void main(String[] args) throws Exception {
        // Test: Filtrado
        
    }
    public static void testModificarDato(Tabla tabla) {
        System.out.println("\nInicia test Modificar dato");
        tabla.visualizar();
        System.out.println("\nSe debe pasar por parametro el nombre de la columna, la fila y el nuevo dato");
        System.out.println();
        tabla.modificarDato("Perro", "pequeño",false); 
        tabla.modificarDato("Nombre", 0,"Analia");
        tabla.modificarDato(331, 3,"chauNA");
        tabla.modificarDato("Columna_2", 3,true);
        System.out.println();
        tabla.visualizar();




        
    }    
        
}
