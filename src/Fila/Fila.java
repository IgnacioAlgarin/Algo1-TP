package Fila;

public class Fila {
    private String etiqueta = null;
    private int posicion;

    public Fila(String etiqueta, int posicion) {
        this.etiqueta = etiqueta;
        this.posicion = posicion;
    }
    public Fila(int posicion) {
        this.posicion = posicion;
    }

    public String toString(){
        if (etiqueta == null){
            return "Fila " + posicion + ", sin etiqueta.";
        } else {
        return "Fila " + posicion + ", etiqueta: " + etiqueta + ".";
        }
    }
    public int getposicion(){
        return posicion;
    }
    public String getetiqueta(){
        return etiqueta;
    }

    public int getIndice(){
        return posicion ;
    }
}
