/*
 * Created on 27/09/2005
 */
package com.erkobridee.PID.processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Erko Bridee de Almeida Cabrera
 * 
 * <br><br>
 * <b>Descrição:</b><br>
 * Classe responsável pela conversão de uma imagem para a escala de cinza
 */
public class EscalaCinza {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
    /**
     * Imagem original enviada para a classe
     */
    private BufferedImage biOriginal;
    /**
     * Imagem resultado da aplicação da mascara
     */
    private BufferedImage biResultado;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	/**
  	 * Construtor da classe sem parametros
  	 */
    public EscalaCinza() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * @return BufferedImage biOriginal.
	 */
	public BufferedImage getBiOriginal() {
	    return biOriginal;
	}
	/**
	 * @param BufferedImage biOriginal
	 */
	public void setBiOriginal(BufferedImage biOriginal) {
	    this.biOriginal = biOriginal;
	}
    /**
     * @return BufferedImage biResultado.
     */
    public BufferedImage getBiResultado() {
        return biResultado;
    }
    /**
     * @param BufferedImage biResultado
     */
    public void setBiResultado(BufferedImage biResultado) {
        this.biResultado = biResultado;
    }
//  -----------------------------------------------------------
//   Fim dos métodos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de serviços da classe
//  -----------------------------------------------------------	
  	/**
  	 * Método que percorre a imagem e realiza os calculos
  	 * para converter uma imagem RGB para escala de cinza
  	 */
    public BufferedImage doWork() {
  	    
  	    // criando um novo buffer para saida do resultado
	    this.setBiResultado( new BufferedImage(this.getBiOriginal().getWidth(), this.getBiOriginal().getHeight(), this.getBiOriginal().getType()) );
  	    
		int xIni = 0;
		int xEnd = this.getBiOriginal().getHeight(null) - 1;			
		while( true ) {
		    if( xEnd < xIni ) {
		        break;
		    }
		    int yIni = 0;
			int yEnd = this.getBiOriginal().getWidth(null) - 1;
		    while( true ) {
		        if( yEnd < yIni ) {
			        break;
			    }
		        
		        //	calcula a nova cor do pixel
				Color colorSE = this.calcula( yIni, xIni );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yIni, xIni, colorSE.getRGB() );
		        //	calcula a nova cor do pixel
				Color colorSD = this.calcula( yIni, xEnd );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yIni, xEnd, colorSD.getRGB());
		        //	calcula a nova cor do pixel
				Color colorIE = this.calcula( yEnd, xIni );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yEnd, xIni, colorIE.getRGB() );
		        //	calcula a nova cor do pixel
				Color colorID = this.calcula( yEnd, xEnd );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yEnd, xEnd, colorID.getRGB());
				
		        /*
				// calcula a nova cor do pixel
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yIni, xIni, this.calcula2( yIni, xIni ) );
		        //	calcula a nova cor do pixel
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yIni, xEnd, this.calcula2( yIni, xEnd ) );
		        //	calcula a nova cor do pixel
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yEnd, xIni, this.calcula2( yEnd, xIni ) );
		        //	calcula a nova cor do pixel
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( yEnd, xEnd, this.calcula2( yEnd, xEnd ) );
				*/
				
				yIni++;
		        yEnd--;
		    }
		    xIni++;
		    xEnd--;
		}
  	    
  	    return this.getBiResultado();
  	}
//  -----------------------------------------------------------
//   Fim dos métodos de serviços da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos auxiliares da classe
//  -----------------------------------------------------------
  	/**
  	 * Método de inicialização dos atributos da classe
  	 */
    private void init() {
  	    this.setBiOriginal( null );
  	    this.setBiResultado( null );
  	}
    /**
     * Método que calcula a cor correspondente em cinza
     * @param x
     * @param y
     * @return
     */
    private Color calcula( int x, int y ) {        
		
        Color color = new Color(this.getBiOriginal().getRGB(x, y));
		int cinza = (int) (0.2989*color.getRed() + 0.5870*color.getGreen() + 0.1140*color.getBlue()); // Padrao NTSC
		return new Color(cinza, cinza, cinza);
		
    }
    
    /**
     * Método que calcula bits da cor
     * 
     * @param int x
     * @param int y
     * @return int cinza
     */
    private int calcula2( int x, int y ) {        

		int rgb = this.getBiOriginal().getRGB(x, y);
		
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = (rgb >> 0) & 0xff;
		
		r = (int)( r * 0.2989 );
		g = (int)( g * 0.5870 );
		b = (int)( b * 0.1140 );
		
		int cinza = r + g + g;
									// R				G				B
		rgb = (rgb & 0xff000000)  | ( cinza << 16 ) | ( cinza << 8 ) | ( cinza << 0 );
		
		return rgb;
		
    }
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	
}
