/*
 * ImagePane.java
 *
 * Created on 29 de Setembro de 2005, 13:48
 */

package com.erkobridee.PID.GUI;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
/**
 * @author <a href="mailto:erko.bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 *
 * Classe componente para exibi��o de uma imagem
 */
public class ImagePanel extends JPanel {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
    /**
     * Imagem que est� carregada no Buffer
     */
    private BufferedImage bufferedImage;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
    /**
     * Construtor sem parametro
     */
    public ImagePanel() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * @return BufferedImage buffer.
	 */
	public BufferedImage getBufferedImage() {
	    return bufferedImage;
	}
	/**
	 * @param BufferedImage buffer
	 */
	public void setBufferedImage(BufferedImage buffer) {
	    this.bufferedImage = buffer;
	}
//  -----------------------------------------------------------
//   Fim dos m�todos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de servi�os da classe
//  -----------------------------------------------------------	
    /**
     * Sobrescrevendo m�todo de atualiza��o gr�fica
     * do componente
     */
    public void paintComponent(Graphics g) {
        super.paintComponent ( g );        
        if( this.getBufferedImage() != null ) {
	        Graphics2D g2 = (Graphics2D) g;
	        // definindo qualidade de renderiza��o da imagem
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        // desenhando a imagem no Painel
	        g2.drawImage(this.getBufferedImage(), 0, 0, this.getBufferedImage().getWidth(), this.getBufferedImage().getHeight(), this);
        }
    }
//  -----------------------------------------------------------
//   Fim dos m�todos de servi�os da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos auxiliares da classe
//  -----------------------------------------------------------
    /**
     * M�todo de inicializa��o dos atributos da classe
     */
    private void init() {
  	    this.setBufferedImage( null );
  	}
    /**
     * M�todo que atualiza a imagem em exibi��o
     * 
     * @param BufferedImage bufferedImage
     */
    public void draw( BufferedImage bufferedImage ) {
        this.setBufferedImage( bufferedImage );
        this.repaint();
    }
//  -----------------------------------------------------------
//   Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	  
}
