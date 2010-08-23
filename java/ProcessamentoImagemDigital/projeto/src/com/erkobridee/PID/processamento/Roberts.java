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
 * <b>Descrição:</b><br>
 * Classe que implementa os calculos do filtro de Roberts
 */
public class Roberts {
	
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
    public Roberts() { this.init(); }
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
		int yEnd = this.getBiOriginal().getHeight(null) - 2;		
		while( true ) {
		    if( yEnd < yIni ) {
		        break;
		    }
		    int xIni = 0;
			int xEnd = this.getBiOriginal().getWidth(null) - 2;			    
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
  	}
    
    /**
     * Método que realiza o calculo da nova cor do pixel
     * 
     * @param int x
     * @param int y
     * @return Color - nova cor do pixel calculado
     */
	private Color calcula( int x, int y ) {
	    
	    // pixel resultado dos calculos
	    Color resultado = null;
	    
	    // matrix de pixels considerados
	    Color[] pixel = new Color[4];
	          
	    // TODO: TESTAR CASO DE BORDAS INFERIOR E DIREITA
	    
	    // recuperando os pixels
        pixel[0] = new Color(this.getBiOriginal().getRGB( x, y ));
        pixel[1] = new Color(this.getBiOriginal().getRGB( x+1, y+1 ));
        pixel[2] = new Color(this.getBiOriginal().getRGB( x+1, y ));
        pixel[3] = new Color(this.getBiOriginal().getRGB( x, y+1 ));
        
        // verifica se a imagem está em escala de cinza
        if( this.isFlagEscalaCinza() ) {
            /*
            int[] cinza = new int[4];
            cinza[0] = pixel[0].getRed();
            cinza[1] = pixel[1].getRed();
            cinza[2] = pixel[2].getRed();
            cinza[3] = pixel[3].getRed();
            
            int resultadoCinza = this.hipotenusa( (cinza[0] - cinza[1]), (cinza[2] - cinza[3]) );
            */           
            int resultadoCinza = this.hipotenusa( (pixel[0].getRed() - pixel[1].getRed()), (pixel[2].getRed() - pixel[3].getRed()) );
            
            // setando o objeto de saída
            resultado = new Color( resultadoCinza, resultadoCinza, resultadoCinza );
            
        // imagem RGB    
        } else {
            /*
            int[] red = new int[4];
            red[0] = pixel[0].getRed();
            red[1] = pixel[1].getRed();
            red[2] = pixel[2].getRed();
            red[3] = pixel[3].getRed();
            
            int resultadoRed = this.hipotenusa( (red[0] - red[1]), (red[2] - red[3]) );
            */
            int resultadoRed = this.hipotenusa( (pixel[0].getRed() - pixel[1].getRed()), (pixel[2].getRed() - pixel[3].getRed()) );
            if( resultadoRed > 255 ) { 
                System.out.println( "red: " + resultadoRed );
                resultadoRed = 255;
            } else if( resultadoRed < 0 ) {
                System.out.println( "red: " + resultadoRed );
                resultadoRed = 0;
            }
            
            /*
            int[] green = new int[4];
            green[0] = pixel[0].getGreen();
            green[1] = pixel[1].getGreen();
            green[2] = pixel[2].getGreen();
            green[3] = pixel[3].getGreen();
            
            int resultadoGreen = this.hipotenusa( (green[0] - green[1]), (green[2] - green[3]) );
            */
            int resultadoGreen = this.hipotenusa( (pixel[0].getGreen() - pixel[1].getGreen()), (pixel[2].getGreen() - pixel[3].getGreen()) );            
            if( resultadoGreen > 255 ) { 
                System.out.println( "green: " + resultadoGreen );
                resultadoGreen = 255;
            } else if( resultadoGreen < 0 ) {
                System.out.println( "green: " + resultadoGreen );
                resultadoGreen = 0;
            }
            
            /*
            int[] blue = new int[4];
            blue[0] = pixel[0].getBlue();
            blue[1] = pixel[1].getBlue();
            blue[2] = pixel[2].getBlue();
            blue[3] = pixel[3].getBlue();
            
            int resultadoBlue = this.hipotenusa( (blue[0] - blue[1]), (blue[2] - blue[3]) );
            */
            int resultadoBlue = this.hipotenusa( (pixel[0].getBlue() - pixel[1].getBlue()), (pixel[2].getBlue() - pixel[3].getBlue()) );
            if( resultadoBlue > 255 ) { 
                System.out.println( "blue: " + resultadoBlue );
                resultadoBlue = 255;
            } else if( resultadoBlue < 0 ) { 
                System.out.println( "blue: " + resultadoBlue );
                resultadoBlue = 0;
            }
            
            // setando o objeto de saída | Color(r, g, b);
            resultado = new Color( resultadoRed, resultadoGreen, resultadoBlue );
            
        }
	    
        // retornando o objeto de saída do método
	    return resultado;
	}

	/**
	 * Método que calcula a hipotenusa de 2 valores repassados
	 * 
	 * @param int a
	 * @param int b
	 * @return int - resultado do calculo
	 */
	private int hipotenusa( int a, int b ) {
	    
	    int expo = 2;
	    double raiz = 0.5;
	    double somaQuad = Math.pow( a, expo ) + Math.pow( b, expo );
	    
	    return (int)( Math.pow( somaQuad, raiz ) );
	}
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	
}
