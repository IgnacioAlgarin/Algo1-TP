package Tabla;
import Columna.*;
import Fila.Fila;
import Filtro.Filtro;
import NA.NA;
import Operaciones.OperacionesColumna;
import excepciones.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Tabla  implements Filtro{

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

    public Tabla(Tabla otraTabla){
        this.tabla = new ArrayList<>();
        for (Columna<?, ?> columna : otraTabla.tabla) {
            this.tabla.add(columna.copiaProfunda());
        }

        this.filas = new ArrayList<>();
        for (Fila fila : otraTabla.filas) {
            this.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
        }
        
        this.contadorEtiquetac = otraTabla.contadorEtiquetac;
        this.contadorEtiquetaf = otraTabla.contadorEtiquetaf;
        this.etiquetasUsadasc = new HashSet<>(otraTabla.etiquetasUsadasc);
        this.etiquetasUsadasf = new HashSet<>(otraTabla.etiquetasUsadasf);
    }

    //Metodos privados
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

    private Boolean comprobarOrden (Tabla tabla2){
        Boolean ordenado = true;
        for (int i = 0; i < tabla.size(); i++){
            if (tabla2.tabla.get(i).getetiqueta() != tabla.get(i).getetiqueta() || ( tabla.get(i).getClass() != tabla2.tabla.get(i).getClass() )){
                ordenado = false;
                break;
            }
        }
        return ordenado;
    }
    // Metodos publicos

    // Agrega una nueva columna
    public <E> void agregarColumna(List<?> columna, E etiqueta, List<String> etiquetaf) {
        try {
            if (columna.isEmpty()) {
                throw new ListaDatosVaciaException("No se puede agregar una columna sin elementos");
            }
            if (!(etiqueta instanceof String) && !(etiqueta instanceof Integer)) {
                throw new TipoDeEtiquetaInvalidoException("Por favor utilice una etiqueta de tipo entero o string");
            }
            if (etiquetasUsadasc.contains(etiqueta)) {
                throw new EtiquetaEnUsoException("la etiqueta ya se encuentra en uso, por favor utilice otra");
            }
            Columna<?, ?> nueva_columna;
            boolean todosNull = true;
            Class<?> tipoPrimero = null;
            for (Object dato : columna) {
                if (dato != null) {
                    todosNull = false;
                    if (tipoPrimero == null) {
                        if (dato instanceof Number) {
                            tipoPrimero = Number.class;
                        } else {
                            tipoPrimero = dato.getClass();
                        }
                    } else if (tipoPrimero != Number.class && !tipoPrimero.isAssignableFrom(dato.getClass())) {
                        throw new TipoinconsistenteException("La columna contiene tipos de datos distintos");
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
                    throw new TipoNoSoportadoException("Tipo de dato no soportado");
                }
            }

            if (comprobarstring(etiquetaf) || etiquetaf.isEmpty()) {
                List<String> listaString = new ArrayList<>();
                for (Object elemento : etiquetaf) {
                    listaString.add((String) elemento);
                }
                iniciarfilasI(columna.size(), listaString);

            } else {
                throw new TipoinconsistenteException(
                        "Hay elementos de distinto tipos en la lista de etiquetas de fila ");
            }

            if (todosNull) {
                for (int i = 0; i < columna.size(); i++) {
                    nueva_columna.agregarDato(null);
                }
            } else {
                for (Object dato : columna) {
                    if (dato == null) {
                        nueva_columna.agregarDato(null);
                    } else {
                        if (nueva_columna instanceof Columna_string) {
                            ((Columna_string<String, E>) nueva_columna).agregarDato((String) dato);
                        } else if (nueva_columna instanceof Columna_num) {
                            ((Columna_num<Number, E>) nueva_columna).agregarDato((Number) dato);
                        } else if (nueva_columna instanceof Columna_bool) {
                            ((Columna_bool<Boolean, E>) nueva_columna).agregarDato((Boolean) dato);
                        }
                    }
                }
            }
            etiquetasUsadasc.add(etiqueta);
            tabla.add(nueva_columna);

        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ListaDatosVaciaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TipoDeEtiquetaInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EtiquetasfilaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TipoinconsistenteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TipoNoSoportadoException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
    //Agrega una nueva fila vacia
    public void agregarfila(String etiqueta) {
        try {
            if (etiquetasUsadasf.contains(etiqueta)) {
                throw new EtiquetaEnUsoException("Etiquetas en uso");
            }
            for (Columna<?, ?> columna : tabla) {
                columna.agregarDato(null);
            }
            int i = generarEtiquetaf();
            Fila nuevafila = new Fila(etiqueta, i);
            filas.add(nuevafila);
            etiquetasUsadasf.add(etiqueta);
            etiquetasUsadasf.add(i);
        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void agregarfila(){
        String etiqueta = null;
        agregarfila(etiqueta);   
    }
    //Agrega una nueva fila, con datos proporcionados por el usuario
    public void agregarfila(String etiqueta, List<?> datos) {
        try {
            if (etiquetasUsadasf.contains(etiqueta) && etiqueta != null) {
                throw new EtiquetaEnUsoException("Etiquetas en uso");
            }
            if (datos.size() != tabla.size()) {
                throw new DiferentetamañoException(
                        "La cantidad de datos a insertar no coincide con la cantidad de columnas");
            }
            for (int f = 0; f < tabla.size(); f++) {
                Columna<?, ?> columna = tabla.get(f);
                Object dato = datos.get(f);
                if (dato == null) {
                    continue;
                }

                if (!columna.getTipoClase().isInstance(dato)) {
                    throw new TipoinconsistenteException(
                            "No coinciden los tipos de datos con los ya establecidos en las columnas.");
                }
            }
            for (int f = 0; f < tabla.size(); f++) {
                Columna<?, ?> columna = tabla.get(f);
                Object dato = datos.get(f);
                if (dato == null) {
                    columna.agregarDato(null);
                } else {
                    if (columna instanceof Columna_string) {
                        ((Columna_string<String, ?>) columna).agregarDato((String) dato);
                    } else if (columna instanceof Columna_num) {
                        ((Columna_num<Number, ?>) columna).agregarDato((Number) dato);
                    } else if (columna instanceof Columna_bool) {
                        ((Columna_bool<Boolean, ?>) columna).agregarDato((Boolean) dato);
                    }
                }
            }
            int i = generarEtiquetaf();
            Fila nuevafila = new Fila(etiqueta, i);
            filas.add(nuevafila);
            etiquetasUsadasf.add(etiqueta);
            etiquetasUsadasf.add(i);
        } catch (EtiquetaEnUsoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (DiferentetamañoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TipoinconsistenteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void agregarfila(List<?> datos) {
        String etiqueta = null;
        agregarfila(etiqueta, datos);

    }
    //Imprime la tabla en pantalla mostrando hasta 8 columnas y 30 filas
    public void visualizar() {
        StringBuilder filastring = new StringBuilder();
        filastring.append("+-------+----------+");
        for (Columna<?, ?> columna : tabla) {
            filastring.append("----------+");
        }
        filastring.append("\n");
        filastring.append(String.format("| %-5s | %-8s |", "ID", "Etiqueta"));
        for (Columna<?, ?> columna : tabla) {
            String etiqueta = String.format("%-8s", columna.getetiqueta());
            if (etiqueta.length() > 8) {
                etiqueta = etiqueta.substring(0, 6) + "..";
            }
            filastring.append(String.format(" %-8s |", etiqueta));
        }
        filastring.append("\n");
        filastring.append("+-------+----------+");
        for (Columna<?, ?> columna : tabla) {
            filastring.append("----------+");
        }
        filastring.append("\n");
        for (int f = 0; f < filas.size(); f++) {
            String posicionLimitada = String.format("%-5s",filas.get(f).getposicion());
            if (posicionLimitada.length() > 5) {
                posicionLimitada = posicionLimitada.substring(0, 3) + "..";
            }
            String etiquetaLimitada = String.format("%-8s",filas.get(f).getetiqueta());
            if (etiquetaLimitada.length() > 8) {
                etiquetaLimitada = etiquetaLimitada.substring(0, 6) + "..";
            }
            filastring.append(String.format("| %-5s | %-8s |", posicionLimitada, etiquetaLimitada));
            for (Columna<?, ?> columna : tabla) {
                String dato = String.format("%-8s",columna.getdato(f));
                if (dato.length() > 8) {
                    dato = dato.substring(0, 6) + "..";
                }
                filastring.append(String.format(" %-8s |", dato));
            }
            filastring.append("\n");
            filastring.append("+-------+----------+");
            for (Columna<?, ?> columna : tabla) {
                filastring.append("----------+");
            }
            filastring.append("\n");
        }
        System.out.println(filastring);
    }
    //Imprime la tabla en pantalla mostrando las filas y columnas seleccionadas por el usuario
    public Tabla visualizarParcial(List<String> etiquetasFilas, List<String> etiquetasColumnas) {
        try {
            if (!(etiquetasUsadasf.containsAll(etiquetasFilas)) || !(etiquetasUsadasc.containsAll(etiquetasColumnas))){
                throw new EtiquetasnombreException("Una de las listas de etiquetas no coincide con las que estas presentes en la tabla.");
            }
            Tabla tablaparcial = new Tabla();
            for (Columna<?, ?> columna : tabla) {
                if (etiquetasColumnas == null || etiquetasColumnas.isEmpty() || etiquetasColumnas.contains(columna.getetiqueta())) {
                    tablaparcial.tabla.add(columna);
                }
            }
            tablaparcial.etiquetasUsadasf = new HashSet<>(etiquetasFilas);
            List<Integer> posicionelim = new ArrayList<>();
            int posicionaux = 0;
            for (Fila fila : filas) {
                if (etiquetasFilas == null || etiquetasFilas.isEmpty() || etiquetasFilas.contains(fila.getetiqueta())) {
                    tablaparcial.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                    tablaparcial.etiquetasUsadasf.add( fila.getposicion());
                    if (fila.getposicion() > posicionaux){
                        posicionaux = fila.getposicion();
                    }
                } else {
                    tablaparcial.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                    tablaparcial.etiquetasUsadasf.add( fila.getposicion());
                    posicionelim.add( fila.getposicion());
                }
            }
            for (int pos : posicionelim){
                tablaparcial.eliminarFila(pos, false);
            }

            tablaparcial.contadorEtiquetac = this.contadorEtiquetac;
            tablaparcial.contadorEtiquetaf = posicionaux;
            tablaparcial.etiquetasUsadasc = new HashSet<>(etiquetasColumnas);
            tablaparcial.etiquetasUsadasf.addAll(etiquetasFilas);
            tablaparcial.visualizar();
            return tablaparcial;
        } catch (EtiquetasnombreException e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

     public Tabla visualizarAleatorio(double porcentaje) {
        try {
            if (porcentaje < 0 || porcentaje > 100) {
                throw new ValorNoEsperadoException("El porcentaje debe estar entre 0 y 100.");
            }

            int numFilasSeleccionadas = (int) Math.round((porcentaje / 100) * filas.size());
            Random random = new Random();

            Tabla tablaAleatoria = new Tabla();
            List<Integer> posicionelim = new ArrayList<>();
            List<Integer> posicionaux = new ArrayList<>();
            for (Columna<?, ?> columna : this.tabla) {
                tablaAleatoria.tabla.add(columna.copiaProfunda());
            }
            for (Fila fila : this.filas) {
                tablaAleatoria.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                posicionelim.add(fila.getposicion());
                tablaAleatoria.etiquetasUsadasf.add(fila.getposicion());
                tablaAleatoria.etiquetasUsadasf.add(fila.getetiqueta());
            }
            int maxpos = 0;
            while (posicionaux.size() < numFilasSeleccionadas) {
                int indiceAleatorio = random.nextInt(filas.size());
                int posicionelegida = filas.get(indiceAleatorio).getposicion();

                if (!posicionaux.contains(posicionelegida)) {
                    posicionaux.add(posicionelegida);
                    if (posicionelegida > maxpos){
                        maxpos = posicionelegida;
                    }
                }
            }
            posicionelim.removeAll(posicionaux);
            System.out.println(posicionelim);
            tablaAleatoria.visualizar();
            for (int pos : posicionelim){
                System.out.println(pos);
                tablaAleatoria.eliminarFila(pos, false);
            }
            tablaAleatoria.etiquetasUsadasc = this.etiquetasUsadasc;
            tablaAleatoria.contadorEtiquetac = this.contadorEtiquetac;
            tablaAleatoria.contadorEtiquetaf = maxpos;

            tablaAleatoria.visualizar();
            return tablaAleatoria;
        } catch (ValorNoEsperadoException e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
         
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

    public List<Fila> getFilas() {
        return filas;
    }

    public Tabla copia_p() {
        Tabla copia = new Tabla();

        for (Columna<?, ?> columna : this.tabla) {
            copia.tabla.add(columna.copiaProfunda());
        }

        for (Fila fila : this.filas) {
            copia.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
        }

        copia.contadorEtiquetac = this.contadorEtiquetac;
        copia.contadorEtiquetaf = this.contadorEtiquetaf;
        copia.etiquetasUsadasc = new HashSet<>(this.etiquetasUsadasc);
        copia.etiquetasUsadasf = new HashSet<>(this.etiquetasUsadasf);

        return copia;
    }

    public Tabla concatenar(Tabla tabla2) {
        try{
            if ( tabla2.tabla.size()  != tabla.size() || !comprobarOrden(tabla2)){
                throw new Tablanovalidaexception("Estas dos tablas no son validas para concantenarse");
            }
    
            Tabla nuevaTabla = copia_p();

            Tabla tablaauxiliar = tabla2.copia_p();

            for (int i=0; i< tablaauxiliar.filas.size(); i++){
                List<Object> listaAuxiliar = new ArrayList<>();
                for (Columna<?,?> columna : tablaauxiliar.tabla){
                    listaAuxiliar.add(columna.getdato(i));


                }
                nuevaTabla.agregarfila(listaAuxiliar);

            }
            return nuevaTabla;

        } catch (Tablanovalidaexception e) {
            System.out.println(e.getMessage());               
        }

        return null; 
    }

    public <E,T> Tabla seleccionar(List<E> etiquetaFila, List<T> etiquetaColumna) {
        Tabla seleccion = new Tabla();
        return seleccion;
    }

    /**
     * Busca en la tabla un valor.
     * @param dato a buscar
     * @return boolean muestra por consola si el dato fue encontrado o no con los datos de la fila y columna.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe.
     */

    public boolean buscarDato(Object dato) {
        for (Columna<?, ?> columna : tabla) {
            if (dato instanceof String && columna instanceof Columna_string) {
                if (((Columna_string<?,?>) columna).contieneDato((String) dato)) {
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

    /**
     * Busca un dato en una columna específica, muestra lo encontrado incluido los repetidos.
     * @param dato
     * @param etiquetaColumna
     * @return boolean muestra por consola si el dato fue encontrado o no con los datos de la fila y columna.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe. 
     */
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
    /**
     * Busca un dato en una columna y fila específica, muestra lo encontrado incluido los repetidos.
     * @param etiquetaColumna
     * @param etiquetaFila
     * @param nuevoDato
     * @throws TipoinconsistenteException si el tipo de nuevoDato no coincide con el tipo de la columna.
     * @throws EtiquetasfilaException si la fila con la etiqueta dada no existe.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe.
     */
    public void modificarDato(Object etiquetaColumna, Object etiquetaFila, Object nuevoDato) {
        boolean columnaEncontrada = false;
        boolean filaEncontrada = false;

        try {
            for (Columna<?, ?> columna : tabla) {
                if (columna.getetiqueta().equals(etiquetaColumna)) {
                    columnaEncontrada = true;
                    if ( nuevoDato == null){

                    } else if ((columna instanceof Columna_string && !(nuevoDato instanceof String)) ||
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

    /**
     * Remplaza los valores nulos en todas las columnas por NA. 
     * @param recibe un objeto Tabla 
     * @return void   
     */
    public void reemplazarNullConNA() {
        for (Columna<?, ?> columna : tabla) {
            List<Object> valores = (List<Object>) columna.getDatos(); 
            for (int i = 0; i < valores.size(); i++) {
                if (valores.get(i) == null) {
                    valores.set(i, NA.getInstance()); 
                }
            }
        }
    }

    /**
     * Rellena los datos faltantes (NA) en una columna específica con un nuevo valor.
     * @param etiquetaColumna
     * @param nuevoValor
     * @throws TipoinconsistenteException si el tipo de nuevoValor no coincide con el tipo de la columna.
     */

    public void rellenarDatosFaltantes(String etiquetaColumna, Object nuevoValor) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna)) {
                List<?> datos = columna.getDatos();
                List<Object> valores = (List<Object>) datos;
    
                try {
                    Class<?> tipoColumna = columna.getTipoClase(); 
                    if (!tipoColumna.isInstance(nuevoValor)) {
                        throw new TipoinconsistenteException("El tipo de nuevoValor no coincide con el tipo de la columna.");
                    }

                    for (int i = 0; i < valores.size(); i++) {
                        if (valores.get(i) instanceof NA) {
                            valores.set(i, nuevoValor);
                        }
                    }
    
                } catch (TipoinconsistenteException e) {
                    System.out.println(e.getMessage());               
                }
                break; 
            }
        }
    }
    // cantidad filas
    public int getCantidadFilas() {
        return filas.size();
    }

    // cantidad columnas
    public int getCantidadColumnas() {
        return tabla.size();
    }

    public List<String> getEtiquetasFilas() {
        List<String> etiquetas = new ArrayList<>();
        for (Fila fila : filas) {
            etiquetas.add(filas.get(fila.getposicion()).getetiqueta()); 
        }
        return etiquetas;
    }

    public List<Integer> getPosicionFilas() {
        // Devuelve una lista de enteros con los id de filas
        List<Integer> posicionf = new ArrayList<>();
        for (Fila fila : filas) {
            posicionf.add(fila.getposicion());  
        }
        return posicionf;
    }

    // cuenta nulls
    private int contarValoresNulos(int indiceColumna) {
        int contador = 0;
        Columna<?, ?> columna = tabla.get(indiceColumna);
        for (Object dato : columna.getDatos()) {
            if (dato == null) {
                contador++;
            }
        }
        return contador;
    }

    // cuenta "NA"s
    private int contarValoresNA(int indiceColumna) {
        int contador = 0;
        Columna<?, ?> columna = tabla.get(indiceColumna);
        for (Object dato : columna.getDatos()) {
            if (dato instanceof NA) {
                contador++;
            }
        }
        return contador;
    }

    
    /**
     * Muestra un cuadro de información con los siguientes datos de cada columna:
     * - Columna: etiqueta de la columna
     * - Tipo de Dato: tipo de dato de la columna   
     * - Cantidad de Filas: cantidad de filas en la tabla
     * - Registros: cantidad de registros válidos (no nulos ni NA) en la columna
     * - Valores Nulos: cantidad de valores nulos en la columna
     * - Valores NA: cantidad de valores NA en la columna      
     */
    public void mostrarCuadroInformacion() {    
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", 
                          "Columna", "Tipo de Dato", "Cantidad de Filas", "Registros", "Valores Nulos", "Valores NA");
        System.out.println("-----------------------------------------------------------------------------------------");
    
        for (int i = 0; i < getCantidadColumnas(); i++) {
            Columna<?, ?> columna = tabla.get(i);
            String nombreColumna = columna.getetiqueta().toString();
            Class<?> tipoDato = columna.getTipoClase();
            int cantidadFilas = getCantidadFilas();
            int registros = cantidadFilas - contarValoresNulos(i) - contarValoresNA(i); 
            int valoresNulos = contarValoresNulos(i);
            int valoresNA = contarValoresNA(i);
    
            System.out.printf("%-15s %-20s %-15d %-15d %-15d %-15d%n", 
                              nombreColumna, tipoDato.getSimpleName(), cantidadFilas, registros, valoresNulos, valoresNA);
        }

        List<String> etiquetasFilas = getEtiquetasFilas();
        int maxEtiquetas = 5;  

        String etiquetasString;
        if (etiquetasFilas.size() > maxEtiquetas) {
        etiquetasString = "[" + String.join(", ", etiquetasFilas.subList(0, maxEtiquetas)) + ",....., " + etiquetasFilas.get(etiquetasFilas.size() - 1) + "]";
        } else {
        etiquetasString = etiquetasFilas.toString();
        }
        System.out.println("\nEtiquetas de Filas: " + etiquetasString);
        System.out.println();
    }

    public void mostrarEtiquetasColumnas() {
        System.out.print("Etiquetas de columnas: ");
        for (Columna<?, ?> columna : tabla) {
            System.out.print(columna.getetiqueta() + " ");
        }
        System.out.println();  
    }

    public void eliminarColumna(String etiquetaColumna) {
        boolean encontrada = false;
        for (int i = 0; i < tabla.size(); i++) {
            if (tabla.get(i).getetiqueta().equals(etiquetaColumna)) {
                tabla.remove(i);
                etiquetasUsadasc.remove(etiquetaColumna);
                encontrada = true;
                System.out.println("Columna con etiqueta '" + etiquetaColumna + "' eliminada.");
                break;
            }
        }
        if (!encontrada) {
            throw new IllegalArgumentException("La columna con etiqueta '" + etiquetaColumna + "' no fue encontrada.");
        }
    }

    public void eliminarColumna(int indiceColumna) {
        if (indiceColumna < 0 || indiceColumna >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango.");
        }
        // Elimina la columna en el índice especificado sin considerar etiquetas
        Columna<?, ?> columnaEliminada = tabla.remove(indiceColumna);
        System.out.println("Columna eliminada en el índice " + indiceColumna + " con etiqueta: " + columnaEliminada.getetiqueta());
    }
    
    public void eliminarFila(int indiceFila) {
        eliminarFila(indiceFila, true);
    }

    public void eliminarFila(int indiceFila, Boolean mostrarMensaje) {
        if (!etiquetasUsadasf.contains(indiceFila)) {
            throw new TipoDeEtiquetaInvalidoException("Índice de fila no contenido en la tabla");
        }
        int indice = 0;
        for (Fila fila : filas){
            if (fila.getposicion() == indiceFila){
                indice = filas.indexOf(fila);
                etiquetasUsadasf.remove(fila.getetiqueta());
                break;
            }
        }
        filas.remove(indice);
        for (Columna<?, ?> columna : tabla) {
            columna.getDatos().remove(indice);  // Elimina el dato de esa fila en cada columna
        }
        etiquetasUsadasf.remove(indiceFila);
        if(mostrarMensaje) {
            System.out.println("Fila en el índice " + indiceFila + " eliminada.");
        }
    }
    
    public void eliminarFila(String etiquetaFila) {
        boolean encontrada = false;
        for (int i = 0; i < filas.size(); i++) {
            if (filas.get(i).getetiqueta().equals(etiquetaFila)) {
                filas.remove(i);
                etiquetasUsadasf.remove(etiquetaFila);
                for (Columna<?, ?> columna : tabla) {
                    columna.getDatos().remove(i);  // Elimina el dato de esa fila en cada columna
                }
                encontrada = true;
                System.out.println("Fila con etiqueta '" + etiquetaFila + "' eliminada.");
                break;
            }
        }
        if (!encontrada) {
            throw new IllegalArgumentException("La fila con etiqueta '" + etiquetaFila + "' no fue encontrada.");
        }
    }
    
    public void ordenarTabla(List<String> etiquetasColumnas, List<Boolean> ordenAscendente) {
        if (etiquetasColumnas.size() != ordenAscendente.size()) {
            throw new IllegalArgumentException("La cantidad de columnas y de órdenes deben coincidir.");
        }
    
        // Configurar el comparador para ordenar filas según las columnas especificadas
        Comparator<Fila> comparadorFinal = (fila1, fila2) -> 0;
    
        for (int i = 0; i < etiquetasColumnas.size(); i++) {
            String etiquetaColumna = etiquetasColumnas.get(i);
            boolean ascendente = ordenAscendente.get(i);
    
            // Obtener la columna correspondiente
            Columna<?, ?> columna = obtenerColumnaPorEtiqueta(etiquetaColumna);
            if (columna == null) {
                throw new IllegalArgumentException("Columna no encontrada: " + etiquetaColumna);
            }
    
            Comparator<Fila> comparadorColumna = (fila1, fila2) -> {
                int indexFila1 = filas.indexOf(fila1);
                int indexFila2 = filas.indexOf(fila2);
    
                Object dato1 = columna.getdato(indexFila1);
                Object dato2 = columna.getdato(indexFila2);
    
                if (dato1 == null || dato1 instanceof NA) return ascendente ? 1 : -1;
                if (dato2 == null || dato2 instanceof NA) return ascendente ? -1 : 1;
    
                if (dato1 instanceof Comparable && dato2 instanceof Comparable) {
                    return ascendente ? ((Comparable) dato1).compareTo(dato2) : ((Comparable) dato2).compareTo(dato1);
                } else {
                    throw new ClassCastException("Datos no comparables en la columna: " + etiquetaColumna);
                }
            };
    
            comparadorFinal = comparadorFinal.thenComparing(comparadorColumna);
        }
    
        // Realizar una copia temporal de las filas y ordenar esa lista
        List<Fila> filasOrdenadas = new ArrayList<>(filas);
        Collections.sort(filasOrdenadas, comparadorFinal);
    
        // Actualizar las filas en la tabla con el nuevo orden
        filas.clear();
        filas.addAll(filasOrdenadas);
    
        System.out.println("Ordenamiento completado. Tabla actualizada:");
        this.visualizar();  // Muestra la tabla para verificar el resultado
    }
    
    

    public List<Object> obtenerFilaPorEtiqueta(Object etiquetaFila) {
        for (Fila fila : filas) {
            // Verifica que la etiqueta no sea nula antes de usar equals
            if (fila.getetiqueta() != null && fila.getetiqueta().equals(etiquetaFila)) {
                List<Object> datosFila = new ArrayList<>();
                for (Columna<?, ?> columna : tabla) {
                    datosFila.add(columna.getdato(fila.getIndice()));
                }
                return datosFila;
            }
        }
        throw new IllegalArgumentException("Fila no encontrada con la etiqueta: " + etiquetaFila);
    }
    
    public List<Object> obtenerFilaPorPosicion(int posicion) {
        for (Fila fila : filas) {
            // Verifica que la posicion no sea nula antes de usar equals
            if (fila.getposicion() == posicion) {
                List<Object> datosFila = new ArrayList<>();
                for (Columna<?, ?> columna : tabla) {
                    datosFila.add(columna.getdato(fila.getposicion()));
                }
                return datosFila;
            }
        }
        throw new IllegalArgumentException("Fila no encontrada con la posicion: " + posicion);
    }

    public <E> Columna<?, ?> obtenerColumnaPorEtiqueta(E etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna)) {
                return columna;
            }
        }
        throw new IllegalArgumentException("Columna no encontrada: " + etiquetaColumna);
    }

    public List<?> obtenerColumnaPorEtiquetaIndex(Object etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna)) {
                return columna.getDatos(); // Devuelve todos los datos en la columna
            }
        }
        throw new IllegalArgumentException("Columna no encontrada con la etiqueta: " + etiquetaColumna);
    }
    
    public Object obtenerCelda(Object etiquetaFila, Object etiquetaColumna) {
        Fila filaEncontrada = null;
        for (Fila fila : filas) {
            if (fila.getetiqueta() != null && fila.getetiqueta().equals(etiquetaFila)) {
                filaEncontrada = fila;
                break;
            }
        }
    
        if (filaEncontrada == null) {
            throw new IllegalArgumentException("Fila no encontrada con la etiqueta: " + etiquetaFila);
        }
    
        Columna<?, ?> columnaEncontrada = null;
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta() != null && columna.getetiqueta().equals(etiquetaColumna)) {
                columnaEncontrada = columna;
                break;
            }
        }
    
        if (columnaEncontrada == null) {
            throw new IllegalArgumentException("Columna no encontrada con la etiqueta: " + etiquetaColumna);
        }
    
        return columnaEncontrada.getdato(filaEncontrada.getIndice());
    }


    
}
