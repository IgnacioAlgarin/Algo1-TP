package Columna;

import java.util.ArrayList;

public class Columna_string <T extends CharSequence> extends Columna {

        // Constructores
    public <U> Columna_string(ArrayList<T> datos, U etiqueta ) {
        super.datos = datos;
        super.etiqueta = etiqueta;
    }

    public <U> Columna_string(ArrayList<T> datos) {
        super.datos = datos;
        //Tabla tiene que asignarle una etiqueta numerica entera
    }

    //generamos toString para visualizar mientras probamos.
    @Override
    public String toString() {
        return "Columna_string [columna=" + datos + "] ";
    }
}


