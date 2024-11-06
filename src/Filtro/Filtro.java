package Filtro;

import java.util.List;

import Columna.Columna;
import Columna.Columna_bool;
import Columna.Columna_num;
import Columna.Columna_string;
import Tabla.Tabla;

public class Filtro {

    static Tabla filtrar(Tabla tabla, String argumento) {
        Tabla tablaFiltrada = tabla.copia_p();

        // Dividir los argumentos por la palabra clave 'and' para manejar múltiples condiciones
        String[] condiciones = argumento.split(" and ");

        // Iterar por cada fila y aplicar el filtro
        tablaFiltrada.eliminarFilasQueNoCumplen(fila -> {
            for (String condicion : condiciones) {
                if (!aplicarCondicion(fila, condicion, tabla)) {
                    return false;
                }
            }
            return true;
        });

        return tablaFiltrada;
    } 

    private static boolean aplicarCondicion(List<Object> fila, String condicion, Tabla tabla) {
        String[] partes = condicion.split(" ");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Condición no válida: " + condicion);
        }

        String nombreColumna = partes[0];
        String operador = partes[1];
        String valorCondicion = partes[2];

        Columna columna = tabla.obtenerColumnaPorNombre(nombreColumna);
        Object valor = fila.get(tabla.obtenerIndiceDeColumna(nombreColumna));

        // Convertir el valor de la condición en el tipo adecuado y evaluar
        switch (operador) {
            case ">":
                return columna instanceof Columna_num && 
                       valor instanceof Number &&
                       esMayorQue((Number) valor, Double.parseDouble(valorCondicion));
            case "<":
                return columna instanceof Columna_num && 
                       valor instanceof Number &&
                       esMenorQue((Number) valor, Double.parseDouble(valorCondicion));
            case "=":
                if (columna instanceof Columna_bool) {
                    return valor.toString().equalsIgnoreCase(valorCondicion);
                } else if (columna instanceof Columna_string) {
                    return valor.toString().equals(valorCondicion);
                } else if (columna instanceof Columna_num) {
                    return esIgualQue((Number) valor, Double.parseDouble(valorCondicion));
                }
                break;
            default:
                throw new IllegalArgumentException("Operador no soportado: " + operador);
        }
        return false;
    }

    private static Boolean esMayorQue(Number a, Number b) {
        return a.doubleValue() > b.doubleValue();
    }

    private static Boolean esMenorQue(Number a, Number b) {
        return a.doubleValue() < b.doubleValue();
    }

    private static Boolean esIgualQue(Number a, Number b) {
        return a.doubleValue() == b.doubleValue();
    }
}
