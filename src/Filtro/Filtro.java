package Filtro;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

import Columna.Columna;
import Tabla.Tabla;

/**
 * Esta interfaz define un filtro para aplicar criterios de filtrado sobre columnas de una tabla.
 */
public interface Filtro {

    /**
     * Filtra una tabla según una cadena de consulta (query) en un formato específico.
     * Ejemplo de query: “columna1 > 3 and columna2 = Verdadero”
     * La query puede contener operadores (>, <, =) y operadores lógicos (and, or, not).
     *
     * @param tabla  La tabla sobre la cual se aplicará el filtro.
     * @param query  La cadena de consulta que define las condiciones del filtro.
     * @return Una nueva tabla que contiene las filas que cumplen con las condiciones especificadas en la query.
     */
    default Tabla filtrar(Tabla tabla, String query) {
        List<String[]> condiciones = interpretarQuery(query);
        Tabla tablaFiltrada = tabla.copia_p();
        List<Tabla> tablasFiltradas = new ArrayList<>();
        String logico = "";

        for (String[] condicion : condiciones) {
            String columna = condicion[0];
            char operador = condicion[1].charAt(0);
            String valor = condicion[2];

            Tabla resultadoCondicion = filtrarPorCondicion(tablaFiltrada, columna, operador, valor);
            tablasFiltradas.add(resultadoCondicion);
        }

        if (condiciones.get(0).length > 3) {
            logico = condiciones.get(0)[3].toLowerCase();
        }
        if ("and".equals(logico)) {
            tablaFiltrada = interseccion(tablasFiltradas.get(0), tablasFiltradas.get(1));
        } else if ("or".equals(logico)) {
            tablaFiltrada = union(tablasFiltradas.get(0), tablasFiltradas.get(1));
        } else if ("not".equals(logico)) {
            tablaFiltrada = diferencia(tablasFiltradas.get(0), tablasFiltradas.get(1));
        } else {
            tablaFiltrada = tablasFiltradas.get(0);
        }

        return tablaFiltrada;
    }

    /**
     * Interpreta la cadena de consulta (query) para dividirla en condiciones de filtrado.
     *
     * @param query La cadena de consulta que contiene las condiciones.
     * @return Una lista de arreglos de cadenas que representan las condiciones separadas.
     */
    private List<String[]> interpretarQuery(String query) {
        String[] queryPartes = query.split(" ");
        List<String[]> condiciones = new ArrayList<>();

        for (int i = 0; i < queryPartes.length; i += 4) {
            String columna = queryPartes[i];
            char operador = queryPartes[i + 1].charAt(0);
            String valor = queryPartes[i + 2];
            String logico = (i + 3 < queryPartes.length) ? queryPartes[i + 3] : "";

            condiciones.add(new String[]{columna, String.valueOf(operador), valor, logico});
        }

        return condiciones;
    }

    /**
     * Filtra una tabla según una condición específica.
     *
     * @param tabla           La tabla a filtrar.
     * @param etiquetaColumna La etiqueta de la columna sobre la cual se aplicará la condición.
     * @param operador        El operador de comparación ('>', '<', '=', '!').
     * @param valor           El valor contra el cual se comparará.
     * @return Una nueva tabla que contiene las filas que cumplen con la condición.
     */
    default Tabla filtrarPorCondicion(Tabla tabla, String etiquetaColumna, char operador, String valor) {
        Map<Character, Predicate<Object>> operadores = new HashMap<>();
        operadores.put('<', e -> compararNumeros(e, valor) < 0);
        operadores.put('>', e -> compararNumeros(e, valor) > 0);
        operadores.put('=', e -> compararNumeros(e, valor) == 0);
        operadores.put('!', e -> compararNumeros(e, valor) != 0);

        Predicate<Object> condicion = operadores.get(operador);

        if (condicion == null) {
            throw new IllegalArgumentException("Operador no válido. Los operadores válidos son '<', '>', '=', '!'");
        }

        Tabla tablaFiltrada = tabla.copia_p();
        Columna columnaAFiltrar = tablaFiltrada.obtenerColumnaPorEtiqueta(etiquetaColumna);

        if (columnaAFiltrar == null) {
            throw new IllegalArgumentException("La columna con la etiqueta " + etiquetaColumna + " no existe.");
        }

        for (int i = columnaAFiltrar.largoColumna() - 1; i >= 0; i--) {
            Object valorAComparar = columnaAFiltrar.getdato(i);
            if (valorAComparar == null || !condicion.test(valorAComparar)) {
                tablaFiltrada.eliminarFila(i);
            }
        }

        return tablaFiltrada;
    }

    /**
     * Compara un objeto numérico con un valor en formato de cadena.
     *
     * @param e     El valor numérico a comparar.
     * @param valor La cadena que representa el valor numérico con el que se compara.
     * @return Un valor entero indicando el resultado de la comparación.
     */
    private int compararNumeros(Object e, String valor) {
        if (e == null || valor == null) {
            return 1;
        }

        try {
            if (e instanceof Number) {
                Double eDouble = ((Number) e).doubleValue();
                Double valorDouble = Double.parseDouble(valor);
                return eDouble.compareTo(valorDouble);
            } else {
                return e.toString().compareTo(valor);
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El valor no es un número válido: " + valor, ex);
        }
    }

    /**
     * Realiza la intersección de dos tablas, manteniendo solo las filas comunes.
     *
     * @param tabla1 La primera tabla.
     * @param tabla2 La segunda tabla.
     * @return Una nueva tabla que contiene solo las filas comunes.
     */
    private Tabla interseccion(Tabla tabla1, Tabla tabla2) {
        Tabla resultado = tabla1.copia_p();
        for (int i = resultado.getCantidadFilas() - 1; i >= 0; i--) {
            if (!tabla2.getPosicionFilas().contains(resultado.getPosicionFilas().get(i))) {
                resultado.eliminarFila(i);
            }
        }
        return resultado;
    }

    /**
     * Realiza la unión de dos tablas, manteniendo todas las filas de ambas tablas.
     *
     * @param tabla1 La primera tabla.
     * @param tabla2 La segunda tabla.
     * @return Una nueva tabla que contiene todas las filas de ambas tablas.
     */
    private Tabla union(Tabla tabla1, Tabla tabla2) {
        Tabla resultado = tabla1.copia_p();

        for (int posicionFila : tabla2.getPosicionFilas()) {
            if (!resultado.getPosicionFilas().contains(posicionFila)) {
                resultado.agregarfila(tabla2.obtenerFilaPorPosicion(posicionFila));
            }
        }
        return resultado;
    }

    /**
     * Realiza la diferencia entre dos tablas, eliminando de la primera tabla las filas que están en la segunda.
     *
     * @param tabla1 La tabla base.
     * @param tabla2 La tabla cuyas filas se eliminarán de la tabla base.
     * @return Una nueva tabla que contiene las filas de la primera tabla que no están en la segunda.
     */
    private Tabla diferencia(Tabla tabla1, Tabla tabla2) {
        Tabla resultado = tabla1.copia_p();
        for (int posicionFila : tabla2.getPosicionFilas()) {
            resultado.eliminarFila(posicionFila);
        }
        return resultado;
    }
}
