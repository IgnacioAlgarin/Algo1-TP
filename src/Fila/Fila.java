package Fila;

/**
 * Clase que representa una fila en una tabla de datos.
 * Cada fila tiene una posición y, opcionalmente, una etiqueta.
 */
public class Fila {
    private String etiqueta = null;
    private int posicion;

    /**
     * Constructor que inicializa una fila con una etiqueta y una posición.
     * @param etiqueta La etiqueta de la fila.
     * @param posicion La posición de la fila.
     */
    public Fila(String etiqueta, int posicion) {
        this.etiqueta = etiqueta;
        this.posicion = posicion;
    }
    /**
     * Constructor que inicializa una fila solo con una posición.
     * @param posicion La posición de la fila.
     */
    public Fila(int posicion) {
        this.posicion = posicion;
    }

    /**
     * Genera una representación en cadena de la fila.
     * @return Una cadena que describe la fila, indicando la posición y la etiqueta si está presente.
     */
    public String toString(){
        if (etiqueta == null){
            return "Fila " + posicion + ", sin etiqueta.";
        } else {
        return "Fila " + posicion + ", etiqueta: " + etiqueta + ".";
        }
    }

    /**
     * Obtiene la posición de la fila.
     * @return La posición de la fila.
     */
    public int getposicion(){
        return posicion;
    }

    /**
     * Obtiene la etiqueta de la fila.
     * @return La etiqueta de la fila, o null si no tiene etiqueta.
     */
    public String getetiqueta(){
        return etiqueta;
    }

    // /**
    //  * Obtiene el índice de la fila en base a su posición, ajustado en -1.
    //  * @return El índice de la fila (posición - 1).
    //  */
    // public int getIndice(){
    //     return posicion-1 ;
    // }
}
