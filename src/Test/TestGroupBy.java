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
        System.out.println("Inicio test groupBy\n");
        Tabla tablaAgrupar = new Tabla();
        tablaAgrupar = Archivo.importar("src/datos_GroupBy.csv",",", false);

        Tabla tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_0", "Contar");
        tabla.visualizar();

        System.out.println("Test con columna booleana");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_2"), "Columna_0", "Contar");
        tabla.visualizar();

        System.out.println("Test de operaciones pero en columna NO numerica");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_2", "sumar");
        tabla.visualizar();

        System.out.println("Fin de test\n");

    }

}
