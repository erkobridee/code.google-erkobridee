/*
 * Created on 09/04/2005
 */
package com.erkobridee.calculadora.lexica;

import javax.swing.tree.DefaultMutableTreeNode;

import com.erkobridee.calculadora.lexica.util.InfixToPostfix;
import com.erkobridee.calculadora.lexica.util.PostfixEvaluator;


/**
 * Classe que analisa uma string para execução de uma expressão
 * matemátiva de 4 operações
 * 
 * @author <a href="mailto:erko.bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class Analise {

    /*------------------------------------------------------------------------*
     *  Declaração das variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * <code>expressao</code> -
     * expressão a ser analisada
     */
    private String expressao;
    /**
     * <code>resultado</code> - 
     * resultado do calculo da expressão
     */
    private String resultado;
    /**
     * <code>infixToPostfix</code> - 
     * objeto para conversão da notação infixa para notação pós-fixa
     */
    private InfixToPostfix infixToPostfix; 
    /**
     * <code>postfixEvaluator</code> -
     * objeto para calculo/avaliação da notação pós fixa 
     */
    private PostfixEvaluator postfixEvaluator;
    /** árvore de análise */
    private DefaultMutableTreeNode analise;
    /*------------------------------------------------------------------------*
     *  Fim Declaração das variáveis da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Construtores da classe
     *------------------------------------------------------------------------*/
    /**
     * Construtor sem parametros da classe
     */
    public Analise() {}
    /**
     * Construtor da classe que recebe como parametro
     * a expressão matemática que irá analisar
     * @param String expressao
     */
    public Analise(String expressao) {
        this.setExpressao( expressao );
    }
    /*------------------------------------------------------------------------*
     *  Fim Construtores da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * @return String expressao.
     */
    public String getExpressao() {
        return expressao;
    }
    /**
     * @param String expressao.
     */
    public void setExpressao(String expressao) {
        this.expressao = expressao;
        this.calcula();
    }
    /**
     * @return String resultado.
     */
    public String getResultado() {
        return resultado;
    }
    /**
     * @param String resultado
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    /**
     * @return InfixToPostfix infixToPostfix.
     */
    public InfixToPostfix getInfixToPostfix() {
        return infixToPostfix;
    }
    /**
     * @param InfixToPostfix infixToPostfix.
     */
    public void setInfixToPostfix(InfixToPostfix infixToPostfix) {
        this.infixToPostfix = infixToPostfix;
    }
    /**
     * @return PostfixEvaluator postfixEvaluator.
     */
    public PostfixEvaluator getPostfixEvaluator() {
        return postfixEvaluator;
    }
    /**
     * @param PostfixEvaluator postfixEvaluator.
     */
    public void setPostfixEvaluator(PostfixEvaluator postfixEvaluator) {
        this.postfixEvaluator = postfixEvaluator;
    }
    /**
     * @return DefaultMutableTreeNode analise.
     */
    public DefaultMutableTreeNode getAnalise() {
        return analise;
    }
    /**
     * @param DefaultMutableTreeNode analise.
     */
    public void setAnalise(DefaultMutableTreeNode analise) {
        this.analise = analise;
    }
    /*------------------------------------------------------------------------*
     *  Fim Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*
     *  Serviços da classe
     *------------------------------------------------------------------------*/
    /**
     * Método que realiza o calculo da expressão e retorna o seu resultado
     */
    private void calcula() {
        this.setInfixToPostfix( new InfixToPostfix( this.getExpressao() ) );
        this.setPostfixEvaluator( new PostfixEvaluator( this.getInfixToPostfix() ) );
        this.setResultado( this.getPostfixEvaluator().getResultado()[0].toString() );
        this.setAnalise( this.getPostfixEvaluator().getRootNode() );
    }
    /*------------------------------------------------------------------------*
     *  Fim Serviços da classe
     *------------------------------------------------------------------------*/

}
