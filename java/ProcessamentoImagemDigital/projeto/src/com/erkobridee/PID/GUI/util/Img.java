/*
 * Created on 10/04/2005
 */
package com.erkobridee.PID.GUI.util;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Classe que recupera imagens do pacote de imagens
 * 
 * @author <a href="mailto:erko.bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class Img {
	/**
	 * Path das imagens
	 */
	private String path = "/com/erkobridee/PID/images/";
	
	/**
	 * Construtor da classe sem parametros 
	 */
	public Img() {}
	
	/**
	 * Método estático que retorna um imagem
	 * @param String name
	 * @return ImageIcon
	 */
	public static ImageIcon getIcon( String name ) {
		return new Img().getIconNoStatic( name );
	}
	/**
	 * Método que retorna um icone que se encontra
	 * no pacote de icones 16x16
	 * @param String name
	 * @return ImageIcon
	 */
	private ImageIcon getIconNoStatic( String name ) {
		ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource(path + name)); 
		return ii;
	}
	/**
	 * Método estático que retorna um icone que se encontra
	 * @param String name
	 * @return Image
	 */
	public static Image getImage( String name ) {
		return new Img().getImageNoStatic( name );
	}
	/**
	 * Método que retorna um icone que se encontra
	 * @param String name
	 * @return Image
	 */
	private Image getImageNoStatic( String name ) {
		ImageIcon ii = new ImageIcon(getClass().getResource(path + name)); 
		return ii.getImage();
	}
	
}
