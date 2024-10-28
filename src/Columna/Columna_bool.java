package Columna;
import NA.NA;

public class Columna_bool<T, U> extends Columna <T, U> {
    // Constructores
    public Columna_bool(U etiqueta ) {
        super(etiqueta);
    }

    //generamos toString para visualizar mientras probamos.
    @Override
    public String toString() {
        return "Columna_bool [columna=" + datos.toString() + "] " + etiqueta.toString();
    }

    @SuppressWarnings("unchecked")
    public void agregarDato(T dato) {
        if (dato == null){
            datos.add((T) new NA());
        }else {
            datos.add(dato);
        }
    }

    @SuppressWarnings("unchecked")
    public void cambiarDato(T dato, int i) {
        if (dato == null){
            datos.set(i,(T) new NA());
        }else {
            datos.set(i,dato);
        }
    }
    public Class<Boolean> getTipoClase() {
        return Boolean.class;
    }
}
