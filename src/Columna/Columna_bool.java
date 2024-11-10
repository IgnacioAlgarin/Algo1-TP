package Columna;
import java.util.ArrayList;
import java.util.List;

import NA.NA;

/**
 * Clase que representa una columna booleana en una tabla de datos.
 * Extiende de la clase abstracta Columna para almacenar valores de tipo booleano.
 * @param <T> El tipo de datos almacenados en la columna.
 * @param <U> El tipo de la etiqueta de la columna.
 */
public class Columna_bool<T, U> extends Columna<T, U> {

    /**
     * Constructor que inicializa la columna con una etiqueta y una lista vacía de datos.
     * @param etiqueta La etiqueta de la columna.
     */
    public Columna_bool(U etiqueta) {
        super(etiqueta);
    }

    /**
     * Constructor que inicializa la columna con una etiqueta y una lista de datos.
     * @param etiqueta La etiqueta de la columna.
     * @param datos La lista de datos iniciales de la columna.
     */
    public Columna_bool(U etiqueta, List<T> datos) {
        super(etiqueta, datos);
    }

    /**
     * Método para generar una representación en cadena de la columna, útil para depuración.
     * @return Una cadena que representa la columna y su etiqueta.
     */
    @Override
    public String toString() {
        return "Columna_bool [columna=" + datos.toString() + "] " + etiqueta.toString();
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
     * Obtiene la clase de los datos almacenados en la columna, que en este caso es Boolean.
     * @return La clase Boolean.
     */
    public Class<Boolean> getTipoClase() {
        return Boolean.class;
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
     * Verifica si la columna contiene un valor booleano específico.
     * @param dato El valor booleano a buscar en la columna.
     * @return true si el valor se encuentra en la columna, de lo contrario false.
     */
    public boolean contieneDato(Boolean dato) {
        return datos.contains(dato);
    }

    /**
     * Realiza una copia profunda de la columna booleana.
     * @return Una nueva instancia de Columna_bool con los mismos datos y etiqueta que la columna actual.
     */
    public Columna_bool<T, U> copiaProfunda() {
        List<T> datosCopia = new ArrayList<>(this.datos);
        return new Columna_bool<>(this.etiqueta, datosCopia);
    }
}

