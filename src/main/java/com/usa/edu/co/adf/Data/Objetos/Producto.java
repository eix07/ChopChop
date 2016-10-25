package com.usa.edu.co.adf.Data.Objetos;

import com.usa.edu.co.adf.Data.FixedWidthField;

public class Producto {
	@FixedWidthField(position = 1, width = 25) String nombre;
	@FixedWidthField(position = 2, width = 10) double precio;
	@FixedWidthField(position = 3, width = 100) String rutaArchivo;
	@FixedWidthField(position = 4, width = 9) String type;
	@FixedWidthField(position = 5, width = 2) int timeShop;
	
	public String getType() {
		return type;
	}

	public int getTimeShop() {
		return timeShop;
	}

	public void setTimeShop(int timeShop) {
		this.timeShop = timeShop;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Producto(){
		
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	public void hacerDescuento(double desc){
		double descuento = desc/100;
		precio =precio- precio *descuento;
	}
	
	public void hacerIncremento(double desc){
		double descuento = desc/100;
		precio =precio+ precio *descuento;
	}
	
	public double getValue(double desc){
		return (precio *desc);
	}
	
	
}
