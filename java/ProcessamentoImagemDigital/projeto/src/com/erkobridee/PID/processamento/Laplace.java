/*
 * Created on 15/11/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.erkobridee.PID.processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Erko Bridee de Almeida Cabrera
 *
 * 15/11/2005 <br>
 * <b>Descri��o:</b><br>
 * Classe que implementa os calculos do filtro Laplaciano
 */
public class Laplace {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
    /**
     * Imagem original enviada para a classe
     */
    private BufferedImage biOriginal;
    /**
     * Imagem resultado da aplica��o da mascara
     */
    private BufferedImage biResultado;
	/**
	 * flag que indica se a imagem em manipula��o atual � a RGB ou a em escala 
	 * de cinza
	 */
	private boolean flagEscalaCinza;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	/**
  	 * Construtor sem parametros da classe
  	 */
    public Laplace() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de acesso aos atributos da classe
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
    /**
     * @return boolean flagEscalaCinza.
     */
    public boolean isFlagEscalaCinza() {
        return flagEscalaCinza;
    }
    /**
     * @param boolean flagEscalaCinza
     */
    public void setFlagEscalaCinza(boolean flagEscalaCinza) {
        this.flagEscalaCinza = flagEscalaCinza;
    }
//  -----------------------------------------------------------
//   Fim dos m�todos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de servi�os da classe
//  -----------------------------------------------------------	
  	/**
  	 * M�todo que calcula a mediana para a imagem
  	 * 
  	 * @return BufferedImage - imagem
  	 */
	public BufferedImage doWork() {
	    // criando um novo buffer para saida do resultado
	    this.setBiResultado( new BufferedImage(this.getBiOriginal().getWidth(), this.getBiOriginal().getHeight(), this.getBiOriginal().getType()) );
	    
	    int yIni = 0;
		int yEnd = this.getBiOriginal().getHeight(null) - 1;		
		while( true ) {
		    if( yEnd < yIni ) {
		        break;
		    }
		    int xIni = 0;
			int xEnd = this.getBiOriginal().getWidth(null) - 1;			    
		    while( true ) {
		        if( xEnd < xIni ) {
			        break;
			    }
		        //	calcula a nova cor do pixel
				Color colorSE = this.calcula( xIni, yIni );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( xIni, yIni, colorSE.getRGB() );
		        //	calcula a nova cor do pixel
				Color colorSD = this.calcula( xIni, yEnd );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( xIni, yEnd, colorSD.getRGB());
		        //	calcula a nova cor do pixel
				Color colorIE = this.calcula( xEnd, yIni );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( xEnd, yIni, colorIE.getRGB() );
		        //	calcula a nova cor do pixel
				Color colorID = this.calcula( xEnd, yEnd );
				// seta a nova cor do pixel para a imagem
				this.getBiResultado().setRGB( xEnd, yEnd, colorID.getRGB());
				xIni++;
		        xEnd--;
		    }
		    yIni++;
		    yEnd--;
		}
	    
		// retorna a imagem resultado
		return this.getBiResultado();
	}
//  -----------------------------------------------------------
//   Fim dos m�todos de servi�os da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos auxiliares da classe
//  -----------------------------------------------------------
  	/**
  	 * M�todo de inicializa��o dos atributos da classe em uma
  	 * nova int�ncia��o de um objeto
  	 */
    private void init() {
  	    this.setFlagEscalaCinza( false );
  	    this.setBiOriginal( null );
  	    this.setBiResultado( null );
  	}
    
    /**
     * M�todo que realiza o calculo da nova cor do pixel
     * 
     * @param int x
     * @param int y
     * @return Color - nova cor do pixel calculado
     */
	private Color calcula( int x, int y ) {
	    
	    // TODO: verificar se usa apenas aplica��o de um filtro
	    /*
	     *    0	-1  0
	     * 	 -1  4 -1
	     *    0 -1  0
	     */
	    
        // recupera a cor do pixel da posi��o x,y da imagem origirnal
        Color pixel = new Color(this.getBiOriginal().getRGB(x, y));
        
        // verifica se a imagem est� em escala de cinza
        if( this.isFlagEscalaCinza() ) {
            
        // imagem RGB    
        } else {
            
        }
	    
	    return null;
	}
//  -----------------------------------------------------------
//   Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	
}
