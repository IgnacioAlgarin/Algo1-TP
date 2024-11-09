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

public class Archivo {
    
    //Metodos publicos
    public static void exportar(Tabla tabla, String nombreArchivo) throws ArchivoException {
        // Exporta una tabla a un archivo con valores por defecto para sep y header
        exportar(tabla, nombreArchivo, ",", true); // Valores por defecto para `sep` y `header`
    }

    public static void exportar(Tabla tabla, String nombreArchivo, String sep) throws ArchivoException {
        // Exporta una tabla a un archivo con valores por defecto para header
        exportar(tabla, nombreArchivo, sep, true); // Valor por defecto para `header`
    }

    public static void exportar(Tabla tabla, String nombreArchivo, String sep, Boolean header) throws ArchivoException {
        // Exporta una tabla a un archivo aclarando cual separador usar y si tiene encabezados las columnas
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
            throw new ArchivoException("La tabla a exportar esta vacia");
        }
    }

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

    //Metodos privados
    private static List<Object[]> filasAColumnas(List<Object[]> datos) {
        //Convierte los datos de filas para utilizarlos como columnas.
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

    private static List<Object[]> parseCSV(String nombreArchivo, String sep, Boolean header) {
        // Recorre un archivo de texto para extraer los datos 
        List<Object[]> datos = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
    
            // Leer el resto de las líneas
            while ((linea = bufferedReader.readLine()) != null) {
                String[] valores = linea.split(sep,-1);
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
            e.printStackTrace();
        }
        return datos;
    }
    
    private static Object detectarTipo(String valor) {
        // Método para detectar el tipo de dato de cada valor
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
