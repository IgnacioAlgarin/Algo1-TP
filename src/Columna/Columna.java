package Columna;
import java.util.ArrayList;
import java.util.List; 

/**
 * Clase abstracta que representa una columna genérica en una tabla de datos.
 * @param <T> El tipo de datos que almacena la columna.
 * @param <U> El tipo de la etiqueta de la columna.
 */
public abstract class Columna<T, U> {
    /**
     * Etiqueta de la columna, que actúa como su identificador./**
     * Lista de datos almacenados en la columna.
     */
    protected U etiqueta;
    protected List<T> datos;

    /**
     * Constructor que inicializa la columna con una etiqueta y una lista vacía de datos.
     * @param etiqueta La etiqueta de la columna.
     */
    public Columna(U etiqueta) {
        this.etiqueta = etiqueta;
        this.datos = new ArrayList<>();
    } 
    
    /**
     * Constructor que inicializa la columna con una etiqueta y una lista de datos.
     * @param etiqueta La etiqueta de la columna.
     * @param datos La lista de datos iniciales de la columna.
     */
    public Columna(U etiqueta, List<T> datos) {
        this.etiqueta = etiqueta;
        this.datos = new ArrayList<>(datos);
    }

    /**
     * Establece una nueva lista de datos para la columna.
     * @param nuevosDatos Lista de datos que reemplazarán a los actuales.
     */
    public void setDatos(List<?> nuevosDatos) {
        this.datos = (List<T>) nuevosDatos;  // Conversión genérica a T
    }

    /**
     * Obtiene el tipo de clase de los elementos almacenados en la columna.
     * @return La clase del tipo de los datos almacenados.
     */
    public Class<?> getTipoClase() {
        return Object.class;
    }

    /**
     * Obtiene la etiqueta de la columna.
     * @return La etiqueta de la columna.
     */
    public U getetiqueta(){
        return etiqueta;
    }

    /**
     * Método para modificar un valor de la columna. Debe ser implementado en clases derivadas.
     */
    public void modificarValor() {
        
    }

    /**
     * Agrega un dato al final de la columna.
     * @param dato El dato a agregar.
     */
    public void agregarDato(T dato){

    }

    /**
     * Obtiene el dato en una posición específica de la columna.
     * @param i La posición del dato a obtener.
     * @return El dato en la posición especificada.
     */
    public T getdato(int i){
        return datos.get(i);
    }

    /**
     * Obtiene la lista completa de datos de la columna.
     * @return Lista de datos de la columna.
     */
    public List<T> getDatos() {
        return datos;
    }

     /**
     * Obtiene el número de elementos en la columna.
     * @return El tamaño de la columna.
     */
    public int largoColumna() {
        return datos.size();
    }

    /**
     * Realiza una copia profunda de la columna. Debe ser implementado en clases derivadas.
     * @return Una nueva columna con los mismos datos que la columna actual.
     */
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
