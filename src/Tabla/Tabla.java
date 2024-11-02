package Tabla;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import Columna.*;
import Fila.Fila;
import Operaciones.Operaciones;
import excepciones.*;


public class Tabla {

    private List<Columna<?, ?>> tabla;
    private List <Fila> filas;
    private int contadorEtiquetac = 0;
    private int contadorEtiquetaf = 0;
    private Set <Object> etiquetasUsadasc;
    private Set <Object> etiquetasUsadasf;


    // Constructores
    public Tabla() {
        tabla = new ArrayList<>();
        etiquetasUsadasc = new HashSet<>();
        etiquetasUsadasf = new HashSet<>();
        filas = new ArrayList<>();
    }
    private Integer generarEtiquetac(){
        int nuevaetiqueta;
        do{
            nuevaetiqueta = contadorEtiquetac++;
        } while (etiquetasUsadasc.contains(nuevaetiqueta));
        return nuevaetiqueta;
    }
    private Integer generarEtiquetaf() {
        int nuevaetiqueta;
        do{
            nuevaetiqueta = contadorEtiquetaf++;
        } while (etiquetasUsadasf.contains(nuevaetiqueta));
        etiquetasUsadasf.add(nuevaetiqueta);
        return nuevaetiqueta;
    }
    
    private void iniciarfilasI(int tamaño, List<String> etiquetaf) {

        if (filas.isEmpty()&& !(etiquetaf.isEmpty()) ){
            if (tamaño == etiquetaf.size()){
                for (String etiq : etiquetaf){
                    Fila nuevafila = new Fila(etiq, generarEtiquetaf());
                    filas.add(nuevafila);
                    etiquetasUsadasf.add(etiq);
                }
            } else {
                throw new EtiquetasfilaException("El tamaño de lista de etiquetas de la fila no coincide con el tamaño de la columna");
            }
        } else if(filas.isEmpty() && etiquetaf.isEmpty()){
            for (int i =0; i<tamaño; i++){
                Fila nuevafila = new Fila(i);
                filas.add(nuevafila); 
            }

        }else if (!filas.isEmpty() && etiquetaf.isEmpty()) {
            if (tamaño != filas.size()){
                throw new EtiquetasfilaException("El tamaño de columna no coincide con la cantidad de filas");
            }
        } else {
            Set <?> set1 = new HashSet<>(filas);
            Set <?> set2 = new HashSet<>(etiquetaf);
            if (tamaño != filas.size() || !(set1.equals(set2))){
                throw new EtiquetasfilaException("El tamaño de columna no coincide con la cantidad de filas o la lista de filas proporcionada no coincide con la presente en la tabla");
            }
        }
        
    }
    private boolean comprobarstring(List<?> etiquetaf) {
        for (Object etiqueta: etiquetaf){
            if (!(etiqueta instanceof String)){
                return false;
            }
        }
        return true;
    }
    private boolean comprobarenteros(List<?> etiquetaf) {
        for (Object etiqueta: etiquetaf){
            if (!(etiqueta instanceof Integer)){
                return false;
            }
        }
        return true;
    }

    // Metodos publicos

    public <E> void agregarColumna(List<?> columna, E etiqueta, List<String> etiquetaf) {
        if (columna.isEmpty()){
            throw new ListaDatosVaciaException("No se puede agregar una columna sin elementos");
        } 
        if (!(etiqueta instanceof String) && !(etiqueta instanceof Integer)){
            throw new TipoDeEtiquetaInvalido ("Por favor utilice una etiqueta de tipo entero o string");
        }
        if (etiquetasUsadasc.contains(etiqueta)){
            throw new EtiquetaEnUsoException ("la etiqueta ya se encuentra en uso, por favor utilice otra");
        }
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
        
        if (comprobarstring(etiquetaf) || etiquetaf.isEmpty()){
            List<String> listaString = new ArrayList<>();
            for (Object elemento : etiquetaf){
                listaString.add((String) elemento);
            }
            iniciarfilasI(columna.size(), listaString);

        } else {
            throw new TipoinconsistenteException ("Hay elementos de distinto tipos en la lista de etiquetas de fila ");
        }

        if (todosNull){
            for (int i = 0; i < columna.size(); i++){
                nueva_columna.agregarDato(null);
            }
        } else{
            for (Object dato : columna) {
                if (dato == null) {
                    nueva_columna.agregarDato(null);
                } else {
                    if (nueva_columna instanceof Columna_string){
                        ((Columna_string<String, E>) nueva_columna).agregarDato((String)dato);
                    } else if (nueva_columna instanceof Columna_num){
                        ((Columna_num<Number, E>) nueva_columna).agregarDato((Number)dato);
                    } else if (nueva_columna instanceof Columna_bool){
                        ((Columna_bool<Boolean, E>) nueva_columna).agregarDato((Boolean)dato);
                    }                
                }
            }
        }
        etiquetasUsadasc.add(etiqueta);
        tabla.add(nueva_columna);
    }

    public void agregarColumna(List<?> columna, int etiquetaColumna) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, etiquetaColumna, listaf);                
    }
    
    public void agregarColumna(List<?> columna, String etiqueta) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, etiqueta, listaf);                
    }

    public void agregarColumna(List<?> columna) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, generarEtiquetac(), listaf);                
    }

    public void agregarfila (String etiqueta){
        if (etiquetasUsadasf.contains(etiqueta)){
            throw new EtiquetaEnUsoException("Etiquetas en uso");
        }
        for (Columna<?,?> columna: tabla){
            columna.agregarDato(null);
        }        
        int i = generarEtiquetaf();
        Fila nuevafila = new Fila(etiqueta, i);
        filas.add(nuevafila); 
        etiquetasUsadasf.add(etiqueta);
        etiquetasUsadasf.add(i); 

    }

    public void agregarfila(){
        String etiqueta = null;
        agregarfila(etiqueta);   
    }

     public void agregarfila (String etiqueta,List<?> datos){
        if (etiquetasUsadasf.contains(etiqueta) ){
            throw new EtiquetaEnUsoException("Etiquetas en uso");
        }
        if ( datos.size() != tabla.size()){
            throw new DiferentetamañoException ("La cantidad de datos a insertar no coincide con la cantidad de columnas");
        }
        for (int f = 0; f < tabla.size(); f++){
            Columna<?, ?> columna = tabla.get(f);
            Object dato = datos.get(f);
            if (dato == null){
                continue;
            }

            if (!columna.getTipoClase().isInstance(dato)){
                throw new TipoinconsistenteException ("No coinciden los tipos de datos con los ya establecidos en las columnas.");
            }
        }
        for (int f = 0; f < tabla.size(); f++){
            Columna<?, ?> columna = tabla.get(f);
            Object dato = datos.get(f);  
            if (dato == null){
                columna.agregarDato(null);
            } else {
                if (columna instanceof Columna_string){
                    ((Columna_string<String, ?>) columna).agregarDato((String)dato);
                } else if (columna instanceof Columna_num){
                    ((Columna_num<Number, ?>) columna).agregarDato((Number)dato);
                } else if (columna instanceof Columna_bool){
                    ((Columna_bool<Boolean, ?>) columna).agregarDato((Boolean)dato);
                }      
            }
        }
        int i = generarEtiquetaf();
        Fila nuevafila = new Fila(etiqueta, i);
        filas.add(nuevafila);    
        etiquetasUsadasf.add(etiqueta);
        etiquetasUsadasf.add(i);
    }

    public void agregarfila(List<?> datos){
        String etiqueta = null;
        agregarfila(etiqueta, datos);
        
    }



    public void visualizar() {
        StringBuilder filastring = new StringBuilder();
        filastring.append(String.format("%-8s %-8s", "", ""));
    
        for (Columna<?, ?> columna : tabla) {
            filastring.append(String.format("%-8s", columna.getetiqueta()));
        }
        filastring.append("\n");
    
        for (int f = 0; f < filas.size(); f++) {
            filastring.append(String.format("%-8s %-8s", filas.get(f).getposicion(), filas.get(f).getetiqueta()));
            for (Columna<?, ?> columna : tabla) {
                filastring.append(String.format("%-8s", columna.getdato(f)));
            }
            filastring.append("\n");
        }
        
        System.out.println(filastring.toString());
    }

        public void visualizarParcial(List<String> etiquetasFilas, List<String> etiquetasColumnas) {
        StringBuilder output = new StringBuilder();
        output.append(String.format("%-8s %-8s", "", ""));
        
        List<Columna<?, ?>> columnasSeleccionadas = new ArrayList<>();
        for (Columna<?, ?> columna : tabla) {
            if (etiquetasColumnas == null || etiquetasColumnas.isEmpty() || etiquetasColumnas.contains(columna.getetiqueta())) {
                columnasSeleccionadas.add(columna);
                output.append(String.format("%-8s", columna.getetiqueta()));
            }
        }
        output.append("\n");

        for (Fila fila : filas) {
            if (etiquetasFilas == null || etiquetasFilas.isEmpty() || etiquetasFilas.contains(fila.getetiqueta())) {
                output.append(String.format("%-8s %-8s", fila.getposicion(), fila.getetiqueta()));
                for (Columna<?, ?> columna : columnasSeleccionadas) {
                    output.append(String.format("%-8s", columna.getdato(fila.getposicion())));
                }
                output.append("\n");
            }
        }

        System.out.println(output.toString());
    }

    public void visualizarAleatorio(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }

        int numFilasSeleccionadas = (int) Math.round((porcentaje / 100) * filas.size());
        Random random = new Random();
        List<Fila> filasAleatorias = new ArrayList<>();

        while (filasAleatorias.size() < numFilasSeleccionadas) {
            int indiceAleatorio = random.nextInt(filas.size());
            Fila filaSeleccionada = filas.get(indiceAleatorio);
            if (!filasAleatorias.contains(filaSeleccionada)) {
                filasAleatorias.add(filaSeleccionada);
            }
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format("%-8s %-8s", "", ""));
        for (Columna<?, ?> columna : tabla) {
            output.append(String.format("%-8s", columna.getetiqueta()));
        }
        output.append("\n");

        for (Fila fila : filasAleatorias) {
            output.append(String.format("%-8s %-8s", fila.getposicion(), fila.getetiqueta()));
            for (Columna<?, ?> columna : tabla) {
                output.append(String.format("%-8s", columna.getdato(filas.indexOf(fila))));
            }
            output.append("\n");
        }

        System.out.println("Visualización aleatoria (" + porcentaje + "% de filas):");
        System.out.println(output.toString());
    }

    public void visualizarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append(String.format("%-15s %-10s %-10s %-10s %-10s %-10s\n",
                "Columna", "Tipo", "No Nulos", "Nulos", "Mín", "Máx"));
    
        for (Columna<?, ?> columna : tabla) {
            String nombreColumna = columna.getetiqueta().toString();
            String tipoColumna = columna.getTipoClase().getSimpleName();
            int conteoNoNulos = 0;
            int conteoNulos = 0;
            Double min = null;
            Double max = null;
            Double suma = 0.0;
            int conteoNumerico = 0;
    
            // Recorre los datos de la columna y calcula estadísticas
            for (Object dato : columna.getDatos()) {
                if (dato == null) {
                    conteoNulos++;
                } else {
                    conteoNoNulos++;
                    if (dato instanceof Number) {
                        double valor = ((Number) dato).doubleValue();
                        if (min == null || valor < min) min = valor;
                        if (max == null || valor > max) max = valor;
                        suma += valor;
                        conteoNumerico++;
                    }             
                }
            }
    
            // Calcular promedio si es numérico
            Double promedio = conteoNumerico > 0 ? suma / conteoNumerico : null;
    
            // Formatear los resultados de acuerdo al tipo de columna
            resumen.append(String.format("%-15s %-10s %-10d %-10d %-10s %-10s\n",
                    nombreColumna,
                    tipoColumna,
                    conteoNoNulos,
                    conteoNulos,
                    min != null ? String.format("%.2f", min) : "-",
                    max != null ? String.format("%.2f", max) : "-"));
        }
    
        System.out.println("Resumen de la tabla:");
        System.out.println(resumen.toString());
    }

    public class OperacionesColumna implements Operaciones<Double> {

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

    public Columna<Number, ?> obtenerColumnaNumerica(String etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna) && columna instanceof Columna_num) {
                // Casting seguro si la columna es de tipo numérico
                return (Columna<Number, ?>) columna;
            }
        }
        throw new IllegalArgumentException("Columna numérica con etiqueta " + etiquetaColumna + " no encontrada.");
    }

    public void mostrarResumenOperaciones(String etiquetaColumna) {
        Columna<Number, ?> columna = obtenerColumnaNumerica(etiquetaColumna);
        OperacionesColumna operaciones = new OperacionesColumna(columna.getDatos());

        System.out.println("Resumen de operaciones para la columna: " + etiquetaColumna);
        System.out.println("Suma: " + operaciones.sumar());
        System.out.println("Conteo: " + operaciones.contar());
        System.out.println("Promedio: " + operaciones.promediar());
        System.out.println("Máximo: " + operaciones.maximo());
        System.out.println("Mínimo: " + operaciones.minimo());
        System.out.println("Varianza: " + operaciones.varianza());
        System.out.println("Desvío: " + operaciones.desvio());
    }

    //getters y setter
    public List<Columna<?, ?>> getColumnas() {
        return tabla;
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

    //Buscar Datos

    public boolean buscarDato(Object dato) {
        for (Columna<?, ?> columna : tabla) {
            if (dato instanceof String && columna instanceof Columna_string) {
                if (((Columna_string) columna).contieneDato((String) dato)) {
                    System.out.println("Dato encontrado: " + dato + " en columna '" + columna.getetiqueta() + "'" + " en fila " + filas.get(((Columna_string) columna).getDatos().indexOf(dato)).getposicion());
                    return true;
                }
            } else if (dato instanceof Number && columna instanceof Columna_num) {
                if (((Columna_num) columna).contieneDato((Number) dato)) {
                    System.out.println("Dato encontrado: " + dato + " en columna '" + columna.getetiqueta() + "'" + " en fila " + filas.get(((Columna_num) columna).getDatos().indexOf(dato)).getposicion());
                    return true;
                }
            } else if (dato instanceof Boolean && columna instanceof Columna_bool) {
                if (((Columna_bool) columna).contieneDato((Boolean) dato)) {
                    System.out.println("Dato encontrado: " + dato + " en columna '" + columna.getetiqueta() + "'" + " en fila " + filas.get(((Columna_bool) columna).getDatos().indexOf(dato)).getposicion());
                    return true;
                }
            }
        }
        System.out.println("Dato No encontrado: " + dato);
        return false; 
    }

    //Buscar datos por columna y muestra si hay repetidos  
    public boolean buscarDatosPorColumna(Object dato, Object etiquetaColumna) {
        boolean encontrado = false;
        try {
            boolean columnaExiste = false;
            for (Columna<?, ?> columna : tabla) {
                if (columna.getetiqueta().equals(etiquetaColumna)) {
                    columnaExiste = true; 
                    for (int f = 0; f < filas.size(); f++) {
                        if (dato.equals(columna.getdato(f))) {
                            System.out.println("Dato encontrado en columna '" + etiquetaColumna + "' en fila " + filas.get(f).getposicion() + " con etiqueta " + filas.get(f).getetiqueta() + ": " + dato);
                            encontrado = true;
                        }
                    }
                }
            }
            if (!columnaExiste) {
                throw new ColumnaNoValidaException("La columna '" + etiquetaColumna + "' no existe.");
            }
            if (!encontrado) {
                System.out.println("Dato no encontrado en columna '" + etiquetaColumna + "': " + dato);
            }   
        } catch (ColumnaNoValidaException e) {
            System.out.println(e.getMessage());
        }      
        return encontrado;
    }
    //Modificar datos
    public void modificarDato(Object etiquetaColumna, Object etiquetaFila, Object nuevoDato) {
        boolean columnaEncontrada = false;
        boolean filaEncontrada = false;

        try {
            for (Columna<?, ?> columna : tabla) {
                if (columna.getetiqueta().equals(etiquetaColumna)) {
                    columnaEncontrada = true;
                    if ((columna instanceof Columna_string && !(nuevoDato instanceof String)) ||
                        (columna instanceof Columna_num && !(nuevoDato instanceof Number)) ||
                        (columna instanceof Columna_bool && !(nuevoDato instanceof Boolean))) {
                        throw new TipoinconsistenteException("El tipo de dato de '" + nuevoDato + "' no coincide con el tipo de la columna '" + etiquetaColumna + "'.");
                    }   
                    for (Fila fila : filas) {
                        if ((etiquetaFila instanceof String && etiquetaFila.equals(fila.getetiqueta())) ||
                            (etiquetaFila instanceof Integer && etiquetaFila.equals(fila.getposicion()))) {
                            filaEncontrada = true;
                            int posicionFila = fila.getposicion();
    
                            if (columna instanceof Columna_string) {
                                ((Columna_string<String, ?>) columna).setDato(posicionFila, (String) nuevoDato);
                            } else if (columna instanceof Columna_num) {
                                ((Columna_num<Number, ?>) columna).setDato(posicionFila, (Number) nuevoDato);
                            } else if (columna instanceof Columna_bool) {
                                ((Columna_bool<Boolean, ?>) columna).setDato(posicionFila, (Boolean) nuevoDato);
                            }
                            System.out.println("Dato modificado en columna '" + etiquetaColumna + "' y fila " + etiquetaFila + ": " + nuevoDato);
                            return;
                        }
                    }  
                    if (!filaEncontrada) {
                        throw new EtiquetasfilaException("La fila con etiqueta '" + etiquetaFila + "' no existe.");
                    }
                }
            }    
            if (!columnaEncontrada) {
                throw new ColumnaNoValidaException("La columna con etiqueta '" + etiquetaColumna + "' no existe.");
            }
        } catch (EtiquetasfilaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ColumnaNoValidaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TipoinconsistenteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
