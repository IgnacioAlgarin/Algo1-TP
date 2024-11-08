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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
        result = prime * result + ((datos == null) ? 0 : datos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Columna other = (Columna) obj;
        if (etiqueta == null) {
            if (other.etiqueta != null)
                return false;
        } else if (!etiqueta.equals(other.etiqueta))
            return false;
        if (datos == null) {
            if (other.datos != null)
                return false;
        } else if (!datos.equals(other.datos))
            return false;
        return true;
    }

}
