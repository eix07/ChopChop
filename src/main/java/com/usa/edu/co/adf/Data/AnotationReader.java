/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usa.edu.co.adf.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author Labing
 */
public class AnotationReader {

    private Field[] campos;
    private Annotation[] anotaciones;
    private Class cls;
    private Lector lector;
    private String rutaArchivo;

    public AnotationReader(String rutaDescriptor) throws ClassNotFoundException, FileNotFoundException {
        this.lector= new Lector(new FileReader(rutaDescriptor));
        List<String> lista= this.lector.leerArchivo();
        this.cls= Class.forName(lista.get(0));
        this.rutaArchivo= lista.get(1);
        this.campos = this.cls.getDeclaredFields();
        this.anotaciones = this.cls.getAnnotations();
    }

    public void EscribirConAnotaciones(Object obj) throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        
        //System.out.println(anotaciones.length);
        

        FileWriter fw = new FileWriter(rutaArchivo, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String valor = "";
        String valorEscribir = "";
        for (Field campo : campos) {

            //System.out.println("campo " + campo.getName());
            anotaciones = campo.getAnnotations();

            for (Annotation anotacion : anotaciones) {
               // System.out.println(anotacion);
                if (anotacion instanceof FixedWidthField) {
                    int pos = (((FixedWidthField) anotacion).position());
                    int width = (((FixedWidthField) anotacion).width());
                    Method get = cls.getMethod("get" + capitalize(campo.getName()));
                    valor = get.invoke(obj) + "";
                    int resta = width - valor.length();
                    for (int i = 0; i < resta; i++) {
                        valor = valor + " ";
                    }
                    valorEscribir = valorEscribir + valor;
                }
            }
        }
        bw.write(valorEscribir + "\n");
        bw.close();

    }

    private static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public LinkedList<Object> leerConAnotaciones() throws FileNotFoundException, ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ParseException {
        List<String> datos = new Lector(new FileReader(this.rutaArchivo)).leerArchivo();
        LinkedList<Object> retorna = new LinkedList<>();
        System.out.println(datos.toString());
        for (String dato : datos) {
            Object obj = cls.newInstance();
            int posicion = 0;
            for (Field campo : campos) {
                anotaciones = campo.getAnnotations();
                if (anotaciones[0] instanceof FixedWidthField) {
                    int width = (((FixedWidthField) anotaciones[0]).width()) - 1;
                    String data = dato.substring(posicion, posicion + width);
                    //System.out.println(posicion+" "+(posicion+width));
                    posicion = posicion+width + 1;
                    //System.out.println("campo "+data);
                    Method get = cls.getMethod("set" + capitalize(campo.getName()), campo.getType());
                    get.invoke(obj, casteoObjeto(campo.getType(), data));
                }
            }
            retorna.add(obj);         
        }
        return retorna;
    }


    private Object casteoObjeto(Class<?> type, String data) throws ParseException {
        Object temporal = null;
        ///System.out.println("data " + data);
        if (type.getCanonicalName().equalsIgnoreCase("int")) {
            temporal = Integer.parseInt(data.trim());
        } else if (type.getCanonicalName().equalsIgnoreCase("double")) {
            temporal = Double.parseDouble(data.trim());
        } else if (type.getCanonicalName().equalsIgnoreCase("boolean")) {
            temporal = Boolean.parseBoolean(data.trim());
        } else if (type.getCanonicalName().equalsIgnoreCase("java.lang.String")) {
            temporal = data;
        } else if (type.getCanonicalName().equalsIgnoreCase("java.util.Date")) {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            temporal=dt1.parse(data);
            //System.out.println(dt1.format(temporal));
        }
        return temporal;

    }

	public String getRutaArchivo() {
		return rutaArchivo;
	}

}
