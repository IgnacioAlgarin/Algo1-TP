package Operaciones;
import java.util.List;

import NA.NA;

/**
 * Clase que realiza operaciones estadísticas sobre una lista de valores numéricos.
 * Extiende la clase abstracta Operaciones para proporcionar implementaciones de suma, conteo,
 * promedio, máximo, mínimo, varianza y desvío estándar.
 */
public class Operaciones {

    private List<Number> datos;

    /**
     * Constructor que inicializa la lista de datos sobre la que se realizarán las operaciones.
     * @param datos La lista de valores numéricos.
     */
    public Operaciones(List<Number> datos) {
        this.datos = datos;
    }

    /**
     * Calcula la suma de todos los elementos en la lista de datos, excluyendo NA.
     * @return La suma total como un valor de tipo Double.
     */
    
    public Double sumar() {
        double suma = 0;
        for (Object dato : datos) {
            if (dato instanceof Number && !dato.equals(NA.getInstance())) {
                suma += ((Number) dato).doubleValue();
            }
        }
        return suma;
    }

    /**
     * Cuenta el número de elementos en la lista de datos, excluyendo NA.
     * @return El número de elementos como un valor de tipo Double.
     */
    
    public Double contar() {
        int count = 0;
        for (Object dato : datos) {
            if (dato instanceof Number && !dato.equals(NA.getInstance())) {
                count++;
            }
        }
        return (double) count;
    }

    /**
     * Calcula el promedio de los elementos en la lista de datos, excluyendo NA.
     * @return El promedio de los elementos como un valor de tipo Double.
     */
    
    public Double promediar() {
        return sumar() / contar();
    }

    /**
     * Encuentra el valor máximo en la lista de datos, excluyendo NA.
     * @return El valor máximo como un valor de tipo Double.
     */
    
    public Double maximo() {
        double max = Double.NEGATIVE_INFINITY;
        for (Object dato : datos) {
            if (dato instanceof Number && !dato.equals(NA.getInstance())) {
                double valor = ((Number) dato).doubleValue();
                if (valor > max) {
                    max = valor;
                }
            }
        }
        return max;
    }

    /**
     * Encuentra el valor mínimo en la lista de datos, excluyendo NA.
     * @return El valor mínimo como un valor de tipo Double.
     */
    
    public Double minimo() {
        double min = Double.POSITIVE_INFINITY;
        for (Object dato : datos) {
            if (dato instanceof Number && !dato.equals(NA.getInstance())) {
                double valor = ((Number) dato).doubleValue();
                if (valor < min) {
                    min = valor;
                }
            }
        }
        return min;
    }

    /**
     * Calcula la varianza de los elementos en la lista de datos, excluyendo NA.
     * @return La varianza como un valor de tipo Double.
     */
    
    public Double varianza() {
        double promedio = promediar();
        double sumaCuadrados = 0;
        int count = 0;
        for (Object dato : datos) {
            if (dato instanceof Number && !dato.equals(NA.getInstance())) {
                double diferencia = ((Number) dato).doubleValue() - promedio;
                sumaCuadrados += diferencia * diferencia;
                count++;
            }
        }
        return sumaCuadrados / count;
    }


    /**
     * Calcula el desvío estándar de los elementos en la lista de datos.
     * @return El desvío estándar como un valor de tipo Double.
     */
    
    public Double desvio() {
        return Math.sqrt(varianza());
    }
}
