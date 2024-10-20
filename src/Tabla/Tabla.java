package Tabla;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import Columna.*;
import Fila.Fila;
import excepciones.EtiquetaEnUsoException;
import excepciones.ListaDatosVaciaException;
import excepciones.TipoDeEtiquetaInvalido;
import excepciones.TipoinconsistenteException;
import excepciones.TipoNoSoportadoException;


public class Tabla {

    private List<Columna<?, ?>> tabla;
    private List <Fila> filas;
    private int contadorEtiqueta = 0;
    private Set <Object> etiquetasUsadasc;
    private Set <Object> etiquetasUsadasf;


    // Constructores
    public Tabla() {
        tabla = new ArrayList<>();
        etiquetasUsadasc = new HashSet<>();
        etiquetasUsadasf = new HashSet<>();
        filas = new ArrayList<>();
    }
    private int generarEtiqueta(){
        int nuevaetiqueta;
        do{
            nuevaetiqueta = contadorEtiqueta++;
        } while (etiquetasUsadasc.contains(nuevaetiqueta));
        return nuevaetiqueta;
    }

    // Metodos
    public <E> void agregarColumna(List<?> columna, E etiqueta ) {
        if (columna.isEmpty()){
            throw new ListaDatosVaciaException("No se puede agregar una columna sin elementos");
        } 
        if (!(etiqueta instanceof String) && !(etiqueta instanceof Integer)){
            throw new TipoDeEtiquetaInvalido ("Por favor utilice una etiqueta de tipo entero o string");
        }
        //falta checkear cantidad de filas para agregar una nueva columna
        if (etiquetasUsadasc.contains(etiqueta)){
            throw new EtiquetaEnUsoException ("la etiqueta ya se encuentra en uso, por favor utilice otra");
        }


        etiquetasUsadasc.add(etiqueta);

        Columna <?, ?> nueva_columna;
        boolean todosNull = true;
        Class<?> tipoPrimero = null;

        for (Object dato: columna) {
            if (dato != null) {
                todosNull = false;

                if (tipoPrimero == null) {
                    if (dato instanceof Number){
                        tipoPrimero = Number.class;
                    } else {
                        tipoPrimero = dato.getClass();
                    }
                } else if (tipoPrimero != Number.class && !tipoPrimero.isAssignableFrom(dato.getClass())){
                    throw new TipoinconsistenteException ("La columna contiene tipos de datos distintos");
                }
            }

        }

        if (todosNull) {
            nueva_columna = new Columna_string<String, E>(etiqueta);
            for (int i = 0; i < columna.size(); i++){
                nueva_columna.agregarDato(null);
            }
        } else {
            if (tipoPrimero == String.class) {
                nueva_columna = new Columna_string<String, E>(etiqueta);
            } else if (tipoPrimero == Number.class) {
                nueva_columna = new Columna_num<Number, E>(etiqueta);
            } else if (tipoPrimero == Boolean.class) {
                nueva_columna = new Columna_bool<Boolean, E>(etiqueta);
            } else {
                throw new TipoNoSoportadoException ("Tipo de dato no soportado");
            }
        }

        for (Object dato : columna) {
            if (dato == null) {
                nueva_columna.agregarDato(null);
            } else {
                if (nueva_columna instanceof Columna_string){
                    ((Columna_string<String, E>) nueva_columna).agregarDato((String)dato);
                } else if (nueva_columna instanceof Columna_num){
                    ((Columna_num<Number, E>) nueva_columna).agregarDato((Number)dato);
                } else if (nueva_columna instanceof Columna_string){
                    ((Columna_bool<Boolean, E>) nueva_columna).agregarDato((Boolean)dato);
                }                
            }
        }
        tabla.add(nueva_columna);
    }
    public void agregarColumna(List<?> columna) {
        agregarColumna(columna, generarEtiqueta());                
    }

    public void visualizar() {
        for (Columna<?,?> columna: tabla){
            System.out.println(columna);
        }


    }

    public Tabla copia_p(String nombre) {
        Tabla copiaTabla = new Tabla();
        return copiaTabla;
    }

    public void index() {

    }

    public void agregar_NA() {

    }

    public Tabla concatenar(Tabla tabla1, Tabla tabla2, String etiqueta) {
        Tabla concatenada = new Tabla();
        return concatenada; 
    }

    public Tabla filtrar(Tabla tabla) {
        Tabla filtrada = new Tabla();
        return filtrada;
    }

    public <E,T> Tabla seleccionar(List<E> etiquetaFila, List<T> etiquetaColumna) {
        Tabla seleccion = new Tabla();
        return seleccion;
    }

    public Tabla aletorio(Double porcentaje) {
        Tabla aleatorio = new Tabla();
        return aleatorio;
    }

    public Tabla limit(int cantidad, Boolean head) {
        Tabla limitada = new Tabla();
        return limitada;
    } 
}
