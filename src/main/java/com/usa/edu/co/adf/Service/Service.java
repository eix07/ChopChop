package com.usa.edu.co.adf.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.usa.edu.co.adf.Data.AnotationReader;
import com.usa.edu.co.adf.Data.Objetos.Compra;
import com.usa.edu.co.adf.Data.Objetos.Producto;

public class Service {
	
	private static Service instancia;
	private AnotationReader readerProductos;
	private AnotationReader readerCompras;
	private List<Object> listaProductos;
	private List<Object> listaCompras;
	
	public Service(){
	}
	
	public static Service getInstance() {
		if (instancia == null) {
			instancia = new Service();
			instancia.crearServicio();
		}
		return instancia;
	}
	
	public void crearServicio(){
		try {
			readerProductos= new AnotationReader("C:/Users/river/workspace/ChopChop/src/main/java/com/usa/edu/co/adf/Data/Descriptores/ProductoDescriptor.txt");
			readerCompras= new AnotationReader("C:/Users/river/workspace/ChopChop/src/main/java/com/usa/edu/co/adf/Data/Descriptores/CompraDescriptor.txt");
			listaCompras= new LinkedList<>();		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void agregarProducto(Producto p){
		try {
			readerProductos.EscribirConAnotaciones(p);
			
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void agregarCompra(Compra c){
		try {
			readerCompras.EscribirConAnotaciones(c);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Producto> getProductList(){
		List<Producto> productos = new LinkedList<>();
		try {
			this.listaProductos= this.readerProductos.leerConAnotaciones();
			
			for (Object object : listaProductos) {
				Producto p= (Producto) object;
				productos.add(p);
			}
			
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | InstantiationException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productos;
	}
	
	public List<Compra> listadoCompras(){
		
		List<Compra> compras= new LinkedList<>();
		try {
			this.listaCompras=this.readerCompras.leerConAnotaciones();
			for (Object compra : this.listaCompras) {
				Compra c= (Compra) compra;
				compras.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compras;
	}
	
}
