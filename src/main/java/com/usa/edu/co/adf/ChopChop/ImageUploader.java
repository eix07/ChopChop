package com.usa.edu.co.adf.ChopChop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

class ImageUploader implements Receiver, SucceededListener {
    public File file;
    public Embedded image;
    public String ruta;
    
    public String rutaFile(){
    	return ruta;
    }
    
    public void actualizarRuta(){
    	ruta="uploads/";
    }
	

	public ImageUploader(Embedded image2) {
		this.image=image2;
		this.ruta="uploads/";
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		image.setVisible(true);
        image.setSource(new FileResource(file));
        Notification.show("Imagen agregada con Exito", Type.TRAY_NOTIFICATION);
		
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		image.setVisible(false);
    	// Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File("src/main/webapp/VAADIN/themes/mytheme/uploads/" + filename);
            ruta=ruta+filename;
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                             e.getMessage(),
                             Notification.Type.ERROR_MESSAGE)
                .show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
	}
}