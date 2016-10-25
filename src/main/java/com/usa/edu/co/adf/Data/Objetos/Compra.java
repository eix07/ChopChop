package com.usa.edu.co.adf.Data.Objetos;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.usa.edu.co.adf.Data.FixedWidthField;

public class Compra {
	
	@FixedWidthField(position = 1, width = 20) Date fechaCompra;
	@FixedWidthField(position = 2, width = 25) String nombreProducto;
	@FixedWidthField(position = 3, width = 10) double cantidad;
	@FixedWidthField(position = 4, width = 10) double totalCompra;
	@FixedWidthField(position = 5, width = 4) double descuento;
	
	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Compra(){
		
	}

	public String getFechaCompra() {
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return (dt1.format(this.fechaCompra));
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double totalCompra) {
		this.totalCompra = totalCompra;
	}

	@Override
	public String toString() {
		return "Compra [fechaCompra=" + fechaCompra + ", nombreProducto=" + nombreProducto + ", cantidad=" + cantidad
				+ ", totalCompra=" + totalCompra + "]";
	}
	
	
	
}
