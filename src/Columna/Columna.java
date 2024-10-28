package Columna;
import java.util.ArrayList;
import java.util.List; 

public abstract class Columna<T, U> {
    protected U etiqueta;
    protected List<T> datos;

    public Columna(U etiqueta) {
        this.etiqueta = etiqueta;
        this.datos = new ArrayList<>();
    }  

    public Class<?> getTipoClase() {
        return Object.class;
    }



    public void modificarValor() {
        
    }

    public void agregarDato(T dato){

    }

}
