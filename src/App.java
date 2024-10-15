import java.util.ArrayList;
import java.util.Arrays;

import Columna.Columna;
import Columna.Columna_num;
import Tabla.Tabla;

public class App {
    public static void main(String[] args) throws Exception {
        

        //test crear columna numerica *************************
        ArrayList<Number> datos1 = new ArrayList<>();
        datos1.add(1);
        datos1.add(1);
        datos1.add(1);
        datos1.add(1);
        datos1.add(1.2);
        datos1.add(1.3456);

        Columna columnaNumPrueba1 = new Columna_num(datos1);

        //System.out.println(columnaNumPrueba1.toString());

        ArrayList<Number> datos2 = new ArrayList<>(Arrays.asList(2,4,5,6.7,8.8888888));
        Columna columnaNumPrueba2 =new Columna_num<Number>(datos2, "pepe");

        System.out.println(columnaNumPrueba2.toString());

        // ****************************************************        
    }
}
