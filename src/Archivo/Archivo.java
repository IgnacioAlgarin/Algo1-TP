package Archivo;

import Tabla.Tabla;

public class Archivo {

    public void exportar(Tabla tabla, String path, char sep, Boolean header) {

    }

    public Tabla importar(String path, char sep, Boolean header) {
        Tabla tablaImportada = new Tabla();
        return tablaImportada;
    }

    public void parseCSV() {
        //crear metodo para que recorra datos de un archivo CSV
    }

    public void cargarDatoColumna() {
        // crear metodo para carga dato de parseCSV y lo cargue en una Columna
    }

    public void extraerDatoColumna() {
        // crear metodo para extraer Dato de Columna y lo envie para exportar a un archivo
    }
}
