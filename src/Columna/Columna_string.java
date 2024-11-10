package Columna;
import java.util.ArrayList;
import java.util.List;

import NA.NA;

/**
 * Clase que representa una columna de tipo String en una tabla de datos.
 * Extiende de la clase abstracta Columna para almacenar valores de tipo cadena.
 * @param <T> El tipo de datos almacenados en la columna.
 * @param <U> El tipo de la etiqueta de la columna.
 */
public class Columna_string<T, U> extends Columna<T, U> {

    /**
     * Constructor que inicializa la columna con una etiqueta y una lista vacía de datos.
     * @param etiqueta La etiqueta de la columna.
     */
    public Columna_string(U etiqueta) {
        super(etiqueta);
    }

    /**
     * Constructor que inicializa la columna con una etiqueta y una lista de datos.
     * @param etiqueta La etiqueta de la columna.
     * @param datos La lista de datos iniciales de la columna.
     */
    public Columna_string(U etiqueta, List<T> datos) {
        super(etiqueta, datos);
    }

    /**
     * Método para generar una representación en cadena de la columna, útil para depuración.
     * @return Una cadena que representa la columna y su etiqueta.
     */
    @Override
    public String toString() {
        return "Columna_string [columna=" + datos + "] " + etiqueta.toString();
    }

    /**
     * Agrega un dato a la columna. Si el dato es nulo, agrega un valor nulo a la lista.
     * @param dato El dato a agregar.
     */
    public void agregarDato(T dato) {
        if (dato == null) {
            datos.add(null);
        } else {
            datos.add(dato);
        }
    }

    /**
     * Obtiene la clase de los datos almacenados en la columna, que en este caso es String.
     * @return La clase String.
     */
    public Class<String> getTipoClase() {
        return String.class;
    }

    /**
     * Establece un dato en una posición específica de la columna.
     * Si el dato es nulo, establece un valor nulo en la posición especificada.
     * @param posicion La posición donde se establecerá el dato.
     * @param dato El dato a establecer.
     */
    public void setDato(int posicion, T dato) {
        if (dato == null) {
            datos.set(posicion, null);
        } else {
            datos.set(posicion, dato);
        }
    }

    /**
     * Obtiene la etiqueta de la columna.
     * @return La etiqueta de la columna.
     */
    public U getEtiqueta() {
        return etiqueta;
    }

    /**
     * Obtiene el dato en una posición específica de la columna.
     * @param i La posición del dato a obtener.
     * @return El dato en la posición especificada.
     */
    public T getDato(int i) {
        return datos.get(i);
    }

    /**
     * Verifica si la columna contiene un valor de cadena específico.
     * @param dato El valor de cadena a buscar en la columna.
     * @return true si el valor se encuentra en la columna, de lo contrario false.
     */
    public boolean contieneDato(String dato) {
        return datos.contains(dato);
    }

    /**
     * Realiza una copia profunda de la columna de tipo String.
     * @return Una nueva instancia de Columna_string con los mismos datos y etiqueta que la columna actual.
     */
    public Columna_string<T, U> copiaProfunda() {
        List<T> datosCopia = new ArrayList<>(this.datos);
        return new Columna_string<>(this.etiqueta, datosCopia);
    }
}



