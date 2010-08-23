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
 * <b>Descrição:</b><br>
 * Classe que realiza a quantização da imagem em quantidades 
 * de tons
 */
public class Quantizar {
	
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
     * Quantidade de tons que irá representar
     */
    private int quantizacao;
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
    public Quantizar() { this.init(); }
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
    /**
     * @return int quantizacao.
     */
    public int getQuantizacao() {
        return quantizacao;
    }
    /**
     * @param int quantizacao
     */
    // TODO: VERIFICAR VALIDAÇÕES
    public void setQuantizacao(int quantizacao) {
        if( quantizacao <= 1 ) {
            this.quantizacao = 2;
        } else if( quantizacao >= 255 ) {
            this.quantizacao = 254;
        } else {
            this.quantizacao = quantizacao;
        }
    }
//  -----------------------------------------------------------
//   Fim dos métodos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de serviços da classe
//  -----------------------------------------------------------	
  	/**
  	 * Método que calcula a mediana para a imagem
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
//   Fim dos métodos de serviços da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos auxiliares da classe
//  -----------------------------------------------------------
  	/**
  	 * Método de inicialização dos atributos da classe em uma
  	 * nova intânciação de um objeto
  	 */
    private void init() {
  	    this.setFlagEscalaCinza( false );
  	    this.setBiOriginal( null );
  	    this.setBiResultado( null );
  	    // representa a imagem em apenas 2 tons de cor
  	    this.setQuantizacao( 2 );
  	}
    
    /**
     * Método que realiza o calculo da nova cor do pixel
     * 
     * @param int x
     * @param int y
     * @return Color - nova cor do pixel calculado
     */
	private Color calcula( int x, int y ) {
	    
	    // quantidade total de tons da imagem
        int nt = (int) Math.pow(2, 8);
        // fragmentação da quantidade total em células de determinado valor
        int celula = ( nt / this.getQuantizacao() ) - 1;
	            
        // recupera a cor do pixel da posição x,y da imagem origirnal
        Color pixel = new Color(this.getBiOriginal().getRGB(x, y));

        // verifica se a imagem está em escala de cinza
        if( this.isFlagEscalaCinza() ) {
            
	        int c = new Color(this.getBiOriginal().getRGB(x, y)).getRed();
	    		        
	        boolean bC = true;
	        
	        for (int n = ( this.getQuantizacao() - 1 ); n >= 0; n--) {    	
	            if( n == 0 ) {
	            	if( c <= celula ) { c = 0; }
	            } else if( n == ( this.getQuantizacao() - 1 ) ) {
	            	if( c > ( celula * n ) ) { c = 255; }
	            } else {
	            	if(( c > ( celula * n ) ) && (bC) ) { c = ( celula * ( n + 1 ) ); bC = false; }
	            }
	        }
	        // setando a nova cor do pixel
	        pixel = new Color(c, c, c);
            
        // caso a imagem não esteja em escala de cinza    
        } else {                  
	
	        int r = new Color(this.getBiOriginal().getRGB(x, y)).getRed();
	        int g = new Color(this.getBiOriginal().getRGB(x, y)).getGreen();
	        int b = new Color(this.getBiOriginal().getRGB(x, y)).getBlue();
	    		        
	        boolean bR, bG, bB;
	        bR =  bG = bB = true;
	        
	        for (int n = ( this.getQuantizacao() - 1 ); n >= 0; n--) {    	
	            if( n == 0 ) {
	            	if( r <= celula ) { r = 0; }
	            	if( g <= celula ) { g = 0; }
	            	if( b <= celula ) { b = 0; }
	            } else if( n == ( this.getQuantizacao() - 1 ) ) {
	            	if( r > ( celula * n ) ) { r = 255; }
	            	if( g > ( celula * n ) ) { g = 255; }
	            	if( b > ( celula * n ) ) { b = 255; }
	            } else {
	            	if(( r > ( celula * n ) ) && (bR) ) { r = ( celula * ( n + 1 ) ); bR = false; }
	            	if(( g > ( celula * n ) ) && (bG) ) { g = ( celula * ( n + 1 ) ); bG = false; }
	            	if(( b > ( celula * n ) ) && (bB) ) { b = ( celula * ( n + 1 ) ); bB = false; }
	            }
	        }
	        // setando a nova cor do pixel
	        pixel = new Color(r, g, b);
	        
        }        
        
	    return pixel;
	}
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	
}
