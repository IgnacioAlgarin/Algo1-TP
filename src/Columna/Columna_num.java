package Columna;
import NA.NA;

public class Columna_num<T, U> extends Columna <T, U> {
    // Constructores
    public Columna_num(U etiqueta ) {
        super(etiqueta);
    }

    //generamos toString para visualizar mientras probamos.
    @Override
    public String toString() {
        return "Columna_num [columna=" + datos.toString() + "] " + etiqueta.toString();
    }
    
    public void agregarDato(T dato) {
        if (dato == null){
            datos.add((T) new NA());
        }else {
            datos.add(dato);
        }

    }


}
