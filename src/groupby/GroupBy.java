package groupby;

import Operaciones.Operaciones;
import Tabla.Tabla;
import java.util.Set;
import Columna.Columna;
import NA.NA;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * La clase GroupBy proporciona métodos para agrupar y realizar operaciones
 * en una columna específica de una tabla.
 */
public class GroupBy {

    /**
     * Agrupa una tabla en función de los valores únicos de una columna específica.
     *
     * @param <T>     el tipo de datos en la columna a agrupar.
     * @param tabla   la tabla que se desea agrupar.
     * @param columna el nombre de la columna por la cual se desea agrupar.
     * @return un objeto ResultadoGroupBy que contiene las tablas agrupadas
     *         y la lista de valores únicos en la columna.
     */
    public static <T> ResultadoGroupBy<T> groupBy(Tabla tabla, String columna) {
        List<Tabla> tablas = new ArrayList<>();
        Columna datosColumna = tabla.obtenerColumnaPorEtiqueta(columna);

        // Crear conjunto de valores únicos en la columna
        Set<T> conjunto = new HashSet<>((List<T>) datosColumna.getDatos());
        conjunto.remove(null);  // Excluye null de los valores únicos
        conjunto.remove(NA.getInstance());
        List<T> listaUnica = new ArrayList<>(conjunto);

        // Generar una lista de tablas filtradas según los valores únicos de la columna.
        for (T valorUnico : listaUnica) {
            tablas.add(tabla.filtrarPorCondicion(tabla, columna, '=', valorUnico.toString()));
        }

        return new ResultadoGroupBy<>(tablas, listaUnica);
    }

    /**
     * Aplica una operación a una columna específica de cada grupo en una lista de tablas agrupadas.
     *
     * @param <T>             el tipo de datos en la columna sobre la cual se desea realizar la operación.
     * @param tablasAgrupadas el resultado de una operación de agrupamiento (groupBy).
     * @param columna         el nombre de la columna sobre la cual se aplicará la operación.
     * @param operacion       el tipo de operación a aplicar (sumar, contar, desvio, maximo, minimo, promediar, varianza).
     * @return una nueva tabla que contiene los valores únicos de la columna agrupada y el resultado de la operación aplicada.
     */
    public static <T> Tabla aplicarAColumna(ResultadoGroupBy tablasAgrupadas, String columna, String operacion) {
        Tabla tablaAgrupada = new Tabla();
        List<Tabla> tablas = tablasAgrupadas.getTablas();
        List<T> valoresUnicos = tablasAgrupadas.getValoresUnicos();
        List<Double> valoresOperacion = new ArrayList<>();
        Columna datosColumna;
        Operaciones resultado;

        // Agrega la columna de valores únicos a la nueva tabla
        tablaAgrupada.agregarColumna(valoresUnicos);

        for (int i = 0; i < valoresUnicos.size(); i++) {
            datosColumna = tablas.get(i).obtenerColumnaPorEtiqueta(columna);
            resultado = new Operaciones(datosColumna.getDatos());
            switch (operacion.toLowerCase()) {
                case "sumar":
                    valoresOperacion.add(resultado.sumar());
                    break;
                case "contar":
                    valoresOperacion.add(resultado.contar());
                    break;
                case "desvio":
                    valoresOperacion.add(resultado.desvio());
                    break;
                case "maximo":
                    valoresOperacion.add(resultado.maximo());
                    break;
                case "minimo":
                    valoresOperacion.add(resultado.minimo());
                    break;
                case "promediar":
                    valoresOperacion.add(resultado.promediar());
                    break;
                case "varianza":
                    valoresOperacion.add(resultado.varianza());
                    break;
                default:
                    break;
            }
        }

        // Agrega los resultados de la operación a la nueva tabla
        tablaAgrupada.agregarColumna(valoresOperacion);

        return tablaAgrupada;
    }

    /**
     * Clase auxiliar para almacenar el resultado del método groupBy.
     * Contiene una lista de tablas y una lista de valores únicos.
     *
     * @param <T> el tipo de datos en la lista de valores únicos.
     */
    public static class ResultadoGroupBy<T> {
        private List<Tabla> tablas;
        private List<T> valoresUnicos;

        /**
         * Crea una nueva instancia de ResultadoGroupBy.
         *
         * @param tablas        la lista de tablas agrupadas por valores únicos.
         * @param valoresUnicos la lista de valores únicos que se usaron para agrupar las tablas.
         */
        public ResultadoGroupBy(List<Tabla> tablas, List<T> valoresUnicos) {
            this.tablas = tablas;
            this.valoresUnicos = valoresUnicos;
        }

        /**
         * Obtiene la lista de tablas agrupadas.
         *
         * @return la lista de tablas agrupadas.
         */
        public List<Tabla> getTablas() {
            return tablas;
        }

        /**
         * Obtiene la lista de valores únicos que se usaron para agrupar las tablas.
         *
         * @return la lista de valores únicos.
         */
        public List<T> getValoresUnicos() {
            return valoresUnicos;
        }
    }
}
