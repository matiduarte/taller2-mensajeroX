package com.dk.mensajero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matias on 26/04/15.
 */
public class ProveedorDatos {

    public static HashMap<String, List<String>> getInfo(){

        HashMap<String, List<String>> paisesDetallados = new HashMap<String, List<String>>();
        List<String> paises = new ArrayList<String>();
        paises.add("Argentina");
        paises.add("Brasil");
        paises.add("Chile");
        paises.add("Uruguay");

        paisesDetallados.put("Paises", paises);

        return paisesDetallados;
    }
}
