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
	 * flag que indica se a imagem em manipulação atual é a RGB ou a em escala 
	 * de cinza
	 */
	private boolean flagEscalaCinza;
	/**
	 * flag para o método de aplicação da mascara que indica se divide ou não
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
	 * Construtor sem parametros da classe que carrega as configurações 
	 * default para a classe
	 */
	public Filtros() { this.init(); }
	/**
	 * Construtor da classe que recebe como parametro o caminho
	 * da imagem que deverá ser carregada
	 * 
	 * @param String arquivo
	 */
	public Filtros( String arquivo ) { 
		this.init();
		this.setBiOriginal( ImageIOController.read( arquivo ) ); 
	}	
	/**
	 * Método de inicialização da classe, com as configurações default
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
	 * Métodos de acesso a atributos da classe
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
	 * Fim dos métodos de acesso a atributos da classe
	 *-----------------------------------------------------------------------*/

	
	/*-----------------------------------------------------------------------
	 * Métodos ultilitarios da classe
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
	 * Método que converte a imagem original para a escala de cinza
	 * e carrega o buffer de imagem referente a imagem em escala
	 * de cinza
	 */
	public void grayScale() {
		this.setBiOriginal( this.EscalaCinza() );
		this.setFlagEscalaCinza( true );
	}
	/*-----------------------------------------------------------------------
	 * Fim dos métodos ultilitarios da classe
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
	 * Método que realiza a quantização da imagem em quantidade  de tons
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
	 * Filtro para Limiarização(Threshold)
	 * 
	 * @param int nivel - referente ao nivel de limiarização a ser aplicado na imagem
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
	 * Filtro de Média utilizando Matriz default 3x3
	 * 
	 * @return BufferedImage - filtro de média aplicado
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
	 * Método qua aplica o filtro do valor médio
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
	 * Método que aplica a passa alta na imagem
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
	 * Método que aplica que aplica o filtro Righ Boost
	 * 
	 * @return BufferedImage - imagem com filtro Righ Boost
	 */
	public BufferedImage AltoReforco( double A ) {
	
		/*
		 * se o fator de ampliação for menor que 1 seta ele como 1 assim sendo um filtro
		 * passa alta
		 */
		if( A < 1 ) { A = 1; }
		
		// calcula o fator de ampliação
		double W = (( 9 * A ) - 1);
		// definição da mascara
		double[][] mascara = {{-1,-1,-1},{-1,W,-1},{-1,-1,-1}};
		AplicaMascara aplicaMascara = new AplicaMascara();
		aplicaMascara.setFlagEscalaCinza( this.isFlagEscalaCinza() );
		aplicaMascara.setBiOriginal( this.getBiOriginal() );
		aplicaMascara.setMascara( mascara );
		aplicaMascara.setFlagDivide( false );
		return aplicaMascara.doWork();
		
	}	
	
	/**
	 * Método que aplica o filtro de Roberts
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
	 * Método que aplica o filtro de Sobel
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
	 * Método que aplica o filtro de Prewitt
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
	 * Método que aplica o filtro de Laplace
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
	 * Método que aplica uma mascara customizada
	 * 
	 * @param double[][] mascara - mascara a ser aplicada
	 * @param boolean divide - indica se divide ounão o resultado do calculo
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