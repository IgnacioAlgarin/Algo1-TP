package Operaciones;
import java.util.List;

/**
 * Clase que realiza operaciones estadísticas sobre una lista de valores numéricos.
 * Extiende la clase abstracta Operaciones para proporcionar implementaciones de suma, conteo,
 * promedio, máximo, mínimo, varianza y desvío estándar.
 */
public class OperacionesColumna extends Operaciones<Double> {

    private List<Number> datos;

    /**
     * Constructor que inicializa la lista de datos sobre la que se realizarán las operaciones.
     * @param datos La lista de valores numéricos.
     */
    public OperacionesColumna(List<Number> datos) {
        this.datos = datos;
    }

    /**
     * Calcula la suma de todos los elementos en la lista de datos.
     * @return La suma total como un valor de tipo Double.
     */
    @Override
    public Double sumar() {
        double suma = 0;
        for (Number dato : datos) {
            if (dato != null) {
                suma += dato.doubleValue();
            }
        }
        return suma;
    }

    /**
     * Cuenta el número de elementos en la lista de datos.
     * @return El número de elementos como un valor de tipo Double.
     */
    @Override
    public Double contar() {
        return (double) datos.size();
    }

    /**
     * Calcula el promedio de los elementos en la lista de datos.
     * @return El promedio de los elementos como un valor de tipo Double.
     */
    @Override
    public Double promediar() {
        return sumar() / contar();
    }

    /**
     * Encuentra el valor máximo en la lista de datos.
     * @return El valor máximo como un valor de tipo Double.
     */
    @Override
    public Double maximo() {
        double max = Double.NEGATIVE_INFINITY;
        for (Number dato : datos) {
            if (dato != null && dato.doubleValue() > max) {
                max = dato.doubleValue();
            }
        }
        return max;
    }

    /**
     * Encuentra el valor mínimo en la lista de datos.
     * @return El valor mínimo como un valor de tipo Double.
     */
    @Override
    public Double minimo() {
        double min = Double.POSITIVE_INFINITY;
        for (Number dato : datos) {
            if (dato != null && dato.doubleValue() < min) {
                min = dato.doubleValue();
            }
        }
        return min;
    }

    /**
     * Calcula la varianza de los elementos en la lista de datos.
     * @return La varianza como un valor de tipo Double.
     */
    @Override
    public Double varianza() {
        double promedio = promediar();
        double sumaCuadrados = 0;
        for (Number dato : datos) {
            if (dato != null) {
                double diferencia = dato.doubleValue() - promedio;
                sumaCuadrados += diferencia * diferencia;
            }
        }
        return sumaCuadrados / contar();
    }

    /**
     * Calcula el desvío estándar de los elementos en la lista de datos.
     * @return El desvío estándar como un valor de tipo Double.
     */
    @Override
    public Double desvio() {
        return Math.sqrt(varianza());
    }
}
