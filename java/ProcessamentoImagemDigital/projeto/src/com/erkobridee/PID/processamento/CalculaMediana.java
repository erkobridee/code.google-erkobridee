/*
 * Created on 27/09/2005
 */
package com.erkobridee.PID.processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.erkobridee.PID.util.ColorValue;


/**
 * @author Erko Bridee de Almeida Cabrera
 * 
 * <br><br>
 * <b>Descri��o:</b><br>
 */
public class CalculaMediana {
	
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
  	/**
  	 * Dimens�o a ser considerada
  	 */
  	private int dimensao;
      
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------

  	/**
  	 * Construtor default da classe sem parametros
  	 */
  	public CalculaMediana() {
  	    /*
  	     * chama o m�todo de inicializa��o dos atributos
  	     * da classe
  	     */ 
  	    this.init();
  	}
  	
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
      /**
       * @return int dimensao.
       */
      public int getDimensao() {
          return dimensao;
      }
      /**
       * @param int dimensao
       */
      public void setDimensao(int dimensao) {
          this.dimensao = dimensao;
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
	    	    
	    long iniTime = System.currentTimeMillis();
	    
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
	    
		long endTime = System.currentTimeMillis();
		System.out.println( "tempo de processamento milis: " + ( endTime - iniTime ) );
		
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
  	 * M�todo de inicializa��o dos atributos da classe
  	 * configura��es default para um novo objeto 
  	 * da classe que � instanciado
  	 */
  	private void init() {
  		this.setFlagEscalaCinza( false );
  		this.setBiOriginal( null );
  		this.setBiResultado( null );
  		this.setDimensao( 3 ); // dimens�o default
  	}
	/**
	 * M�todo que retorna o valor m�dio da vizinhan�a do pixel atual
	 * @param int x
	 * @param int y
	 * @return Color - pixel de valor m�dio
	 */
	private Color calcula( int x, int y ) {
    	
		// objeto referente a imagem que ser� manipulada
		BufferedImage imagem = this.getBiOriginal();
    	
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
		 *  * - referente a posi��o onde come�a a analise da regi�o aonde ser� aplicado o filtro no caso: px, py
		 */		
   		int px = x - 1;
   		int py = y - 1;
		
		// para armazenar as cores que ser�o analisadas
		LinkedList lista = new LinkedList();
    	
		for(int i = 0; i < this.getDimensao(); i++) { 
			for(int j = 0; j < this.getDimensao(); j++) {
				try {
    				
					// recupera a cor do pixel na imagem
					Color color = new Color(imagem.getRGB( px, py ));
					// estrutura que armazena a cor da imagem com seu respectivo valor de identifica��o
					ColorValue colorValue = new ColorValue();
					colorValue.setCor( color );
					
					// calculo para imagem em escala de cinza
					if( this.isFlagEscalaCinza() ) {
						colorValue.setValor( (int)color.getRed() );
					// calculo para imagem em RGB	
					} else {
						int valor = 0;
						valor += (int) color.getRed();
						valor += (int) color.getGreen();
						valor += (int) color.getBlue();
						valor /= 3;
						colorValue.setValor( Math.abs( valor ) );
					}
					
					// adiciona na lista de valores
					lista.add( colorValue );
					
				// caso seja um pixel de borda trata essa situa��o	
				}catch(Exception e ) {
					//System.out.println("Calculo valor medio: Borda Detectada.");
				}
				py++;
			}		
			px++;
			py = y - 1;;
		}

		// recupera o vetor
		Object[] array = lista.toArray();
		// ordenando com algoritmo quick sort
		this.quick( array, 0, (array.length-1) );
		// calcula o indice do meio
		int indiceMeio = Math.abs( ( array.length / 2 ) );
		// recupera o elemento do indice do meio do array
		//ColorValue colorValue = (ColorValue)array[indiceMeio];		
		// recuperando o objeto cor
		//Color color = colorValue.getCor();			
		// retorna a nova cor do pixel
		//return color;
		return ((ColorValue)array[indiceMeio]).getCor();
	}
	
	/**
	 * Algoritmo de ordena��o quick sort
	 * 
	 * @param Object[] Q - array a ser ordenado
	 * @param int inicio - primeiro �ndice
	 * @param int fim - ultimo �ndice
	 */
	private void quick( Object[] Q, int inicio, int fim) {
		int meio;
		// se o primeiro �ndice for menor que o ultimo
		if ( inicio < fim )  {
			// particiona e retorna o �ndice do meio
			meio = particiona( Q, inicio, fim );
			// ordena a primeira parte
			quick( Q, inicio, meio-1 );
			// ordena a ultima parte
			quick( Q, meio+1, fim );
		}
	}

	/**
	 * Algoritmo de particionamento utilizado pelo quick sort
	 * 
	 * realiza as trocas necess�rias de acordo com o valor
	 * 
	 * @param Object[] Q - array a ser ordenado
	 * @param int inicio - primeiro �ndice
	 * @param int fim - ultimo �ndice
	 * @return int - �ndice do meio
	 */
	private int particiona( Object[] Q, int inicio, int fim ) {
		   
		int ultbaixo = inicio;

		ColorValue aux = null;
		ColorValue pivo = (ColorValue)Q[inicio];
		 
		for( int i = inicio+1; i <= fim; i++ ) {
			ColorValue atual = (ColorValue)Q[i];
			if ( atual.getValor() <= pivo.getValor() ) {
		         ultbaixo++;
		         aux = (ColorValue)Q[i];
		         Q[i] = Q[ultbaixo];
		         Q[ultbaixo] = aux;
			}
		}

		aux = (ColorValue)Q[inicio];
		Q[inicio] = Q[ultbaixo];
		Q[ultbaixo] = aux;
		   
		return ultbaixo;
	}
//  -----------------------------------------------------------
//   	Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	
}
