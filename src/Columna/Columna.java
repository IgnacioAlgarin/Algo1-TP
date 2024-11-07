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
    
    public Columna(U etiqueta, List<T> datos) {
        this.etiqueta = etiqueta;
        this.datos = new ArrayList<>(datos);
    }

    public void setDatos(List<?> nuevosDatos) {
        this.datos = (List<T>) nuevosDatos;  // Conversión genérica a T
    }

    public Class<?> getTipoClase() {
        return Object.class;
    }
    public U getetiqueta(){
        return etiqueta;
    }

    public void modificarValor() {
        
    }

    public void agregarDato(T dato){

    }
    public T getdato(int i){
        return datos.get(i);
    }

    public List<T> getDatos() {
        return datos;
    }

    public int largoColumna() {
        return datos.size();
    }
    public abstract Columna<T, U> copiaProfunda();

    

}
