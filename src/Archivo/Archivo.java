package Archivo;

import Tabla.Tabla;

public class Archivo {

    public void exportar(Tabla tabla, String path, char sep, Boolean header) {

    }

    public <T> Tabla importar(String path, char sep, Boolean header) {
        Tabla tablaImportada = new Tabla<T>();
        return tablaImportada;
    }
}
