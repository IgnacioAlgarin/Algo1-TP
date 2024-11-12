package Tabla;
import Archivo.Archivo;
import Columna.*;
import Fila.Fila;
import Filtro.Filtro;
import NA.NA;
import Operaciones.OperacionesColumna;
import excepciones.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Clase que representa una tabla de datos, compuesta por columnas y filas.
 * Cada columna puede contener datos de un tipo específico, como String, Number o Boolean.
 * Cada fila puede contener una etiqueta y una posición.
 */

public class Tabla  implements Filtro{

    private List<Columna<?, ?>> tabla;
    private List <Fila> filas;
    private int contadorEtiquetac = 0;
    private int contadorEtiquetaf = 0;
    private Set <Object> etiquetasUsadasc;
    private Set <Object> etiquetasUsadasf;


    // Constructores
    
    /**
     * Crea una tabla vacía.
     */
    public Tabla() {
        tabla = new ArrayList<>();
        filas = new ArrayList<>();
        etiquetasUsadasc = new HashSet<>();
        etiquetasUsadasf = new HashSet<>(); 
    }

    //Constructor a partir de una estructura lineal
    public Tabla(List<?> datos){
        tabla = new ArrayList<>();
        filas = new ArrayList<>();
        etiquetasUsadasc = new HashSet<>();
        etiquetasUsadasf = new HashSet<>();
        agregarColumna(datos);
    }

    //Constructor a partir de una estructura nativa de 2 dimensiones
    public Tabla(Object[][] datos){

        try{
            if (datos == null || datos.length == 0 || datos[0].length == 0) {
                throw new ListaDatosVaciaException("El arreglo de datos no puede estar vacío");
            }
            
            this.tabla = new ArrayList<>();
            this.filas = new ArrayList<>();
            this.etiquetasUsadasc = new HashSet<>();
            this.etiquetasUsadasf = new HashSet<>();

            int numFilas = datos.length; 
            int numColumnas = datos[0].length; 

            for (int j = 0; j < numColumnas; j++) {
                List<Object> datosColumna = new ArrayList<>();
                for (int i = 0; i < numFilas; i++) {
                    if (j < datos[i].length) {
                        datosColumna.add(datos[i][j]);
                    } else {
                        datosColumna.add(null);
                    }
                }
                agregarColumna(datosColumna);
            }
        } catch (ListaDatosVaciaException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Crea una tabla desde otra tabla
     * @param otraTabla
     */
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

    /**
     * Crea una tabla a partir de un archivo CSV.
     * @param path Ruta del archivo CSV.
     * @param sep Separador de columnas.
     * @param header Indica si la primera fila contiene los nombres de las columnas.
     */
    public Tabla (String path, String sep, Boolean header){
        try {
            Tabla tablaImportada = new Tabla(Archivo.importar(path, sep, header));
            this.tabla = tablaImportada.tabla;
            this.filas = tablaImportada.filas;
            this.contadorEtiquetac = tablaImportada.contadorEtiquetac;
            this.contadorEtiquetaf = tablaImportada.contadorEtiquetaf;
            this.etiquetasUsadasc = tablaImportada.etiquetasUsadasc;
            this.etiquetasUsadasf = tablaImportada.etiquetasUsadasf;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //Metodos privados

    /**
     * Genera una etiqueta de columna
     * @return nueva etiqueta
     */
    private Integer generarEtiquetac(){
        int nuevaetiqueta;
        do{
            nuevaetiqueta = contadorEtiquetac++;
        } while (etiquetasUsadasc.contains(nuevaetiqueta));
        return nuevaetiqueta;
    }
    /**
     * Genera una etiqueta de fila
     * @return nueva etiqueta
     */
    private Integer generarEtiquetaf() {
        int nuevaetiqueta;
        do{
            nuevaetiqueta = contadorEtiquetaf++;
        } while (etiquetasUsadasf.contains(nuevaetiqueta));
        etiquetasUsadasf.add(nuevaetiqueta);
        return nuevaetiqueta;
    }
    
    /**
     * Inicializa las filas de la tabla
     * @param tamaño size de filas
     * @param etiquetaf lista de etiquetas de fila
     * @throws EtiquetasfilaException si el tamaño de la lista de etiquetas de fila no coincide con el tamaño de la columna
     * @throws EtiquetasfilaException si el tamaño de la columna no coincide con la cantidad de filas
     * @throws EtiquetasfilaException si la lista de etiquetas de fila proporcionada no coincide con la presente en la tabla
     */
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
                Fila nuevafila = new Fila(generarEtiquetaf());
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
    
    /**
     * Comparar si los elementos de la lista son de tipo String
     * @param etiquetaf
     * @return
     */
    private boolean comprobarstring(List<?> etiquetaf) {
        for (Object etiqueta: etiquetaf){
            if (!(etiqueta instanceof String)){
                return false;
            }
        }
        return true;
    }
    /**
     * Comprueba si las columnas de dos tablas tienen el mismo orden
     * @param tabla2
     * @return boolean
     */
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

    /**
     * Agrega una nueva columna a la tabla.
     * @param columna Lista de datos de la columna.
     * @param etiqueta Etiqueta de la columna.
     * @param etiquetaf  Lista de etiquetas de fila. 
     * @throws ListaDatosVaciaException si la lista de datos de la columna está vacía.
     * @throws TipoDeEtiquetaInvalidoException si la etiqueta no es de tipo entero o string. 
     * @throws EtiquetaEnUsoException si la etiqueta ya se encuentra en uso.
     * @throws TipoinconsistenteException si la columna contiene tipos de datos distintos.
     * @throws TipoNoSoportadoException si el tipo de dato no es soportado.
     * @throws EtiquetasfilaException si hay elementos de distinto tipo en la lista de etiquetas de fila.
     */     
    public <E> void agregarColumna(List<?> columna, E etiqueta, List<String> etiquetaf) {
        try {
            if (columna == null || columna.isEmpty()) {
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
                    } else if ((tipoPrimero != Number.class && !tipoPrimero.isAssignableFrom(dato.getClass())) || (tipoPrimero == Number.class && !tipoPrimero.isAssignableFrom(dato.getClass()))) {
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

    /**
     * Agrega una nueva columna a la tabla con etiqueta de tipo entero.
     * @param columna Lista de datos de la columna.
     * @param etiquetaColumna  Etiqueta de la columna int.   
     */
    public void agregarColumna(List<?> columna, int etiquetaColumna) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, etiquetaColumna, listaf);                
    }

    /**
     * Agrega una nueva columna a la tabla con etiqueta de tipo string.
     * @param etiqueta lista de datos de la columna.
     * @param etiquetaColumna Etiqueta de la columna string.
     */
    public void agregarColumna(List<?> columna, String etiqueta) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, etiqueta, listaf);                
    }
    /**
     * Agrega una nueva columna sin especificar tipo de etiqueta.
     * @param columna lista de datos de la columna.
     */
    public void agregarColumna(List<?> columna) {
        List <String> listaf= new ArrayList<>();
        agregarColumna(columna, generarEtiquetac(), listaf);                
    }
    /**
     * Agrega una nueva fila a la tabla.
     * @param etiqueta etiqueta de la fila de tipo String.
     * @throws EtiquetaEnUsoException si la etiqueta ya se encuentra en uso.
     */
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
    /**
     * Agrega una nueva fila a la tabla sin especificar una etiqueta.
     * <p>
     * Este método crea una fila en la tabla con una etiqueta nula. Utiliza el método 
     * sobrecargado {@link #agregarfila(String)} para llevar a cabo la adición sin asignar
     * una etiqueta específica.
     * </p>
     * 
     * @see #agregarfila(String)
     */
    public void agregarfila(){
        String etiqueta = null;
        agregarfila(etiqueta);   
    }

    /**
     * Agrega una nueva fila a la tabla con datos y etiqueta de fila de tipo String.
     * @param etiqueta etiqueta de la fila de tipo String.
     * @param datos lista de datos de la fila.
     * @throws EtiquetaEnUsoException si la etiqueta ya se encuentra en uso.
     * @throws DiferentetamañoException si la cantidad de datos a insertar no coincide con la cantidad de columnas.
     * @throws TipoinconsistenteException si no coinciden los tipos de datos con los ya establecidos en las columnas.
     * @throws EtiquetaEnUsoException si la etiqueta ya se encuentra en uso.
     */
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
    
    /**
     * Agrega una nueva fila a la tabla con datos y sin etiqueta de fila
     * @param datos lista de datos de la fila.
     */
    public void agregarfila(List<?> datos) {
        String etiqueta = null;
        agregarfila(etiqueta, datos);

    }
    
    /**
     * Muestra la tabla en pantalla con un limite de 8 columnas y 30 filas.
     */
    public void visualizar() {
        visualizar(30, 8);
    }
    
    
    /**
     * Muestra la tabla en pantalla con un limite establecido por el usuario de x filas y x columnas.
     */
    public void visualizar(int limitf, int limitc) {
        StringBuilder filastring = new StringBuilder();
        filastring.append("+-------+----------+");
        int cont = 0;
        for (Columna<?, ?> columna : tabla) {
            if (cont < limitc) {
                filastring.append("----------+");
                cont++;
            } else {
                break;
            }
        }
        filastring.append("\n");
        filastring.append(String.format("| %-5s | %-8s |", "ID", "Etiqueta"));
        cont = 0;
        for (Columna<?, ?> columna : tabla) {
            if (cont < limitc) {
                String etiqueta = String.format("%-8s", columna.getetiqueta());
                if (etiqueta.length() > 8) {
                    etiqueta = etiqueta.substring(0, 6) + "..";
                }
                filastring.append(String.format(" %-8s |", etiqueta));
                cont++;
            } else {
                break;
            }
        }
        filastring.append("\n");
        filastring.append("+-------+----------+");
        cont = 0;
        for (Columna<?, ?> columna : tabla) {
            if (cont < limitc) {
                filastring.append("----------+");
                cont++;
            } else {
                break;
            }
        }
        filastring.append("\n");
        for (int f = 0; f < filas.size() && f < limitf; f++) {
            String posicionLimitada = String.format("%-5s", filas.get(f).getposicion());
            if (posicionLimitada.length() > 5) {
                posicionLimitada = posicionLimitada.substring(0, 3) + "..";
            }
            String etiquetaLimitada = String.format("%-8s", filas.get(f).getetiqueta());
            if (etiquetaLimitada.length() > 8) {
                etiquetaLimitada = etiquetaLimitada.substring(0, 6) + "..";
            }
            filastring.append(String.format("| %-5s | %-8s |", posicionLimitada, etiquetaLimitada));
            cont = 0;
            for (Columna<?, ?> columna : tabla) {
                if (cont < limitc) {
                    String dato = String.format("%-8s", columna.getdato(f));
                    if (dato.length() > 8) {
                        dato = dato.substring(0, 6) + "..";
                    }
                    filastring.append(String.format(" %-8s |", dato));
                    cont++;
                } else {
                    break;
                }
            }
            filastring.append("\n");
            filastring.append("+-------+----------+");
            cont = 0;
            for (Columna<?, ?> columna : tabla) {
                if (cont < limitc) {
                    filastring.append("----------+");
                    cont++;
                } else {
                    break;
                }
            }
            filastring.append("\n");
        }
        System.out.println(filastring);
    }

    /**
     * Muestra la tabla en pantalla con las filas y columnas seleccionadas por el usuario
     * @param etiquetasFilas
     * @param etiquetasColumnas
     * @return tabla con las filas y columnas seleccionadas
     * @throws EtiquetasnombreException si una de las listas de etiquetas no coincide con las que estas presentes en la tabla.
     */
    public Tabla visualizarParcial(List<String> etiquetasFilas, List<String> etiquetasColumnas) {
        try {
            if (!(etiquetasUsadasf.containsAll(etiquetasFilas)) || !(etiquetasUsadasc.containsAll(etiquetasColumnas))){
                throw new EtiquetasnombreException("Una de las listas de etiquetas no coincide con las que estas presentes en la tabla.");
            }
            Tabla tablaaux = this.copia_p();
            Tabla tablaparcial = new Tabla();
            for (Columna<?, ?> columna : tablaaux.tabla) {
                if (etiquetasColumnas == null || etiquetasColumnas.isEmpty() || etiquetasColumnas.contains(columna.getetiqueta())) {
                    tablaparcial.tabla.add(columna);
                }
            }
            tablaparcial.etiquetasUsadasf = new HashSet<>(etiquetasFilas);
            List<Integer> posicionelim = new ArrayList<>();
            int posicionaux = 0;
            for (Fila fila : tablaaux.filas) {
                if (etiquetasFilas == null || etiquetasFilas.isEmpty() || etiquetasFilas.contains(fila.getetiqueta())) {
                    tablaparcial.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                    tablaparcial.etiquetasUsadasf.add( fila.getposicion());
                    if (fila.getposicion() > posicionaux){
                        posicionaux = fila.getposicion();
                    }
                } else {
                    tablaparcial.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                    posicionelim.add( tablaaux.filas.indexOf(fila));
                }
            }
    
            for ( int i=posicionelim.size()-1; i>-1 ; i-- ){
                tablaparcial.eliminarFila(posicionelim.get(i), false);
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
        return new Tabla();
    }
   
    /**
     * Muestra una tabla con un porcentaje de filas seleccionadas al azar.
     * @param porcentaje de filas a seleccionar
     * @return tabla con el porcentaje de filas seleccionadas
     * @throws ValorNoEsperadoException si el porcentaje no esta entre 0 y 100
     */
    public Tabla visualizarAleatorio(double porcentaje) {
        try {
            if (porcentaje < 0 || porcentaje > 100) {
                throw new ValorNoEsperadoException("El porcentaje debe estar entre 0 y 100.");
            }
    
            int numFilasSeleccionadas = (int) Math.round((porcentaje / 100) * filas.size());
            Random random = new Random();
            Tabla tablaaux = this.copia_p();
            Tabla tablaAleatoria = new Tabla();
            List<Integer> posicionelim = new ArrayList<>();
            List<Integer> posicionaux = new ArrayList<>();
            for (Columna<?, ?> columna : tablaaux.tabla) {
                tablaAleatoria.tabla.add(columna.copiaProfunda());
            }
            for (Fila fila : tablaaux.filas) {
                tablaAleatoria.filas.add(new Fila(fila.getetiqueta(), fila.getposicion()));
                posicionelim.add(tablaaux.filas.indexOf(fila));
                tablaAleatoria.etiquetasUsadasf.add(fila.getposicion());
                tablaAleatoria.etiquetasUsadasf.add(fila.getetiqueta());
            }
            int maxpos = 0;
            while (posicionaux.size() < numFilasSeleccionadas) {
                int indiceAleatorio = random.nextInt(tablaaux.filas.size());
                int posicionelegida = tablaaux.filas.get(indiceAleatorio).getposicion();
    
                if (!posicionaux.contains(indiceAleatorio)) {
                    posicionaux.add(indiceAleatorio);
                    if (posicionelegida > maxpos){
                        maxpos = posicionelegida;
                    }
                }
            }
            posicionelim.removeAll(posicionaux);
    
            for ( int i = posicionelim.size() - 1; i > -1 ; i-- ){
                tablaAleatoria.eliminarFila(posicionelim.get(i), false);
            }
            tablaAleatoria.etiquetasUsadasc = this.etiquetasUsadasc;
            tablaAleatoria.contadorEtiquetac = this.contadorEtiquetac;
            tablaAleatoria.contadorEtiquetaf = maxpos;
    
            tablaAleatoria.visualizar();
            return tablaAleatoria;
        } catch (ValorNoEsperadoException e){
            System.out.println("Error: " + e.getMessage());
        }
        return new Tabla();
    }

    /**
     * Muestra las primeras x cantidad de filas solicitadas por el usuario
     * @param cant cantidad de filas a mostrar desde el inicio
     * @throws ValorNoEsperadoException si la cantidad de filas es menor o igual a 0
     */ 
    public void head(int cant){
        try {
            if (cant <= 0){
                throw new ValorNoEsperadoException("Por favor inserte una cantidad de filas valida");
            }
            Tabla tablaaux = this.copia_p();
            if (cant > tablaaux.filas.size()){
                cant = tablaaux.filas.size() ;
            }
            for (int i = tablaaux.filas.size()-1; i > cant - 1; i-- ){
                tablaaux.eliminarFila(i, false);
            }
            tablaaux.visualizar();
        } catch (ValorNoEsperadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
   
    /**
     * Muestra las ultimas x cantidad de filas solicitadas por el usuario
     * @param cant cantidad de filas a mostrar desde el final
     * @throws ValorNoEsperadoException si la cantidad de filas es menor o igual a 0
     */
    public void tail(int cant){
        try {
            if (cant <= 0){
                throw new ValorNoEsperadoException("Por favor inserte una cantidad de filas valida");
            }
            if (cant > filas.size()){
                cant = filas.size() ;
            }
            Tabla tablaaux = this.copia_p();

            for (int i = tablaaux.filas.size() - cant-1; i > -1; i-- ){
                tablaaux.eliminarFila(i, false);
            }
            tablaaux.visualizar();
        } catch (ValorNoEsperadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Muestra un resumen de la tabla en la consola, incluyendo información estadística
     * de cada columna como el tipo de datos, la cantidad de valores no nulos, nulos,
     * el valor mínimo y el valor máximo.
     */
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

    
    /**
     * Obtiene una columna de tipo numérico según la etiqueta proporcionada.
     *
     * @param etiquetaColumna La etiqueta de la columna que se desea obtener.
     * @return La columna numérica correspondiente a la etiqueta dada.
     * @throws IllegalArgumentException si la columna con la etiqueta especificada
     *         no es numérica o no existe en la tabla.
     */
    public Columna<Number, ?> obtenerColumnaNumerica(String etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna) && columna instanceof Columna_num) {
                // Casting seguro si la columna es de tipo numérico
                return (Columna<Number, ?>) columna;
            }
        }
        throw new IllegalArgumentException("Columna numérica con etiqueta " + etiquetaColumna + " no encontrada.");
    }

    /**
     * Muestra un resumen de operaciones estadísticas para una columna numérica específica.
     *
     * @param etiquetaColumna La etiqueta de la columna para la cual se desea obtener el resumen de operaciones.
     * @throws IllegalArgumentException si la columna especificada no es numérica o no existe.
     */
    public void mostrarResumenOperaciones(String etiquetaColumna) {
        try {
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
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //getters y setter

    /**
     * Obtiene la lista de columnas de la tabla.
     * @return lista de columnas
     */
    public List<Columna<?, ?>> getColumnas() {
        return tabla;
    }
    /**
     * Obtiene la lista de filas de la tabla.
     * @return
     */
    public List<Fila> getFilas() {
        return filas;
    }
    /**
     * Realiza una copia de la tabla
     * @return copia de la tabla
     */
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
    /**
     * Concatena dos tablas
     * @param tabla2
     * @return tabla concatenada
     * @throws Tablanovalidaexception si las tablas no son validas para concatenarse
     */
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

    /**
     * Seleccion de la tabla
     * @param etiquetaFila las etiquetas de la fila a seleccionar
     * @param etiquetaColumna las etiquetas de la columna a seleccionar
     * @return tabla con la fila seleccionada
     * @throws EtiquetasfilaException si la fila con la etiqueta dada no existe.
     */
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
                throw new ColumnaNoValidaException("Error: La columna '" + etiquetaColumna + "' no existe.");
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
    /**
     * Cuenta la cantidad de filas
     * @return cantidad de filas
     */
    public int getCantidadFilas() {
        return filas.size();
    }
    /**
     * Cuenta la cantidad de columnas
     * @return cantidad de columnas
     */
    public int getCantidadColumnas() {
        return tabla.size();
    }

    /**
     * Obtiene una lista con las etiquetas de todas las filas.
     *
     * @return Lista de etiquetas de las filas en la tabla.
     */
    public List<String> getEtiquetasFilas() {
        List<String> etiquetas = new ArrayList<>();
        for (Fila fila : filas) {
            etiquetas.add(fila.getetiqueta());  // Agrega la etiqueta de cada fila
        }
        return etiquetas;
    }

    /**
     * Obtiene una lista con las posiciones de todas las filas.
     *
     * @return Lista de posiciones (ID) de las filas en la tabla.
     */
    public List<Integer> getPosicionFilas() {
        List<Integer> posicionf = new ArrayList<>();
        for (Fila fila : filas) {
            posicionf.add(fila.getposicion());  // Agrega la posición de cada fila
        }
        return posicionf;
    }

    /**
     * Obtiene una lista con las etiquetas de todas las columnas.
     *
     * @return Lista de etiquetas de las columnas en la tabla.
     */
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

    /**
     * Cuenta el número de valores de tipo `NA` en una columna específica de la tabla.
     *
     * @param indiceColumna el índice de la columna en la cual contar los valores de tipo `NA`.
     * @return el número de valores `NA` encontrados en la columna especificada.
     */
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

    /**
     * Muestra las etiquetas de todas las columnas de la tabla en la consola.
     * Si no hay columnas en la tabla, muestra un mensaje indicando que no existen etiquetas de columnas.
     */
    public void mostrarEtiquetasColumnas() {
        if (tabla.isEmpty()) {
            System.out.println("No existen etiquetas de columnas.");
            return;
        }

        System.out.print("Etiquetas de columnas: ");
        for (Columna<?, ?> columna : tabla) {
            System.out.print(columna.getetiqueta() + " ");
        }
        System.out.println();  // Salto de línea al final
    }

    /**
     * Elimina una columna de la tabla en función de su etiqueta.
     * @param etiquetaColumna La etiqueta de la columna que se desea eliminar.
     * @throws IllegalArgumentException si no se encuentra una columna con la etiqueta especificada.
     */
    public void eliminarColumna(String etiquetaColumna) {
        try {
            boolean encontrada = false;

            for (int i = 0; i < tabla.size(); i++) {
                if (tabla.get(i).getetiqueta().equals(etiquetaColumna)) {
                    tabla.remove(i);  // Elimina la columna del índice encontrado
                    etiquetasUsadasc.remove(etiquetaColumna);  // Actualiza el conjunto de etiquetas usadas
                    encontrada = true;
                    System.out.println("Columna con etiqueta '" + etiquetaColumna + "' eliminada.");
                    break;
                }
            }

            if (!encontrada) {
                throw new IllegalArgumentException("La columna con etiqueta '" + etiquetaColumna + "' no fue encontrada.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina una columna de la tabla en función de su índice.
     * @param indiceColumna El índice de la columna que se desea eliminar.
     * @throws IndexOutOfBoundsException si el índice de la columna está fuera del rango de columnas existentes.
     */
    public void eliminarColumna(int indiceColumna) {
        try {
            if (indiceColumna < 0 || indiceColumna >= tabla.size()) {
                throw new IndexOutOfBoundsException("Índice de columna fuera de rango.");
            }
        
            // Elimina la columna en el índice especificado
            Columna<?, ?> columnaEliminada = tabla.remove(indiceColumna);
            System.out.println("Columna eliminada en el índice " + indiceColumna + " con etiqueta: " + columnaEliminada.getetiqueta());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina una fila de la tabla en función de su índice.
     * La fila y sus datos en todas las columnas serán eliminados.
     * @param indiceFila El índice de la fila que se desea eliminar.
     */
    public void eliminarFila(int indiceFila) {
        eliminarFila(indiceFila, true);
    }

    /**
     * Elimina una fila de la tabla en función de su índice.
     * La fila y sus datos en todas las columnas serán eliminados.
     *
     * @param indiceFila El índice de la fila que se desea eliminar.
     * @param mostrarMensaje Indica si se debe mostrar un mensaje de confirmación tras la eliminación.
     * @throws IndexOutOfBoundsException si el índice está fuera del rango de las filas existentes.
     */
    public void eliminarFila(int indiceFila, Boolean mostrarMensaje) {
        try {
            if (indiceFila < 0 || indiceFila >= filas.size()) {
                throw new IndexOutOfBoundsException("Índice de fila fuera de rango.");
            }

            // Remueve la etiqueta asociada a la fila eliminada
            etiquetasUsadasf.remove(filas.get(indiceFila).getetiqueta());
            etiquetasUsadasf.remove(filas.get(indiceFila).getposicion());
            filas.remove(indiceFila);

            // Elimina los datos de esa fila en cada columna
            for (Columna<?, ?> columna : tabla) {
                columna.getDatos().remove(indiceFila);
            }

            // Mostrar mensaje de confirmación si se indica
            if (mostrarMensaje) {
                System.out.println("Fila en el índice " + indiceFila + " eliminada.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una fila de la tabla basada en su etiqueta. 
     * La fila y sus datos en todas las columnas serán eliminados.
     *
     * @param etiquetaFila La etiqueta de la fila que se desea eliminar.
     * @throws FilaNoValidaException si la fila con la etiqueta especificada no existe.
     */
    public void eliminarFila(String etiquetaFila) {
        try {
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
            
            // Lanzar excepción si la fila no se encontró
            if (!encontrada) {
                throw new FilaNoValidaException("La fila con etiqueta '" + etiquetaFila + "' no fue encontrada.");
            }
        } catch (FilaNoValidaException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    
    /**
     * Ordena las filas de la tabla en función de las columnas especificadas y el orden ascendente o descendente indicado.
     *
     * @param etiquetasColumnas Lista de etiquetas de las columnas por las que se desea ordenar.
     * @param ordenAscendente Lista de booleanos que indica el orden para cada columna (true para ascendente, false para descendente).
     * @throws IllegalArgumentException si la cantidad de etiquetas de columnas y órdenes no coinciden o si alguna columna no existe.
     * @throws ClassCastException si los datos en una columna no son comparables.
     */
    public void ordenarTabla(List<String> etiquetasColumnas, List<Boolean> ordenAscendente) {
        if (etiquetasColumnas.size() != ordenAscendente.size()) {
            throw new IllegalArgumentException("La cantidad de columnas y de órdenes deben coincidir.");
        }
    
        // Crear comparador compuesto de columnas y órdenes
        Comparator<Fila> comparadorFinal = (fila1, fila2) -> 0;
        
        for (int i = 0; i < etiquetasColumnas.size(); i++) {
            String etiquetaColumna = etiquetasColumnas.get(i);
            boolean ascendente = ordenAscendente.get(i);
    
            // Obtener la columna correspondiente
            Columna<?, ?> columna = obtenerColumnaPorEtiqueta(etiquetaColumna);
            Comparator<Fila> comparadorColumna = (fila1, fila2) -> {
                Object dato1 = columna.getdato(fila1.getposicion());
                Object dato2 = columna.getdato(fila2.getposicion());
    
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
    
        // Ordenar filas usando el comparador compuesto
        filas.sort(comparadorFinal);
    
        // Actualizar las columnas con el nuevo orden de las filas
        for (Columna<?, ?> columna : tabla) {
            List<Object> datosOrdenados = new ArrayList<>();
            for (Fila fila : filas) {
                datosOrdenados.add(columna.getdato(fila.getposicion()));
            }
            columna.setDatos(datosOrdenados); // Asegúrate de tener un método setDatos en la clase Columna
        }
        
        System.out.println("Tabla ordenada correctamente por " + etiquetasColumnas);
    }

    /**
     * Obtiene los datos de una fila en función de su etiqueta.
     *
     * @param etiquetaFila La etiqueta de la fila que se desea obtener.
     * @return Lista de objetos que representan los datos de la fila con la etiqueta especificada.
     * @throws IllegalArgumentException si no se encuentra una fila con la etiqueta especificada.
     */
    public List<Object> obtenerFilaPorEtiqueta(Object etiquetaFila) {
        for (Fila fila : filas) {
            // Verifica que la etiqueta no sea nula antes de usar equals
            if (fila.getetiqueta() != null && fila.getetiqueta().equals(etiquetaFila)) {
                List<Object> datosFila = new ArrayList<>();
                for (Columna<?, ?> columna : tabla) {
                    datosFila.add(columna.getdato(fila.getposicion()));
                }
                return datosFila;
            }
        }
        throw new IllegalArgumentException("Fila no encontrada con la etiqueta: " + etiquetaFila);
    }
    
    /**
     * Obtiene los datos de una fila en función de su posición.
     *
     * @param posicion La posición de la fila que se desea obtener.
     * @return Lista de objetos que representan los datos de la fila en la posición especificada.
     * @throws IllegalArgumentException si no se encuentra una fila en la posición especificada.
     */
    public List<Object> obtenerFilaPorPosicion(int posicion) {
        for (Fila fila : filas) {
            // Verifica que la posición coincida con la buscada
            if (fila.getposicion() == posicion) {
                List<Object> datosFila = new ArrayList<>();
                for (Columna<?, ?> columna : tabla) {
                    datosFila.add(columna.getdato(fila.getposicion()));
                }
                return datosFila;
            }
        }
        throw new IllegalArgumentException("Fila no encontrada con la posición: " + posicion);
    }

    /**
     * Obtiene una columna de la tabla según su etiqueta.
     *
     * @param <E> Tipo de la etiqueta de la columna.
     * @param etiquetaColumna Etiqueta de la columna a buscar.
     * @return Columna encontrada con la etiqueta proporcionada.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe.
     */
    public <E> Columna<?, ?> obtenerColumnaPorEtiqueta(E etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna)) {
                return columna;
            }
        }
        throw new ColumnaNoValidaException("Columna no encontrada: " + etiquetaColumna);
    }

    /**
     * Obtiene todos los datos de una columna específica según su etiqueta.
     *
     * @param etiquetaColumna Etiqueta de la columna a buscar.
     * @return List<?> Lista de datos en la columna encontrada.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe.
     */
    public List<?> obtenerColumnaPorEtiquetaIndex(Object etiquetaColumna) {
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta().equals(etiquetaColumna)) {
                return columna.getDatos(); // Devuelve todos los datos en la columna
            }
        }
        throw new ColumnaNoValidaException("Columna no encontrada con la etiqueta: " + etiquetaColumna);
    }
    
    /**
     * Obtiene el valor de una celda específica en la tabla, identificada por las etiquetas de fila y columna.
     * 
     * @param etiquetaFila Etiqueta de la fila de la celda.
     * @param etiquetaColumna Etiqueta de la columna de la celda.
     * @return Object El valor encontrado en la celda.
     * @throws FilaNoValidaException si la fila con la etiqueta dada no existe.
     * @throws ColumnaNoValidaException si la columna con la etiqueta dada no existe.
     */
    public Object obtenerCelda(Object etiquetaFila, Object etiquetaColumna) {
        Fila filaEncontrada = null;
        for (Fila fila : filas) {
            if (fila.getetiqueta() != null && fila.getetiqueta().equals(etiquetaFila)) {
                filaEncontrada = fila;
                break;
            }
        }

        // Lanza excepción si no se encontró la fila
        if (filaEncontrada == null) {
            throw new FilaNoValidaException("Fila no encontrada con la etiqueta: " + etiquetaFila);
        }

        Columna<?, ?> columnaEncontrada = null;
        for (Columna<?, ?> columna : tabla) {
            if (columna.getetiqueta() != null && columna.getetiqueta().equals(etiquetaColumna)) {
                columnaEncontrada = columna;
                break;
            }
        }

        // Lanza excepción si no se encontró la columna
        if (columnaEncontrada == null) {
            throw new ColumnaNoValidaException("Columna no encontrada con la etiqueta: " + etiquetaColumna);
        }

        // Retorna el dato en la celda especificada
        return columnaEncontrada.getdato(filaEncontrada.getposicion());
    }

    /**
     * Calcula el código hash para la instancia de la tabla, basándose en las listas de columnas (`tabla`) y filas (`filas`).
     * 
     * @return un entero que representa el código hash de la tabla, calculado utilizando un valor primo para minimizar colisiones.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tabla == null) ? 0 : tabla.hashCode());
        result = prime * result + ((filas == null) ? 0 : filas.hashCode());
        return result;
    }

    /**
     * Compara si dos tablas son iguales, basándose en sus listas de columnas y filas.
     * 
     * @param obj el objeto con el que comparar la tabla actual.
     * @return `true` si las tablas son iguales, `false` en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tabla other = (Tabla) obj;

        // Comparar el número de columnas y filas
        if (this.getColumnas().size() != other.getColumnas().size() ||
            this.filas.size() != other.filas.size())
            return false;

        // Comparar cada columna
        for (int i = 0; i < this.getColumnas().size(); i++) {
            Columna<?, ?> thisColumna = this.getColumnas().get(i);
            Columna<?, ?> otherColumna = other.getColumnas().get(i);
            if (!thisColumna.equals(otherColumna)) {
                return false;
            }
        }

        return true;
    }

    
}
