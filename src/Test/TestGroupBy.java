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
        Tabla tablaAgrupar = new Tabla("src/datos_GroupBy.csv",",", true);

        System.out.println("Tabla base");
        tablaAgrupar.visualizar();

        System.out.println("Agrupo segun columna 'Cadena' y aplico contar");
        Tabla tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Cadena"), "Valores", "Contar");
        tabla.visualizar();

        System.out.println("Agrupo segun columna 'Bool' y aplico contar");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Bool"), "Valores", "Contar");
        tabla.visualizar();

        System.out.println("Agrupo segun columna 'Cadena' y aplico promedio");
        tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Cadena"), "Valores", "promediar");
        tabla.visualizar();
        
        System.out.println("Test con muchos datos\n" + 
                            "Agrupo segun 'String_A y aplica a 'Num_0_100' desvio estandar");
        Tabla tablaG = Archivo.importar("src/df_1000filas.csv",",", true);
        tablaG.reemplazarNullConNA();

        tablaG.mostrarCuadroInformacion();
        tablaG = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaG, "String_A"), "Num_0_100" , "desvio");
        tablaG.visualizar();

        // System.out.println("Test de operaciones pero en columna NO numerica");
        // tabla = GroupBy.aplicarAColumna(GroupBy.groupBy(tablaAgrupar, "Cadena"), "Bool", "sumar");
        // tabla.visualizar();

        System.out.println("Fin de test\n");

    }

}
