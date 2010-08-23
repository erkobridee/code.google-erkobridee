/*
 * Created on 28/09/2005
 */
package com.erkobridee.PID.processamento.mascara;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author Erko Bridee de Almeida Cabrera
 */
public class Quadro implements Runnable {

//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
    /**
     * Nome do quadro que está sendo processado
     */
    private String name;
	/**
	 * Mascara a ser aplicada na imagem
	 */    
    private double[][] mascara;
    /**
     * Imagem original enviada para a classe
     */
    private BufferedImage biOriginal;
    /**
     * Imagem resultado da aplicação da mascara
     */
    private BufferedImage biResultado;
	/**
	 * flag que indica se a imagem em manipulação atual é a RGB ou a em escala 
	 * de cinza
	 */
	private boolean flagEscalaCinza;
	/**
	 * flag para o método de aplicação da mascara que indica se divide ou não
	 * assim calculando um valor resultado
	 */
	private boolean flagDivide = true;
	/**
	 * flag que controla a execução da thread
	 */
	private boolean doCalc = true;
	/**
	 * pontos de processamento da imagem
	 * 
	 * # coordenada inicial
	 * xIni - x inicial
	 * yIni - y inicial
	 * 
	 * # coordenada final
	 * xEnd - x final
	 * yEnd - y final
	 */
	private int xIni, yIni, xEnd, yEnd;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	/**
  	 * Construtor sem parametros da classe
  	 */
	public Quadro() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * Determina a área a ser processada
	 */
	public void setArea( int xIni, int yIni, int xEnd, int yEnd ) {
		this.setIni( xIni, yIni );
		this.setEnd( xEnd, yEnd );
	}
	/**
	 * determina as coordenadas inicial de processamento da 
	 * imagem
	 * 
	 * @param int xIni
	 * @param int yIni
	 */
	public void setIni( int xIni, int yIni ) {
		this.xIni = xIni;
		this.yIni = yIni;
	}
	/**
	 * determina as coordenadas finais de processamento
	 * da imagem
	 * 
	 * @param int xEnd
	 * @param int yEnd
	 */
	public void setEnd( int xEnd, int yEnd ) {
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
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
	 * @return <b>boolean</b> flagDivide
	 */
	public boolean isFlagDivide() {
		return flagDivide;
	}
	/**
	 * @param <b>boolean</b> flagDivide
	 */
	public void setFlagDivide(boolean flagDivide) {
		this.flagDivide = flagDivide;
	}
	/**
	 * @return <b>boolean</b> flagEscalaCinza
	 */
	public boolean isFlagEscalaCinza() {
		return flagEscalaCinza;
	}
	/**
	 * @param <b>boolean</b> flagEscalaCinza
	 */
	public void setFlagEscalaCinza(boolean flagEscalaCinza) {
		this.flagEscalaCinza = flagEscalaCinza;
	}
	/**
	 * @return <b>double[][]</b> mascara
	 */
	public double[][] getMascara() {
		return mascara;
	}
	/**
	 * @param <b>double[][]</b> mascara
	 */
	public void setMascara(double[][] mascara) {
		this.mascara = mascara;
	}
	/**
	 * @return <b>boolean</b> doCalc
	 */
	public boolean isDoCalc() {
		return doCalc;
	}
	/**
	 * @param <b>boolean</b> doCalc
	 */
	private void setDoCalc(boolean doCalc) {
		this.doCalc = doCalc;
	}
	/**
	 * @return String name.
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @param String name
	 */
	public void setName(String name) {
	    this.name = name;
	}
//  -----------------------------------------------------------
//   Fim dos métodos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de serviços da classe
//  -----------------------------------------------------------	
	/**
	 * Método de execução para a Thread
	 */
	/*
	public void run() {

		long iniTime = System.currentTimeMillis();
		
		while( this.isDoCalc() ) {
		    int yIni = this.yIni;
			int yEnd = this.yEnd;		
			while( true ) {
			    if( yEnd < yIni ) {
			    	// para execução da thread
			    	this.setDoCalc( false );
			        break;
			    }
			    int xIni = this.xIni;
				int xEnd = this.xEnd;			    
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
		}
		
		long endTime = System.currentTimeMillis();			
		System.out.println( " * FIM THREAD: " + this.getName() + " * tempo de processamento milis: " + ( endTime - iniTime ) );
	} */
	
	public void run() {

		long iniTime = System.currentTimeMillis();
		
		while( this.isDoCalc() ) {
			for( int y = yIni; y < yEnd; y++ ) {
			    for( int x = xIni; x < xEnd; x++ ) {
			        //	calcula a nova cor do pixel
					Color colorSE = this.calcula( x, y );
					// seta a nova cor do pixel para a imagem
					this.getBiResultado().setRGB( x, y, colorSE.getRGB() );
			    }
			}
			this.setDoCalc( false );
		}
		
		long endTime = System.currentTimeMillis();			
		System.out.println( " * FIM THREAD: " + this.getName() + " * tempo de processamento milis: " + ( endTime - iniTime ) );
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
	    this.setName( "" );
  		this.setBiOriginal( null );
  		this.setBiResultado( null );
  		this.setFlagDivide( true );
  		this.setFlagEscalaCinza( false );
  		this.setDoCalc( true );
  		// mascara default, Passa Baixa - média
		double[][] mascaraIni = {{1,1,1},{1,1,1},{1,1,1}};
		this.setMascara( mascaraIni );
  	}
	/**
	 * Método que calcula a aplicação da mascara para uma região x,y da imagem informada
	 * 
	 * @param int x - pixel posição linha
	 * @param int y - pixel posição coluna
	 */
	private Color calcula( int x, int y ) {
    	
		// objeto referente a imagem que será manipulada
		BufferedImage imagem = this.getBiOriginal();
    	
   		// o tamanho da área considerada dependerá das dimensões da mascara
    	// recupera a quantidade de linhas da mascara
    	int linhas = this.getMascara().length;
    	// recupera a quantidade de colunas da mascara
    	int colunas = this.getMascara()[0].length;
		
		/*
		 *  ---->
		 * +-----+-----+-----+ |
		 * |  *  |     |     | |
		 * +-----+-----+-----+ v
		 * |     | x,y |     |
		 * +-----+-----+-----+
		 * |     |     |     |
		 * +-----+-----+-----+
		 * 
		 *  * - referente a posição onde começa a analise da região aonde será aplicado o filtro no caso: px, py
		 */		
   		/*
    	int px = x - 1;
   		int py = y - 1;    	    	
        */
    	// TODO: VERIFICAR CALCULO DE DESLOCAMENTO INICIAL
    	
    	int px = x - (linhas - 2);
   		int py = y - (colunas - 2);
        
		int pixel = 0; // para uso no calculo da média da imagem em escala de cinza
		
		int pixelR = 0; // calculo da média do canal R
		int pixelG = 0; // calculo da média do canal G
		int pixelB = 0; // calculo da média do canal B		 
		
		int divisor = 0; // total pelo qual deverá ser dividido
		
		for(int i = 0; i < linhas; i++) { 
			for(int j = 0; j < colunas; j++) {
				try {
    				
					Color color = new Color(imagem.getRGB( px, py ));
					// calculo para imagem em escala de cinza
					if( this.isFlagEscalaCinza() ) {						
						int CINZA = (int)( color.getRed() * this.getMascara()[i][j] );
						pixel += CINZA;
												
					// calculo para imagem em RGB	
					} else {
						int R = (int)( color.getRed() * this.getMascara()[i][j] );
						int G = (int)( color.getGreen() * this.getMascara()[i][j] );
						int B = (int)( color.getBlue() * this.getMascara()[i][j] );
						
						pixelR += R;
						pixelG += G;
						pixelB += B;						
						
					}
					// incrementando divisor
					divisor++;
				// caso seja um pixel de borda trata essa situação	
				}catch(Exception e ) {
					//System.out.println("Aplicando mascara: Borda Detectada.");
				}
				py++;
			}		
			px++;
			py = y - 1;;
		}
				
		Color color = null;
		if( this.isFlagEscalaCinza() ) {
			
			// verifica se deve ser dividido ou não
			if( this.isFlagDivide() ) {
				pixel = Math.abs(pixel/divisor);
			}
			
			// verifica se o valor está acima do máximo aceito
			if( pixel > 255 ) { pixel = 255; }	
			// verifica se o valor está abaixo do aceito
			if( pixel < 0 ) { pixel = 0; }	
			
			// carrega um objeto cor com a nova cor
			color = new Color( pixel, pixel, pixel );
		} else {
			// verifica se deve ser dividido ou não
			if( this.isFlagDivide() ) {	
				pixelR = Math.abs( pixelR/divisor );
				pixelG = Math.abs( pixelG/divisor );
				pixelB = Math.abs( pixelB/divisor );
			}
			
			// verifica se o valor está acima do máximo aceito
			if( pixelR > 255 ) { pixelR = 255; }
			if( pixelG > 255 ) { pixelG = 255; }
			if( pixelB > 255 ) { pixelB = 255; }
			// verifica se o valor está abaixo do aceito
			if( pixelR < 0 ) { pixelR = 0; }
			if( pixelG < 0 ) { pixelG = 0; }
			if( pixelB < 0 ) { pixelB = 0; }
			
			// carrega um objeto cor com a nova cor
			color = new Color( pixelR, pixelG, pixelB );
		}
		// retorna a nova cor do pixel
		return color;
	}
	
	/**
	 * Método que clona o objeto e retorna um novo objeto 
	 * carregado
	 * 
	 * @return Quadro - clone
	 */
	public Quadro cloneThis() {
	    Quadro clone = new Quadro();
	    clone.setName( this.getName() );
	    clone.setBiOriginal( this.getBiOriginal() );
	    clone.setBiResultado( this.getBiResultado() );
	    clone.setMascara( this.getMascara() );
	    clone.setFlagDivide( this.isFlagDivide() );
	    clone.setFlagEscalaCinza( this.isFlagEscalaCinza() );
	    return clone;
	}
	
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	

}
