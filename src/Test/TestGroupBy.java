package Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Archivo.Archivo;
import Columna.Columna;
import Tabla.Tabla;
import groupby.GroupBy;

public class TestGroupBy {
    public static void main(String[] args) throws Exception  {
        System.out.println("Inicio test groupBy");
        Tabla tablaAgrupar = new Tabla();
        tablaAgrupar = Archivo.importar("src/datos_GroupBy.csv",",", false);

        Tabla tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_0", "Contar");
        tabla.visualizar();

        // List<Tabla> tablas = GroupBy.groupBy(tablaAgrupar, "Columna_1");
        // Columna col = GroupBy.aplicarAColumna(tablas, "Columna_0");
        
        // for (Tabla tabla: tablas) {
        //     int i = 1;
        //     System.out.println("Tabla " + i++);
        //     tabla.visualizar();
        // }
        // System.out.println("Fin test");
    }

}
