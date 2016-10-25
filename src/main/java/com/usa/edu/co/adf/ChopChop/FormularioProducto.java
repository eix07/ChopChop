package com.usa.edu.co.adf.ChopChop;

import com.usa.edu.co.adf.Data.Objetos.Producto;
import com.usa.edu.co.adf.Service.Service;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FormularioProducto extends FormLayout{

	private TextField nombre= new TextField("Nombre Producto");
	private TextField precio= new TextField("Precio del Producto");
	private TextField tipo = new TextField("Tipo del Producto");
	private Button guardar= new Button("Guardar");
	private Service servicio= Service.getInstance();
	private Producto producto= new Producto();
	private MyUI myUI;
	private Upload subir;
	private ImageUploader receiver;
	
	public FormularioProducto(MyUI myUI ) {
		this.myUI= myUI;
		
		final Embedded image = new Embedded("Uploaded Image");
		image.setVisible(false);
		
		receiver = new ImageUploader(image);
		subir= new Upload("Subir Imagen",receiver);
		subir.setButtonCaption("Start Upload");
		subir.addSucceededListener(receiver);
		
		guardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		guardar.setClickShortcut(KeyCode.ENTER);
		guardar.addClickListener(e ->agregarProducto());
		setSizeUndefined();
		setSpacing(true);
		addComponents(nombre,precio,tipo,subir,guardar);
		
		
	}
	
	public void agregarProducto(){
		producto=new Producto();
		producto.setNombre(nombre.getValue());
		producto.setPrecio(Double.parseDouble(precio.getValue()));
		producto.setRutaArchivo(receiver.rutaFile());
		producto.setType(tipo.getValue().toLowerCase());
		receiver.actualizarRuta();
		servicio.agregarProducto(producto);
		setVisible(false);
		nombre.setValue("");
		precio.setValue("");
		tipo.setValue("");
	    myUI.iniciarComboBox();
	}
}
