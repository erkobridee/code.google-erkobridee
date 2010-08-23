/*
 * Created on 15/09/2005
 */
package com.erkobridee.PID.util;

import java.awt.Color;

/**
 * @author Erko Bridee de Almeida Cabrera
 * <br><br>
 * Classe que é um estrutura onde armazena a informação de cor
 * e o seu respectivo valor
 */
public class ColorValue {
	
	/**
	 * valor que representa a cor
	 */
	private int valor;
	/**
	 * Cor em questão
	 */
	private Color cor;
	
	/**
	 * Construtor sem parametros da classe
	 */
	public ColorValue() {}

	/**
	 * @return <b>Color</b> cor
	 */
	public Color getCor() {
		return cor;
	}
	/**
	 * @param <b>Color</b> cor
	 */
	public void setCor(Color cor) {
		this.cor = cor;
	}
	/**
	 * @return <b>int</b> valor
	 */
	public int getValor() {
		return valor;
	}
	/**
	 * @param <b>int</b> valor
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}
}
