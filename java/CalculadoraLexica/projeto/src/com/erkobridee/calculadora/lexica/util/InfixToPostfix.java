/*
 * Created on 09/04/2005
 */
package com.erkobridee.calculadora.lexica.util;

import java.util.LinkedList;


/**
 * Classe que recebe uma expressão matemática e infixa Ex.: (6+2)*3
 * e a converte para uma notação pós - fixa -> 6 2 + 3 *
 * 
 * @author <a href="mailto:bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class InfixToPostfix {

    /*------------------------------------------------------------------------*
     *  Declaração das variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * <code>notacaoPosFixa</code> -
     * estrutura da notação pós-fixa em uma estrutuda de lista 
     */
    private LinkedList notacaoPosFixa;
    /** pilha para auxiliar na conversão */
    private Pilha pilha;
    /** notação infixa a ser convertida */
    private String infix;
    /** resultado, notação pós-fixa */
    private String postfix;
    /*------------------------------------------------------------------------*
     *  Fim Declaração das variáveis da classe
     *------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*
     *  Construtores da classe
     *------------------------------------------------------------------------*/
    /** Construtor sem parametros da classe */
    public InfixToPostfix() {
        // inicializa pilha
        this.setPilha( new Pilha() );
        // inicializando estrutura que armazena a notação pós-fixa como lista
        this.setNotacaoPosFixa( new LinkedList() );
        // inicializa em vazia a notação infixa
        this.setInfix("");
        // inicializa em vazia a notação pós-fixa
        this.setPostfix("");
    }
    /**
     * Construtor da classe que recebe como parametro
     * a notação infixa a ser convertida
     * 
     * @param String infix
     */
    public InfixToPostfix( String infix ) {
        // inicializa pilha
        this.setPilha( new Pilha() );
        // inicializando estrutura que armazena a notação pós-fixa como lista
        this.setNotacaoPosFixa( new LinkedList() );
        // repassa notação infixa
        this.setInfix( infix );
        // inicializa em vazia a notação pós-fixa
        this.setPostfix("");
        // chama método de conversão
        this.convertToPostfix();
    }
    /*------------------------------------------------------------------------*
     *  Fim Construtores da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * @return Pilha pilha.
     */
    public Pilha getPilha() {
        return pilha;
    }
    /**
     * @param Pilha pilha.
     */
    public void setPilha(Pilha pilha) {
        this.pilha = pilha;
    }
    /**
     * @return String postfix.
     */
    public String getPostfix() {
        return postfix;
    }
    /**
     * @return LinkedList notacaoPosFixa.
     */
    public LinkedList getNotacaoPosFixa() {
        return notacaoPosFixa;
    }
    /**
     * @param LinkedList notacaoPosFixa.
     */
    public void setNotacaoPosFixa(LinkedList notacaoPosFixa) {
        this.notacaoPosFixa = notacaoPosFixa;
    }
    /**
     * @param String postfix.
     */
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
    /**
     * @return String infix.
     */
    public String getInfix() {
        return infix;
    }
    /**
     * @param String infix.
     */
    public void setInfix(String infix) {
        this.infix = infix;
    }
    /*------------------------------------------------------------------------*
     *  Fim Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Serviços da classe
     *------------------------------------------------------------------------*/    
    /**
     * Método que converte de notação infixa para pós-fixa
     * 
     * @param String infix - notação infixa
     */
    public void convertToPostfix( String infix ) {
        this.setInfix( infix );
        this.convertToPostfix();
    }
    /**
     * Método que converte de notação infixa para pós-fixa
     */
    public void convertToPostfix() {
        String numero = "";
        for( int i = 0; i<this.getInfix().length(); i++ ) {
            char c = this.getInfix().charAt(i);
            // vetificando parenteses
            switch( c ) {
            	// abrindo parenteses
            	case '(':
            	    this.getPilha().push( ""+ c +"" );
            	    break;
            	/*
            	 * Fechando parenteses desempilha tudo até o fecha
            	 * parenteses 
            	 */
            	case ')':
            	    if( numero.length() > 0 ) {
	            	    this.addNu(numero);
	            	    numero = "";
            	    }
            	    while( !this.getPilha().isEmpty() ) {
            	        String op = this.getPilha().pop().toString();
            	        if( op.equals("(") ) { break; }
            	        this.addOp(op);
            	    }
            	    break;
            }
            // verificando número
            if( this.isNumPart(c)  ) { numero += ""+c; }
            // verificando se é operador
            if( this.isOperator(c) ) {
                this.addNu(numero);
                this.trataOperador(c);
                numero = "";
            }
            if( i == this.getInfix().length()-1 ) {
                this.addNu(numero);
            }
        }
        // se a pilha não estiver vazia desempilha operador
        while( !this.getPilha().isEmpty() ) {
	        String op = this.getPilha().pop().toString();
	        if( op.equals("(") ) { break; }
	        this.addOp(op);
	    }
    }
    /**
     * Método que realiza as verificações necessárias sobre um
     * novo operador encontrado
     * @param c
     */
    private void trataOperador( char c ) {
        if( this.getPilha().isEmpty() ) {
            this.getPilha().push( ""+ c +"" );
            this.newNu();
        } else {
            /*
             * verificando se o operador inserido na pilha tem
             * precedencia maior que o operador atual
             */
            if( this.precedence( ""+c+"", this.getPilha().stackTop() ) ) {
                this.getPilha().push( ""+ c +"" );
                this.newNu();
            } else {
                /*
                 * enquanto a precedencia do operador for menor
                 * do que os da pilha desempilha operador pilha
                 * no final insere operador atual na pilha 
                 */
                while( true ) {
        	        if( !this.getPilha().isEmpty() ) {
	                    if( this.precedence( ""+c+"", this.getPilha().stackTop() )  ) { 
	        	            this.getPilha().push( ""+ c +"" );
	        	            break; 
	        	        }
        	        } else {
        	            this.getPilha().push( ""+ c +"" );
        	            break;
        	        }
        	        String op = this.getPilha().pop().toString();
        	        this.addOp(op);
        	    }
            }
        }
    }
    /**
     * novo número
     */
    private void newNu() {
        if( !this.getPostfix().endsWith(" ") ) {
            this.setPostfix( this.getPostfix() + " " );
        }
    }
    /**
     * concatena o número na notação pós-fixa
     * @param String nu
     */
    private void addNu( String nu ) {
        if( nu.length() > 0 ) {
	        this.getNotacaoPosFixa().addLast( nu );
	        this.setPostfix( this.getPostfix() + nu );
        }
    }
    /**
     * adiciona um operador na notação pós-fixa
     * @param op
     */
    private void addOp( String op ) {
        this.getNotacaoPosFixa().addLast( op );
        if( this.getPostfix().endsWith(" ") ) {
            this.setPostfix( this.getPostfix() + op + " " );
        } else {
            this.setPostfix( this.getPostfix() + " " + op + " " );
        }
        
    }
    /**
     * Método que avalia a precedencia dos operadores e retorna <b>true</b>
     * caso o operador1 tenha precedencia maior que operador2
     * 
     * @param Object operator1
     * @param Object operator2
     * @return boolean
     */
    private boolean precedence( Object operator1, Object operator2 ) {        
        int vo1 = this.operatorPrecedenceValue( operator1 ), vo2 = this.operatorPrecedenceValue( operator2 );
        if( vo1 < vo2 ) {
            return true;
        }
        return false;
    }
    /**
     * Método que retorna o valor da pecedencia do operador
     * @param Object operator
     * @return int - valor de precedencia
     */
    private int operatorPrecedenceValue( Object operator ) {
        /*
         * ! = 0 	- fatorial
         * ^ = 1	- exponenciação
         * * = 2	- multiplicação
         * / = 2	- divisão
         * % = 2	- operação de resto de divisão
         * + = 3	- soma
         * - = 3	- subtração
         */
        if( ((String)operator).equals("!") ) {
            return 0;
        } else if( ((String)operator).equals("^") ) {
            return 1;
        } else if( ((String)operator).equals("*") ) {
            return 2;
        } else if( ((String)operator).equals("/") ) {
            return 2;
        } else if( ((String)operator).equals("%") ) {
            return 2;
        } else if( ((String)operator).equals("+") ) {
            return 3;
        } else if( ((String)operator).equals("-") ) {
            return 3;
        }
        return 4;
    }
    /**
     * Método que verifica um determinado caractere caso seja um operador
     * retorna <b>true</b>, se não retorna <b>false</b>
     * @param char c
     * @return boolean
     */
    private boolean isOperator( char c ) {
        switch(c) {
        	case '!':
        	    return true;
        	case '^':
        	    return true;
        	case '*':
        	    return true;
        	case '/':
        	    return true;
        	case '%':
        	    return true;
        	case '+':
        	    return true;
        	case '-':
        	    return true;
        }
        return false;
    }
    /**
     * Método que verifica se um caractere faz parte de um número
     * se faz, retorna <b>true</b>
     * 
     * @param char c
     * @return boolean
     */
    private boolean isNumPart( char c ) {
        switch(c) {
	    	case '0':
	    	    return true;
	    	case '1':
	    	    return true;
	    	case '2':
	    	    return true;
	    	case '3':
	    	    return true;
	    	case '4':
	    	    return true;
	    	case '5':
	    	    return true;
	    	case '6':
	    	    return true;
	    	case '7':
	    	    return true;
	    	case '8':
	    	    return true;
	    	case '9':
	    	    return true;
	    	case '.':
	    	    return true;
        }
        return false;
    }
    /*------------------------------------------------------------------------*
     *  Fim Serviços da classe
     *------------------------------------------------------------------------*/
    

}
