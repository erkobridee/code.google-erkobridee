package com.erkobridee.PID.processamento;



import java.awt.image.BufferedImage;

import com.erkobridee.PID.util.ImageIOController;


public class Filtros {
	
	/*-----------------------------------------------------------------------
	 * Atributos da classe
	 *-----------------------------------------------------------------------*/
	/**
	 * imagem original carregada
	 */
	private BufferedImage biOriginal;
	/**
	 * imagem resultado
	 */
	private BufferedImage biResultado;
	/**
	 * define a mascara aplicada no calculo de um determinado filtro qualquer
	 */
	private double[][] mascara;
	/**
	 * flag que indica se a imagem em manipula��o atual � a RGB ou a em escala 
	 * de cinza
	 */
	private boolean flagEscalaCinza;
	/**
	 * flag para o m�todo de aplica��o da mascara que indica se divide ou n�o
	 * assim calculando um valor resultado
	 */
	private boolean flagDivide = false;
	/*-----------------------------------------------------------------------
	 * Fim dos atributos da classe
	 *-----------------------------------------------------------------------*/

	
	/*-----------------------------------------------------------------------
	 * Construtores da classe
	 *-----------------------------------------------------------------------*/
	/**
	 * Construtor sem parametros da classe que carrega as configura��es 
	 * default para a classe
	 */
	public Filtros() { this.init(); }
	/**
	 * Construtor da classe que recebe como parametro o caminho
	 * da imagem que dever� ser carregada
	 * 
	 * @param String arquivo
	 */
	public Filtros( String arquivo ) { 
		this.init();
		this.setBiOriginal( ImageIOController.read( arquivo ) ); 
	}	
	/**
	 * M�todo de inicializa��o da classe, com as configura��es default
	 * da classe
	 */
	private  void init() {
		this.setFlagEscalaCinza( false );
		this.setFlagDivide( true );
		this.setBiOriginal( null );
		double[][] mascaraIni = {{1,1,1},{1,1,1},{1,1,1}};
		this.setMascara( mascaraIni );
	}
	/*-----------------------------------------------------------------------
	 * Fim dos construtores da classe
	 *-----------------------------------------------------------------------*/	

	
	/*-----------------------------------------------------------------------
	 * M�todos de acesso a atributos da classe
	 *-----------------------------------------------------------------------*/
	/**
	 * @return <b>double[][]</b> mascara
	 */
	public double[][] getMascara() {
		return this.mascara;
	}
	/**
	 * @param <b>double[][]</b> mascara
	 */
	public void setMascara(double[][] mascara) {
		this.mascara = mascara;
	}
	/**
	 * @return <b>BufferedImage</b> original
	 */
	public BufferedImage getBiOriginal() {
		return this.biOriginal;
	}
	/**
	 * @param <b>BufferedImage</b> original
	 */
	public void setBiOriginal(BufferedImage original) {
		this.biOriginal = original;
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
	 * @return <b>boolean</b> flagEscalaCinza
	 */
	public boolean isFlagEscalaCinza() {
		return this.flagEscalaCinza;
	}
	/**
	 * @param <b>boolean</b> flagEscalaCinza
	 */
	public void setFlagEscalaCinza(boolean flagEscalaCinza) {
		this.flagEscalaCinza = flagEscalaCinza;
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
	/*-----------------------------------------------------------------------
	 * Fim dos m�todos de acesso a atributos da classe
	 *-----------------------------------------------------------------------*/

	
	/*-----------------------------------------------------------------------
	 * M�todos ultilitarios da classe
	 *-----------------------------------------------------------------------*/
	/**
	 * Retorna a altura da imagem que esta sendo utilizada no momento
	 * 
	 * @return int
	 */
	public int getAltura() { 
		return biOriginal.getHeight();	
	}
	/**
	 * Retorna a largura da imagem que esta sendo utilizada no momento
	 * 
	 * @return int
	 */	
	public int getLargura() {
		return biOriginal.getWidth();
	}
	/**
	 * M�todo que converte a imagem original para a escala de cinza
	 * e carrega o buffer de imagem referente a imagem em escala
	 * de cinza
	 */
	public void grayScale() {
		this.setBiOriginal( this.EscalaCinza() );
		this.setFlagEscalaCinza( true );
	}
	/*-----------------------------------------------------------------------
	 * Fim dos m�todos ultilitarios da classe
	 *-----------------------------------------------------------------------*/

	/**
	 * converte a imagem RBG para escala de cinza
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage EscalaCinza() { 
	    EscalaCinza escalaCinza = new EscalaCinza();
	    escalaCinza.setBiOriginal( this.getBiOriginal() );
	    return escalaCinza.doWork();
	}
	
	/**
	 * M�todo que realiza a quantiza��o da imagem em quantidade  de tons
	 * 
	 * @param int tons
	 * @return BufferedImage
	 */
	public BufferedImage Quantizacao( int tons ) {
	    
	    Quantizar quantizar = new Quantizar();
	    quantizar.setFlagEscalaCinza( this.isFlagEscalaCinza() );
	    quantizar.setBiOriginal( this.getBiOriginal() );
	    quantizar.setQuantizacao( tons );
	    return quantizar.doWork();
	    
	}
	
	/**
	 * Filtro para Limiariza��o(Threshold)
	 * 
	 * @param int nivel - referente ao nivel de limiariza��o a ser aplicado na imagem
	 * @return BufferedImage limiarizado
	 */
	public BufferedImage Limiarizacao(int nivel) { 
		    
		Limiarizacao limiarizacao = new Limiarizacao();
		limiarizacao.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		limiarizacao.setBiOriginal( this.getBiOriginal() );
		limiarizacao.setLimiar( nivel );
		return limiarizacao.doWork();
		
	}
 
	/**
	 * Filtro de M�dia utilizando Matriz default 3x3
	 * 
	 * @return BufferedImage - filtro de m�dia aplicado
	 */	
	public BufferedImage Media() {
		
		// definindo a mascara
		double[][] mascara = {{1,1,1},{1,1,1},{1,1,1}};		
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( true );
		return aplicaMascara.doWork();
		
	}
    
	/**
	 * M�todo qua aplica o filtro do valor m�dio
	 * 
	 * @return BufferedImage - imagem com filtro aplicado
	 */
	public BufferedImage Mediana() {
	    
		CalculaMediana calculaMediana = new CalculaMediana();
		calculaMediana.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		calculaMediana.setBiOriginal( this.getBiOriginal() );
		return calculaMediana.doWork();
		
	}
	
	/**
	 * M�todo que aplica a passa alta na imagem
	 * 
	 * @return BufferedImage - retorna a imagem com o filtro da passa alta
	 */
	public BufferedImage PassaAlta() {
			
		// definindo a mascara
		double[][] mascara = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};		
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( false );
		return aplicaMascara.doWork();
		
	}	

	/**
	 * M�todo que aplica que aplica o filtro Righ Boost
	 * 
	 * @return BufferedImage - imagem com filtro Righ Boost
	 */
	public BufferedImage AltoReforco( double A ) {
	
		/*
		 * se o fator de amplia��o for menor que 1 seta ele como 1 assim sendo um filtro
		 * passa alta
		 */
		if( A < 1 ) { A = 1; }
		
		// calcula o fator de amplia��o
		double W = (( 9 * A ) - 1);
		// defini��o da mascara
		double[][] mascara = {{-1,-1,-1},{-1,W,-1},{-1,-1,-1}};
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( false );
		return aplicaMascara.doWork();
		
	}	
	
	/**
	 * M�todo que aplica o filtro de Roberts
	 * 
	 * @return BufferedImage - imagem resultado
	 */
	public BufferedImage Roberts() {
	    
	    Roberts roberts = new Roberts();
	    roberts.setFlagEscalaCinza( this.isFlagEscalaCinza() );
	    roberts.setBiOriginal( this.getBiOriginal() );
	    return roberts.doWork();	
	    
	}
	
	/**
	 * M�todo que aplica o filtro de Sobel
	 * 
	 * @return BufferedImage - imagem resultado
	 */
	public BufferedImage Sobel() {
	    
	    Sobel sobel = new Sobel();
	    sobel.setFlagEscalaCinza( this.isFlagEscalaCinza() );
	    sobel.setBiOriginal( this.getBiOriginal() );
	    return sobel.doWork();
	    
	}
	
	/**
	 * M�todo que aplica o filtro de Prewitt
	 * 
	 * @return BufferedImage - imagem resultado
	 */
	public BufferedImage Prewitt() {
	    
	    Prewitt prewitt = new Prewitt();
	    prewitt.setFlagEscalaCinza( this.isFlagEscalaCinza() );
	    prewitt.setBiOriginal( this.getBiOriginal() );
	    return prewitt.doWork();
	    
	}

	/**
	 * M�todo que aplica o filtro de Laplace
	 * 
	 * @return BufferedImage - imagem resultado
	 */
	public BufferedImage Laplace() {
		
		// definindo a mascara
		double[][] mascara = {{0,-1,0},{-1,4,-1},{0,-1,0}};		
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( false );
		return aplicaMascara.doWork();
		
	}
	
	/**
	 * M�todo que aplica uma mascara customizada
	 * 
	 * @param double[][] mascara - mascara a ser aplicada
	 * @param boolean divide - indica se divide oun�o o resultado do calculo
	 * @return BufferedImage - imagem resultado
	 */
	public BufferedImage MascaraCustomizada( double[][] mascara, boolean divide ) {
	    
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( divide );
		return aplicaMascara.doWork();
		
	}
}