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

        System.out.println("Tabla base");
        tablaAgrupar.visualizar();

        Tabla tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_0", "Contar");
        tabla.visualizar();

        System.out.println("Test con columna booleana, contar");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_2"), "Columna_0", "Contar");
        tabla.visualizar();

        System.out.println("Test con columna string, promedio");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_0", "promediar");
        tabla.visualizar();

        // System.out.println("Test de operaciones pero en columna NO numerica");
        // tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Columna_1"), "Columna_2", "sumar");
        // tabla.visualizar();
        
        System.out.println("Test con muchos datos");
        Tabla tablaG = Archivo.importar("src/df_1000filas.csv",",", true);
        tablaG.reemplazarNullConNA();
        tablaG = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaG, "String_A"), "Num_0_100" , "desvio");
        tablaG.visualizar();

        System.out.println("Fin de test\n");

    }

}
