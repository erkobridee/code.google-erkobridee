/*
 * Created on 28/09/2005
 */
package com.erkobridee.PID.processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Erko Bridee de Almeida Cabrera
 *
 * <br><br>
 * <b>Descrição:</B><br>
 * Classe responsável pelo calculo da limiarização
 * da imagem
 */
public class Limiarizacao {
	
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
    /**
     * Valor do limiar utilizado para a imagem
     */
    private int limiar;
	/**
	 * flag que indica se a imagem em manipulação atual é a RGB ou a em escala 
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
    public Limiarizacao() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * @return <b>BufferedImage</b> biOriginal
	 */
	public BufferedImage getBiOriginal() {
		return biOriginal;
	}
	/**
	 * @param <b>BufferedImage</b> biOriginal
	 */
	public void setBiOriginal(BufferedImage biOriginal) {
		this.biOriginal = biOriginal;
	}
	/**
	 * @return <b>BufferedImage</b> biResultado
	 */
	public BufferedImage getBiResultado() {
		return biResultado;
	}
	/**
	 * @param <b>BufferedImage</b> biResultado
	 */
	public void setBiResultado(BufferedImage biResultado) {
		this.biResultado = biResultado;
	}
	/**
	 * @return <b>int</b> limiar
	 */
	public int getLimiar() {
		return limiar;
	}
	/**
	 * @param <b>int</b> limiar
	 */
	public void setLimiar(int limiar) {	    
		/*
		 *  verifica se o intervalo está fora do permitido
		 *  caso esteja considera o nível como sendo 127
		 * 
		 *  0 a 255
		 */
		if(limiar > 255 || limiar <= 0) {
		    this.limiar = 127;
		} else {
		    this.limiar = limiar;
		}	   		
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
//   Fim dos métodos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de serviços da classe
//  -----------------------------------------------------------	
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
				yIni++;
		        yEnd--;
		    }
		    xIni++;
		    xEnd--;
		}
		
		// retorna a imagem com o filtro da média aplicado
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
  	 * em uma nova instância de objeto
  	 */
	private void init() {
	    this.setFlagEscalaCinza( false );
	    this.setBiOriginal( null );
	    this.setBiResultado( null );
	    this.setLimiar( 127 );
	}
	
	/**
	 * Calcula a limiarização do pixel da coordenada x, y
	 * 
	 * @param int x
	 * @param int y
	 * @return Color - nova cor do pixel
	 */
	private Color calcula( int x, int y ) {
		// recupera a cor do pixel na posição atual
		Color color = new Color(this.getBiOriginal().getRGB(x, y));
		// calculo para a imagem em escala de cinza
		if( this.isFlagEscalaCinza() ) {
			int cinza = (int) color.getRed();                
			if (cinza >= this.getLimiar()) {
				color = Color.WHITE;
			}else {
				color = Color.BLACK;
			}
		// calculo para a imagem RGB	
		} else {
			int R = (int)color.getRed(); // recuperando o canal R
			int G = (int)color.getGreen(); // recuperando o canal G
			int B = (int)color.getBlue(); // recuperando o canal B
			// verificando o nível
				// canal R
			if (R >= this.getLimiar()) {
				R = 255;
			}else {
				R = 0;
			}
				//  canal G
			if (G >= this.getLimiar()) {
				G = 255;
			}else {
				G = 0;
			}
				//  canal B
			if (B >= this.getLimiar()) {
				B = 255;
			}else {
				B = 0;
			}
			// define a nova cor
			color = new Color( R, G, B );
		}
		// retorna a cor limiarizada
	    return color;
	}
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	
}
