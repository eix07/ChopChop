package com.usa.edu.co.adf.ChopChop;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.usa.edu.co.adf.Data.Objetos.Compra;
import com.usa.edu.co.adf.Data.Objetos.Producto;
import com.usa.edu.co.adf.Service.Service;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private Service servicio = Service.getInstance();
	private Panel encabezado = new Panel();
	private ComboBox producto = new ComboBox();
	private Button nuevoProducto = new Button("Crear nuevo producto");
	private Button agregar = new Button("Añadir al carrito");
	private Button verCompras = new Button("Ver Compras");
	private Button eliminarProducto = new Button();
	private Button comprar = new Button("Realizar compra");

	private Grid tabla = new Grid();
	private Grid tablaCompras = new Grid();
	private Grid imagen = new Grid();
	private Image im = new Image();
	private Image logo = new Image();

	private Producto productico = new Producto();
	private Compra comprita;
	private List<Producto> listaProductos;
	private List<Compra> listaAgregados = new LinkedList<>();
	private TextField cantidad = new TextField("Ingrese cantidad");
	private KieSession session;
	KieServices ks = KieServices.Factory.get();
	KieContainer kc = ks.getKieClasspathContainer();
	
	Calendar ca=Calendar.getInstance();
	int minutes=ca.get(Calendar.MINUTE);
	
	private FormularioProducto formulario = new FormularioProducto(this);

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		session = kc.newKieSession();
		iniciarComboBox();
		logo.setSource(new ThemeResource("ChopShopLogo.png"));
		encabezado.setContent(logo);
		encabezado.setWidth("316px");
		encabezado.setStyleName(Runo.PANEL_LIGHT);
		tabla.setColumns("nombreProducto", "cantidad", "totalCompra");
		tablaCompras.setColumns("fechaCompra", "nombreProducto", "cantidad", "totalCompra","descuento");
		comprar.setIcon(FontAwesome.SHOPPING_CART);
		agregar.setIcon(FontAwesome.CART_PLUS);
		nuevoProducto.setIcon(FontAwesome.PLUS);
		verCompras.setIcon(FontAwesome.LIST);
		if(minutes>30){
			Notification.show("Descuentos de 20% frutas por los siguientes 30 minutos!!", Notification.TYPE_WARNING_MESSAGE);
		}else if(minutes<=30){
			Notification.show("Descuentos de 15% ropa por los siguientes 30 minutos!!", Notification.TYPE_WARNING_MESSAGE);
		}
		HorizontalLayout main = new HorizontalLayout(tabla, tablaCompras, formulario);
		HorizontalLayout layoutImagen = new HorizontalLayout(producto, im);
		HorizontalLayout layoutAddRemove = new HorizontalLayout(agregar, eliminarProducto);
		HorizontalLayout layoutCompra = new HorizontalLayout(nuevoProducto, comprar, verCompras);
		nuevoProducto.setSizeUndefined();
		comprar.setResponsive(true);
		layoutCompra.setSpacing(true);
		layoutImagen.setSpacing(true);
		layoutAddRemove.setSpacing(true);
		main.setSpacing(true);
		
		main.setSizeFull();
		tabla.setSizeFull();
		tablaCompras.setSizeFull();
		main.setExpandRatio(tabla, 1);
		main.setExpandRatio(tablaCompras, 1);
		tablaCompras.setVisible(false);
		layoutCompra.setVisible(true);

		eliminarProducto.setIcon(FontAwesome.TIMES);
		eliminarProducto.setVisible(false);
		layout.addComponents(encabezado,layoutImagen, cantidad, layoutAddRemove, main, layoutCompra);
		layout.setComponentAlignment(encabezado, Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
		formulario.setVisible(false);
		nuevoProducto.addClickListener(e -> {
			formulario.setVisible(true);
		});
		agregar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregar.addClickListener(e -> obtenerProducto());
		comprar.addClickListener(e -> finalizarCompra());
		tabla.addSelectionListener(e -> seleccion(e));
		eliminarProducto.addClickListener(e -> eliminarProducto());
		verCompras.addClickListener(e -> verCompras());
		producto.addValueChangeListener(e -> {
			iniciarProductico();
			if (!productico.getRutaArchivo().equalsIgnoreCase(" ")) {
				im.setSource(new ThemeResource(productico.getRutaArchivo()));
				im.setWidth("100px");
				im.setHeight("100px");
				im.setVisible(true);
			}else{
				im.setVisible(false);
			}
			
		});
		
		
	}

	private void iniciarProductico() {
		String pd = (String) producto.getValue();
		for (int i = 0; i < listaProductos.size(); i++) {
			if (listaProductos.get(i).getNombre().equalsIgnoreCase(pd)) {
				productico = listaProductos.get(i);
				break;
			}
		}

	}

	private void verCompras() {
		tablaCompras.setContainerDataSource(new BeanItemContainer<>(Compra.class, servicio.listadoCompras()));
		tabla.setVisible(false);
		tablaCompras.setVisible(true);
	}

	private void seleccion(SelectionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSelected().isEmpty()) {
			eliminarProducto.setVisible(false);
		} else {
			comprita = (Compra) e.getSelected().iterator().next();
			eliminarProducto.setVisible(true);
		}
	}

	private void eliminarProducto() {
		// TODO Auto-generated method stub
		listaAgregados.remove(comprita);
		actualizarTabla();
		eliminarProducto.setVisible(false);
	}

	private void finalizarCompra() {

		if (listaAgregados.isEmpty()) {
			Notification.show("Selecione productos para comprar");
		} else {
			Date fecha = new Date();
			
			for (Compra c : listaAgregados) {
				c.setFechaCompra(fecha);
				servicio.agregarCompra(c);
			}
			Notification.show("Compra realizada con Exito");
			listaAgregados.clear();
			actualizarTabla();
		}

	}

	private void obtenerProducto() {
		
		if (!tabla.isVisible()) {
			tabla.setVisible(true);
			tablaCompras.setVisible(false);
		}

		if (producto.getValue() == null || cantidad.getValue() == null) {
			Notification.show("Por favor rellene todos los campos");
		} else {

			double cd = Double.parseDouble(cantidad.getValue());
			productico.setType(productico.getType().trim());
			session.insert(productico);
			///////////////////////////////--->
			productico.setTimeShop(minutes);
			System.out.println("Laconcha"+productico.getType());
			System.out.println("Laconcha"+productico.getTimeShop());
			session.fireAllRules();
			String precio = String.valueOf(productico.getPrecio());
			String total = String.valueOf(productico.getPrecio() * cd);
			Compra c = new Compra();
			c.setCantidad(cd);
			c.setNombreProducto(productico.getNombre());
			c.setTotalCompra(Double.parseDouble(total));
			if(productico.getType().equalsIgnoreCase("fruta ")){
				Notification.show("Descuento del 20%", Notification.TYPE_TRAY_NOTIFICATION);
				c.setDescuento((double)20);
			}else if(productico.getType().equalsIgnoreCase("verdura ")){
				Notification.show("Descuento del 30%", Notification.TYPE_TRAY_NOTIFICATION);
				c.setDescuento((double)30);
			}
			
			listaAgregados.add(c);
			actualizarTabla();
			producto.clear();
			cantidad.clear();

		}

	}

	private void actualizarTabla() {
		tabla.setContainerDataSource(new BeanItemContainer<>(Compra.class, listaAgregados));
	}

	public void iniciarComboBox() {
	listaProductos = servicio.getProductList();
	LinkedList<String> nombresProductos = new LinkedList<>();
		for (int i = 0; i < listaProductos.size(); i++) {
			nombresProductos.add(listaProductos.get(i).getNombre());
		}
		producto.setInputPrompt("No product select");
		if (producto.isEmpty()) {
			producto.setContainerDataSource(new BeanItemContainer<>(String.class,nombresProductos));
		}else{
			producto.removeAllItems();
			producto.setContainerDataSource(new BeanItemContainer<>(String.class,nombresProductos));
			
		}
		
		

	}
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
