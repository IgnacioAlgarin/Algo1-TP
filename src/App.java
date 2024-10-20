import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Columna.*;
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
        tabla.agregarColumna(datos1);
        //TEST COLUMNA STRING
        List<String> datos2 = Arrays.asList ("Hola","mundo",null,"NA");
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
        tabla.visualizar();
    }
}
