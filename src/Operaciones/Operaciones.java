package Operaciones;

/**
 * Clase abstracta que define un conjunto de operaciones estadísticas 
 * que se pueden realizar sobre una colección de datos.
 * @param <T> El tipo de dato sobre el que se realizarán las operaciones.
 */
public abstract class Operaciones<T> {

    /**
     * Calcula la suma de los elementos en la colección de datos.
     * @return El resultado de la suma como un valor de tipo T.
     */
    public abstract T sumar();

    /**
     * Cuenta el número de elementos en la colección de datos.
     * @return El número de elementos como un valor de tipo T.
     */
    public abstract T contar();

    /**
     * Calcula el promedio de los elementos en la colección de datos.
     * @return El promedio de los elementos como un valor de tipo T.
     */
    public abstract T promediar();

    /**
     * Encuentra el valor máximo en la colección de datos.
     * @return El valor máximo como un valor de tipo T.
     */
    public abstract T maximo();

    /**
     * Encuentra el valor mínimo en la colección de datos.
     * @return El valor mínimo como un valor de tipo T.
     */
    public abstract T minimo();

    /**
     * Calcula la varianza de los elementos en la colección de datos.
     * @return La varianza como un valor de tipo T.
     */
    public abstract T varianza();

    /**
     * Calcula el desvío estándar de los elementos en la colección de datos.
     * @return El desvío estándar como un valor de tipo T.
     */
    public abstract T desvio();
}

