package Tabla;
import java.util.List;
import java.util.ArrayList;

public class Tabla<T> {

    private List<List<T>> tabla;

    // Constructores
    public Tabla() {
        tabla = new ArrayList<>();
    }

    // Metodos
    public void agregarColumna(List<T> nueva_col ) {
    
    }

    public void visualizar() {

    }

    public Tabla<T> copia_p(String nombre) {
        Tabla<T> copiaTabla = new Tabla<>();
        return copiaTabla;
    }

    public void index() {

    }

    public void agregar_NA() {

    }

    public Tabla<T> concatenar(Tabla tabla1, Tabla tabla2, String etiqueta) {
        Tabla<T> concatenada = new Tabla<T>();
        return concatenada; 
    }

    public Tabla<T> filtrar(Tabla tabla) {
        Tabla<T> filtrada = new Tabla<T>();
        return filtrada;
    }

    public Tabla<T> seleccionar(List<T> etiquetaFila, List<T> etiquetaColumna) {
        Tabla<T> seleccion = new Tabla<T>();
        return seleccion;
    }

    public Tabla<T> aletorio(Double porcentaje) {
        Tabla<T> aleatorio = new Tabla<T>();
        return aleatorio;
    }

    public Tabla<T> limit(int cantidad, Boolean head) {
        Tabla<T> limitada = new Tabla<T>();
        return limitada;
    } 
}
