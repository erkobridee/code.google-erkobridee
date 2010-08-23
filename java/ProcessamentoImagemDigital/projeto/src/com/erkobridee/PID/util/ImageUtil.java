/*
 * Created on 27/09/2005
 */
package com.erkobridee.PID.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Erko Bridee de Almeida Cabrera
 * 
 * <br><br>
 * Classe de utilidades para analise da imagem
 */
public class ImageUtil {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
  	/**
  	 * Imagem a ser analisada
  	 */
    private BufferedImage image;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	
    /**
     * Construtor da classe sem parametros
     */
    public ImageUtil() {}
    
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * @return BufferedImage image.
	 */
	public BufferedImage getImage() {
	    return image;
	}
	/**
	 * @param BufferedImage image
	 */
	public void setImage(BufferedImage image) {
	    this.image = image;
	}
//  -----------------------------------------------------------
//   Fim dos m�todos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de servi�os da classe
//  -----------------------------------------------------------	
  	/**
  	 * Verifica se a imagem informada est� ou n�o na escala
  	 * de cinza
  	 */
	public boolean isGrayScale() {
  	    
  	    boolean out = false;
  	    
		int altura = (int)this.getImage().getHeight() / 2;
		int largura = (int)this.getImage().getWidth() / 2;
		
		// recuperando o pixel central da imagem para testar se � uma imagem em escala de cinza
		Color colorL = new Color( this.getImage().getRGB(altura, largura-3) );
		Color colorS = new Color( this.getImage().getRGB(altura-3, largura) );
		Color color = new Color( this.getImage().getRGB(altura, largura) );
		Color colorI = new Color( this.getImage().getRGB(altura+3, largura) );
		Color colorD = new Color( this.getImage().getRGB(altura-3, largura+1) );
		
		// verificando se os 3 canais possuem o mesmo valor
		if(
			( color.getRed() == color.getGreen() ) && ( color.getGreen() == color.getBlue() ) &&
			( colorS.getRed() == colorS.getGreen() ) && ( colorS.getGreen() == colorS.getBlue() ) &&
			( colorI.getRed() == colorI.getGreen() ) && ( colorI.getGreen() == colorI.getBlue() ) &&
			( colorL.getRed() == colorL.getGreen() ) && ( colorL.getGreen() == colorL.getBlue() ) &&
			( colorD.getRed() == colorD.getGreen() ) && ( colorD.getGreen() == colorD.getBlue() )
		) {
			// seta o flag que indica que a imagem � em escala de cinza
			out = true;
		}		
		
  	    return out;
  	}
  	/**
  	 * M�todo est�tico que verifica uma imagem repassada se ela 
  	 * est� em escala de cinza, sendo verificados se os valores
  	 * RGB est�o com o mesmo valor em 5 pixels da imagem fornecida
  	 * para analise
  	 * 
  	 * @param Buffered image
  	 * @return boolean - indica se a imagem � ou n�o uma em escala de cinza
  	 */
  	public static boolean isGrayScale( BufferedImage image ) {
  	    ImageUtil util = new ImageUtil();
  	    util.setImage( image );
  	    return util.isGrayScale();
  	}
//  -----------------------------------------------------------
//   Fim dos m�todos de servi�os da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos auxiliares da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	

}
