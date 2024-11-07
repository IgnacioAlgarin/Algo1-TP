package Filtro;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

import Columna.Columna;
import Columna.Columna_bool;
import Columna.Columna_num;
import Columna.Columna_string;
import Tabla.Tabla;

public interface Filtro {

    default <E extends Comparable<E>> Tabla filtrar(Tabla tabla, String etiquetaColumna, char operador, E valor) {
        Map<Character, Predicate<E>> operadores = new HashMap<>();
    
        operadores.put('<', e -> compararNumeros(e, valor) < 0);
        operadores.put('>', e -> compararNumeros(e, valor) > 0);
        operadores.put('=', e -> compararNumeros(e, valor) == 0);
        operadores.put('!', e -> compararNumeros(e, valor) != 0);
    
        Predicate<E> condicion = operadores.get(operador);
    
        if (condicion == null) {
            throw new IllegalArgumentException("Operador no válido. Los operadores válidos son '<', '>', '=', '!'");
        }
    
        Tabla tablaFiltrada = tabla.copia_p();
        Columna columnaAFiltrar = tablaFiltrada.obtenerColumnaPorEtiqueta(etiquetaColumna);
    
        if (columnaAFiltrar == null) {
            throw new IllegalArgumentException("La columna con la etiqueta " + etiquetaColumna + " no existe.");
        }
    
        for (int i = columnaAFiltrar.largoColumna() - 1; i >= 0; i--) {
            E valorAComparar = (E) columnaAFiltrar.getdato(i);
            if (valorAComparar == null || !condicion.test(valorAComparar)) {
                tablaFiltrada.eliminarFila(i);
            }
        }
    
        return tablaFiltrada;
    }
    
    @SuppressWarnings("unchecked")
    private <E> int compararNumeros(E e, E valor) {
        if (e == null || valor == null) {
            return 1;  // Si alguno es nulo, tratamos el valor como no válido
        }
        
        // Convierte ambos valores a Double si son números
        if (e instanceof Number && valor instanceof Number) {
            Double eDouble = ((Number) e).doubleValue();
            Double valorDouble = ((Number) valor).doubleValue();
            return eDouble.compareTo(valorDouble);
        }
        
        // Si no son números, intenta la comparación genérica
        return ((Comparable<E>) e).compareTo(valor);
    }
    
    // static Tabla filtrar(Tabla tabla, String argumento) {
    //     Tabla tablaFiltrada = tabla.copia_p();

    //     // Dividir los argumentos por la palabra clave 'and' para manejar múltiples condiciones
    //     String[] condiciones = argumento.split(" and ");

    //     // Iterar por cada fila y aplicar el filtro
    //     tablaFiltrada.eliminarFilasQueNoCumplen(fila -> {
    //         for (String condicion : condiciones) {
    //             if (!aplicarCondicion(fila, condicion, tabla)) {
    //                 return false;
    //             }
    //         }
    //         return true;
    //     });

    //     return tablaFiltrada;
    // } 

    // private static boolean aplicarCondicion(List<Object> fila, String condicion, Tabla tabla) {
    //     String[] partes = condicion.split(" ");
    //     if (partes.length != 3) {
    //         throw new IllegalArgumentException("Condición no válida: " + condicion);
    //     }

    //     String nombreColumna = partes[0];
    //     String operador = partes[1];
    //     String valorCondicion = partes[2];

    //     Columna columna = tabla.obtenerColumnaPorNombre(nombreColumna);
    //     Object valor = fila.get(tabla.obtenerIndiceDeColumna(nombreColumna));

    //     // Convertir el valor de la condición en el tipo adecuado y evaluar
    //     switch (operador) {
    //         case ">":
    //             return columna instanceof Columna_num && 
    //                    valor instanceof Number &&
    //                    esMayorQue((Number) valor, Double.parseDouble(valorCondicion));
    //         case "<":
    //             return columna instanceof Columna_num && 
    //                    valor instanceof Number &&
    //                    esMenorQue((Number) valor, Double.parseDouble(valorCondicion));
    //         case "=":
    //             if (columna instanceof Columna_bool) {
    //                 return valor.toString().equalsIgnoreCase(valorCondicion);
    //             } else if (columna instanceof Columna_string) {
    //                 return valor.toString().equals(valorCondicion);
    //             } else if (columna instanceof Columna_num) {
    //                 return esIgualQue((Number) valor, Double.parseDouble(valorCondicion));
    //             }
    //             break;
    //         default:
    //             throw new IllegalArgumentException("Operador no soportado: " + operador);
    //     }
    //     return false;
    // }

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
