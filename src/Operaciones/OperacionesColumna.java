package Operaciones;
import java.util.List;

public class OperacionesColumna extends Operaciones<Double> {

    private List<Number> datos;

    public OperacionesColumna(List<Number> datos) {
        this.datos = datos;
    }

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

    @Override
    public Double contar() {
        return (double) datos.size();
    }

    @Override
    public Double promediar() {
        return sumar() / contar();
    }

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

    @Override
    public Double desvio() {
        return Math.sqrt(varianza());
    }

    }

