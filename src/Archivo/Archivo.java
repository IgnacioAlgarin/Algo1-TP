package Archivo;

import Tabla.Tabla;
import excepciones.ArchivoException;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Columna.Columna;

/**
 * La clase Archivo proporciona métodos para exportar e importar datos de una
 * tabla a un archivo CSV, utilizando diferentes configuraciones para el
 * separador y el encabezado.
 */
public class Archivo {

    /**
     * Exporta una tabla a un archivo CSV con un separador por defecto (coma) y con encabezado.
     *
     * @param tabla         la tabla a exportar.
     * @param nombreArchivo el nombre del archivo donde se exportará la tabla.
     * @throws ArchivoException si ocurre algún error al exportar los datos.
     */
    public static void exportar(Tabla tabla, String nombreArchivo) throws ArchivoException {
        exportar(tabla, nombreArchivo, ",", true); // Valores por defecto para `sep` y `header`
    }

    /**
     * Exporta una tabla a un archivo CSV con encabezado y un separador específico.
     *
     * @param tabla         la tabla a exportar.
     * @param nombreArchivo el nombre del archivo donde se exportará la tabla.
     * @param sep           el separador que se usará en el archivo CSV.
     * @throws ArchivoException si ocurre algún error al exportar los datos.
     */
    public static void exportar(Tabla tabla, String nombreArchivo, String sep) throws ArchivoException {
        exportar(tabla, nombreArchivo, sep, true); // Valor por defecto para `header`
    }

    /**
     * Exporta una tabla a un archivo CSV con opciones específicas para el separador y si
     * debe incluir encabezado.
     *
     * @param tabla         la tabla a exportar.
     * @param nombreArchivo el nombre del archivo donde se exportará la tabla.
     * @param sep           el separador que se usará en el archivo CSV.
     * @param header        si es true, incluye encabezados de columna en el archivo.
     * @throws ArchivoException si ocurre algún error al exportar los datos.
     */
    public static void exportar(Tabla tabla, String nombreArchivo, String sep, Boolean header) throws ArchivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            if (header) {
                List<String> encabezados = new ArrayList<>();
                for (Columna<?, ?> c : tabla.getColumnas()) {
                    encabezados.add(c.getetiqueta().toString());
                }
                String encabezado = String.join(sep, encabezados);
                writer.write(encabezado);
                writer.newLine();
            }

            int numFilas = tabla.getColumnas().get(0).getDatos().size();
            for (int i = 0; i < numFilas; i++) {
                List<String> fila = new ArrayList<>();
                for (Columna<?, ?> c : tabla.getColumnas()) {
                    if (c.getdato(i) != null) {
                        fila.add(c.getdato(i).toString());
                    } else {
                        fila.add("");
                    }
                }
                String linea = String.join(sep, fila);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ArchivoException("Error al exportar los datos a " + nombreArchivo + e);
        } catch (IndexOutOfBoundsException e) {
            throw new ArchivoException("La tabla a exportar está vacía");
        }
    }

    /**
     * Importa datos desde un archivo CSV y los carga en una tabla, utilizando un
     * separador específico y considerando si incluye encabezado.
     *
     * @param nombreArchivo el nombre del archivo CSV desde el cual importar datos.
     * @param sep           el separador usado en el archivo CSV.
     * @param header        si es true, el archivo incluye encabezados de columna.
     * @return una tabla con los datos importados.
     * @throws IOException si ocurre algún error al leer el archivo.
     */
    public static Tabla importar(String nombreArchivo, String sep, Boolean header) throws IOException {
        try {
            Tabla tablaImportada = new Tabla();
            List<Object[]> datos = parseCSV(nombreArchivo, sep, header);
            List<String> etiquetasColumnas = new ArrayList<>();

            if (header && !datos.isEmpty()) {
                for (Object etiqueta : datos.get(0)) {
                    etiquetasColumnas.add((String) etiqueta);
                }
                datos.remove(0);
            }

            List<Object[]> columnas = filasAColumnas(datos);
            for (int i = 0; i < columnas.size(); i++) {
                String etiquetaColumna;
                List<Object> columna = Arrays.asList(columnas.get(i));
                if (header) {
                    etiquetaColumna = etiquetasColumnas.get(i);
                } else {
                    etiquetaColumna = "Columna_" + i;
                }
                tablaImportada.agregarColumna(columna, etiquetaColumna);
            }
            return tablaImportada;
        } catch (ClassCastException e) {
            System.out.println("Error de conversión de tipos en los datos: " + e.getMessage());
        }
        return null;
    }

    /**
     * Convierte una lista de datos organizada en filas en una lista organizada en columnas.
     *
     * @param datos los datos en formato de filas.
     * @return los datos reorganizados en formato de columnas.
     */
    private static List<Object[]> filasAColumnas(List<Object[]> datos) {
        List<Object[]> datosColumna = new ArrayList<>();

        if (datos.isEmpty()) {
            return datosColumna;
        }

        int numColumnas = datos.get(0).length;

        for (int i = 0; i < numColumnas; i++) {
            datosColumna.add(new Object[datos.size()]);
        }

        for (int i = 0; i < datos.size(); i++) {
            Object[] fila = datos.get(i);
            for (int j = 0; j < fila.length; j++) {
                datosColumna.get(j)[i] = fila[j];
            }
        }
        return datosColumna;
    }

    /**
     * Lee un archivo CSV y convierte sus líneas en una lista de arreglos de
     * objetos.
     *
     * @param nombreArchivo el nombre del archivo CSV.
     * @param sep           el separador usado en el archivo CSV.
     * @param header        si es true, el archivo incluye encabezados de columna.
     * @return una lista de arreglos de objetos con los datos del archivo CSV.
     */
    private static List<Object[]> parseCSV(String nombreArchivo, String sep, Boolean header) {
        List<Object[]> datos = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] valores = linea.split(sep, -1);
                Object[] valoresConvertidos = new Object[valores.length];

                for (int i = 0; i < valores.length; i++) {
                    if (valores[i].isEmpty()) {
                        valoresConvertidos[i] = null;
                    } else {
                        valoresConvertidos[i] = detectarTipo(valores[i]);
                    }
                }
                datos.add(valoresConvertidos);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return datos;
    }

    /**
     * Detecta y convierte el tipo de dato adecuado para un valor de texto.
     *
     * @param valor el valor en formato de texto.
     * @return el valor convertido en su tipo de dato correspondiente.
     */
    private static Object detectarTipo(String valor) {
        if (valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(valor);
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e1) {
            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException e2) {
                return valor; // Si no es booleano o número, lo tratamos como String
            }
        }
    }
}
