package groupby;

import Filtro.Filtro;
import Operaciones.OperacionesColumna;
import Tabla.Tabla;
import java.util.Map;
import java.util.Set;

import Columna.Columna;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class GroupBy {

    public static <T> ResultadoGroupBy<T> groupBy(Tabla tabla, String columna) {
        List<Tabla> tablas = new ArrayList<>();
        Columna datosColumna = tabla.obtenerColumnaPorEtiqueta(columna);

        // Crear conjunto de valores únicos en la columna
        Set<T> conjunto = new HashSet<>((List<T>) datosColumna.getDatos());
        List<T> listaUnica = new ArrayList<>(conjunto);

        // Generar una lista de tablas filtradas según los valores únicos de la columna.
        for (T valorUnico : listaUnica) {
            tablas.add(tabla.filtrarPorCondicion(tabla, columna, '=', valorUnico.toString()));
        }

        return new ResultadoGroupBy<>(tablas, listaUnica);
    }

    public static <T> Tabla aplicarAColumna(ResultadoGroupBy tablasAgrupadas, String columna, String operacion) {
        Tabla tablaAgrupada = new Tabla();
        List<Tabla> tablas = tablasAgrupadas.getTablas();
        List<T> valoresUnicos = tablasAgrupadas.getValoresUnicos();
        List<Double> valoresOperacion = new ArrayList<>();
        Columna datosColumna;
        OperacionesColumna resultado;

        tablaAgrupada.agregarColumna(valoresUnicos);

        for (int i= 0; i < valoresUnicos.size(); i++) {
            datosColumna = tablas.get(i).obtenerColumnaPorEtiqueta(columna);
            resultado = new OperacionesColumna(datosColumna.getDatos());
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

        tablaAgrupada.agregarColumna(valoresOperacion);

        return tablaAgrupada;
    }

    /**
     * Clase auxiliar para almacenar el resultado del método groupBy.
     * Contiene una lista de tablas y una lista de valores únicos.
     */
    public static class ResultadoGroupBy<T> {
        private List<Tabla> tablas;
        private List<T> valoresUnicos;

        public ResultadoGroupBy(List<Tabla> tablas, List<T> valoresUnicos) {
            this.tablas = tablas;
            this.valoresUnicos = valoresUnicos;
        }

        public List<Tabla> getTablas() {
            return tablas;
        }

        public List<T> getValoresUnicos() {
            return valoresUnicos;
        }
    }
}
