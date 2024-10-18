package Columna;

import java.util.ArrayList;

public class Columna_num<T extends Number> extends Columna{
    // Constructores
    public <U> Columna_num(ArrayList<T> datos, U etiqueta ) {
        super.datos = datos;
        super.etiqueta = etiqueta;
    }

    public <U> Columna_num(ArrayList<T> datos) {
        super.datos = datos;
        //Tabla tiene que asignarle una etiqueta numerica entera
    }

    //generamos toString para visualizar mientras probamos.
    @Override
    public String toString() {
        return "Columna_num [columna=" + datos.toString() + "] " + etiqueta.toString();
    }
}
