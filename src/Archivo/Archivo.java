package Archivo;

import Tabla.Tabla;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Archivo {
    private String nombreArchivo;
    private String path;
    private String[] etiquetas;

    // Contructores
    public Archivo(String nombreArchivo, String path) {
        this.nombreArchivo = nombreArchivo;
        this.path = path;
    };

    //Seters y geters

    public void setEtiquetas(String[] etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void exportar(Tabla tabla, String path, char sep, Boolean header) {

    }

    public Tabla importar(String sep, Boolean header) {
        Tabla tablaImportada = new Tabla();
        List<Object[]> datos = parseCSV(sep, header);
        tablaImportada.agregarfila(datos);
        return tablaImportada;
    }

    public List<Object[]> parseCSV(String sep, Boolean header) {
        List<Object[]> datos = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path + nombreArchivo))) {
            String linea;
    
            // Leer la primera línea si el archivo tiene encabezado
            if (header && (linea = bufferedReader.readLine()) != null) {
                setEtiquetas(linea.split(sep));
            }
    
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
    
    // Método para detectar el tipo de dato de cada valor
    private Object detectarTipo(String valor) {
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

    public void cargarDatoColumna() {
        // crear metodo para carga dato de parseCSV y lo cargue en una Columna
    }

    public void extraerDatoColumna() {
        // crear metodo para extraer Dato de Columna y lo envie para exportar a un archivo
    }
}
