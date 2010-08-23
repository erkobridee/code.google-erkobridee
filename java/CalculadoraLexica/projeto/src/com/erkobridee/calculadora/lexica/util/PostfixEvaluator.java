/*
 * Created on 10/04/2005
 */
package com.erkobridee.calculadora.lexica.util;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Classe que avalia e executa o calculo de uma notação pós-fixa
 * 
 * @author <a href="mailto:bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class PostfixEvaluator {
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
    /** monta a estrutura de árvore de análise */
    private DefaultMutableTreeNode rootNode;
    /** nó corrente sendo processado */
    private DefaultMutableTreeNode currentNode;
    /** resultado do calculo da notação */
    private Object[] resultado;
    /*------------------------------------------------------------------------*
     *  Fim Declaração das variáveis da classe
     *------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*
     *  Construtores da classe
     *------------------------------------------------------------------------*/
    /**
     * Construtor sem parametros da classe
     */
    public PostfixEvaluator() {
        this.setNotacaoPosFixa( new LinkedList() );
        this.setPilha( new Pilha() );
        this.setInfix("");
        this.setPostfix("");
    }
    /**
     * Construtor da classe que recebe a estrutura da notação pós-fixa
     * @param LinkedList notacaoPosFixa
     */
    public PostfixEvaluator( LinkedList notacaoPosFixa ) {
        this.setNotacaoPosFixa( notacaoPosFixa );
        this.setPilha( new Pilha() );
        this.setInfix("");
        this.setPostfix("");
    }
    /**
     * Construtor da classe que recebe a notação pós-fixa a ser processada
     * @param String notacaoPosFixa
     */
    public PostfixEvaluator( String notacaoPosFixa ) {
        this.setNotacaoPosFixa( new LinkedList() );
        this.setPilha( new Pilha() );
        this.setInfix("");
        this.setPostfix( notacaoPosFixa );
    }
    /**
     * Construtor da classe que recebe um objeto da classe
     * onde se encontram a estrutura da notação pós-fixa
     * e a notação infixa
     * @param InfixToPostfix infixToPostfix
     */
    public PostfixEvaluator( InfixToPostfix infixToPostfix ) {
        this.setNotacaoPosFixa( infixToPostfix.getNotacaoPosFixa() );
        this.setPilha( new Pilha() );
        this.setInfix( infixToPostfix.getInfix() );
        this.montaAnalise();
        this.processaNotacao();
    }
    /*------------------------------------------------------------------------*
     *  Fim Construtores da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * @return LinkedList pilha.
     */
    public Pilha getPilha() {
        return pilha;
    }
    /**
     * @return String resultado.
     */
    public Object[] getResultado() {
        return resultado;
    }
    /**
     * @param String resultado.
     */
    public void setResultado(Object[] resultado) {
        this.resultado = resultado;
    }
    /**
     * @param LinkedList pilha.
     */
    public void setPilha(Pilha pilha) {
        this.pilha = pilha;
    }
    /**
     * @return DefaultMutableTreeNode currentNode.
     */
    public DefaultMutableTreeNode getCurrentNode() {
        return currentNode;
    }
    /**
     * @param DefaultMutableTreeNode currentNode.
     */
    public void setCurrentNode(DefaultMutableTreeNode currentNode) {
        this.currentNode = currentNode;
    }
    /**
     * @return DefaultMutableTreeNode rootNode.
     */
    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
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
    /**
     * @param DefaultMutableTreeNode rootNode.
     */
    public void setRootNode(DefaultMutableTreeNode rootNode) {
        this.rootNode = rootNode;
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
        if( notacaoPosFixa.size() > 0 ) {
            this.notacaoPosFixa = this.cloneLinkedList( notacaoPosFixa );            
        } else {
            this.notacaoPosFixa = notacaoPosFixa;
        }
    }
    /**
     * @return String postfix.
     */
    public String getPostfix() {
        if( ( this.postfix == null ) && ( this.getNotacaoPosFixa() != null ) ) {
            String tmp = "";
            for( int i = 0; i < this.getNotacaoPosFixa().size(); i++ ) {
                tmp += (String)this.getNotacaoPosFixa().get(i) + " ";
            }
            this.postfix = tmp;
        }
        return this.postfix;
    }
    /**
     * @param String postfix.
     */
    public void setPostfix(String postfix) {
        this.postfix = postfix;
        if( this.postfix.length() > 0 ) {
            this.geraEstruturaNotacao();
        }
    }
    /*------------------------------------------------------------------------*
     *  Fim Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Serviços da classe
     *------------------------------------------------------------------------*/
    /**
     * Método para clonar uma estrutura de lista
     */
    private LinkedList cloneLinkedList( LinkedList lk ) {
        LinkedList clone = new LinkedList();        
        for( int i = 0; i < lk.size(); i++ ) {                
            clone.addLast( lk.get(i).toString() );
        }
        return clone;
    }
    /**
     * Método que inverte a ordem de uma lista
     * @param LinkedList lk - lista a inverter
     * @return LinkedList - ordem invertida
     */
    private LinkedList reverseLinkedList( LinkedList lk ) {
    	LinkedList reverse = new LinkedList();
    	while( lk.size() > 0 ) {                
    		reverse.addLast( lk.removeLast() );
        }
        return reverse;
    }
    /**
     * Converte a string da notação pós-fixa para o formato de estrutura
     * que é analisada pela classe, da qual é calculado o seu resultado
     */
    private void geraEstruturaNotacao() {
        /*
         * TODO:
         * Código para a partir de uma string em notação pós-fixa
         * gerar a sua estrutura em forma de lista para depois 
         * ser calculada
         */
        
        // serviços chamados ao final do método
        this.montaAnalise();
        this.processaNotacao();
    }
    /**
     * Método que realiza o calculo da notação pós-fixa
     */
    private void processaNotacao() {
        LinkedList resultado = this.cloneLinkedList( this.getNotacaoPosFixa() );
        /*
         * faz enquanto a quantidade de elementos dentro s da lista for maior 
         * que um, quando for 1 é o que indica que o resultado foi achado
         */
        while( resultado.size() >= 1 ) {            
            if( ( resultado.size() == 1 ) && ( !this.isOperator( resultado.getFirst() ) ) ) { break; }
            // verifica se é um operador
            if( this.isOperator( resultado.getFirst() ) ) {
                Object o1, o2;
                int opType = this.typeOperator( resultado.getFirst() );
                if( ( this.getPilha().size() >= 2 ) && ( opType != 1 ) ) {
                    o2 = this.getPilha().pop();
                    o1 = this.getPilha().pop();
                } else {
                    o2 = this.getPilha().pop();
                    o1 = "0";
                }
                resultado.addFirst( this.calcula(o1,resultado.removeFirst(),o2) );
                while( this.getPilha().size() > 0 ) {
                    resultado.addFirst( this.getPilha().pop() );
                }
            } else {
                this.getPilha().push( resultado.removeFirst() );
            }
        }
        this.setResultado( resultado.toArray() );
    }
    /**
     * Método que analisa a notação e gera uma representação no formato
     * de uma árvore
     */
    private void montaAnalise() {
        this.setRootNode( new DefaultMutableTreeNode("<expressão>") );
        this.addNode( this.getRootNode(), "infixa = "+ this.getInfix() );
        this.addNode( this.getRootNode(), "pós-fixa = "+ this.getPostfix() );
        this.setCurrentNode( this.getRootNode() );

        LinkedList estruturaNotacao = this.cloneLinkedList( this.getNotacaoPosFixa() );
        /*
         * faz enquanto a quantidade de elementos dentro s da lista for maior 
         * que um, quando for 1 é o que indica que o resultado foi achado
         */
        while( estruturaNotacao.size() >= 1 ) {            
            if( ( estruturaNotacao.size() == 1 ) && ( estruturaNotacao.getFirst() instanceof DefaultMutableTreeNode ) ) { 
            	this.getRootNode().add( (DefaultMutableTreeNode )estruturaNotacao.removeFirst() );
            	break; 
            }
            // verifica se é um operador
            if( estruturaNotacao.getFirst() instanceof DefaultMutableTreeNode ) {
            	this.getPilha().push( estruturaNotacao.removeFirst() );
            } else if( this.isOperator( estruturaNotacao.getFirst() ) ) {
                Object o1, o2;
                int opType = this.typeOperator( estruturaNotacao.getFirst() );
                if( ( this.getPilha().size() >= 2 ) && ( opType != 1 ) ) {
                    o2 = this.getPilha().pop();
                    o1 = this.getPilha().pop();
                } else {
                    o2 = this.getPilha().pop();
                    o1 = null;
                }
                
                Object o = estruturaNotacao.removeFirst();
                DefaultMutableTreeNode operador = new DefaultMutableTreeNode("<operador>");                
                operador.add( new DefaultMutableTreeNode( "símbolo = " + o ) );                
                operador.add( new DefaultMutableTreeNode( "tipo = " + this.nameOperator( o ) ) );
                if( o1 != null ) { operador.add( ( ( DefaultMutableTreeNode )o1 ) ); }
                operador.add( ( ( DefaultMutableTreeNode )o2 ) );
                
                estruturaNotacao.addFirst( operador );
                
                while( this.getPilha().size() > 0 ) {
                    estruturaNotacao.addFirst( this.getPilha().pop() );
                }
            } else {
            	DefaultMutableTreeNode numero = new DefaultMutableTreeNode("<número>");
	            numero.add( new DefaultMutableTreeNode( "valor = " + estruturaNotacao.removeFirst() ) );
	            this.getPilha().push( numero );
            }
        }        
    } 
    /**
     * Método para adicionar um novo elemento na árvore
     * 
     * @param DefaultMutableTreeNode pai
     * @param Objec obj
     * @return DefaultMutableTreeNode - elemento adicionado
     */
    private DefaultMutableTreeNode addNode( DefaultMutableTreeNode pai, Object obj ) {
        DefaultMutableTreeNode filho = new DefaultMutableTreeNode(obj); 
        pai.add( filho );
        return filho;
    }
    /**
     * Método que verifica se o elemento verificado é um operador
     * retorna <b>true</b>, se for um operador
     * 
     * @param Object obj
     * @return boolean
     */
    private boolean isOperator( Object operator ) {
        if( ((String)operator).equals("!") ) {
            return true;
        } else if( ((String)operator).equals("^") ) {
            return true;
        } else if( ((String)operator).equals("*") ) {
            return true;
        } else if( ((String)operator).equals("/") ) {
            return true;
        } else if( ((String)operator).equals("%") ) {
            return true;
        } else if( ((String)operator).equals("+") ) {
            return true;
        } else if( ((String)operator).equals("-") ) {
            return true;
        }
        return false;
    }
    /**
     * Método que verifica se o elemento verificado é um operador
     * retorna o número da operação
     * 
     * @param Object obj
     * @return int
     */
    private int typeOperator( Object operator ) {
        if( ((String)operator).equals("!") ) {
            return 1;
        } else if( ((String)operator).equals("^") ) {
            return 2;
        } else if( ((String)operator).equals("*") ) {
            return 3;
        } else if( ((String)operator).equals("/") ) {
            return 4;
        } else if( ((String)operator).equals("%") ) {
            return 5;
        } else if( ((String)operator).equals("+") ) {
            return 6;
        } else if( ((String)operator).equals("-") ) {
            return 7;
        }
        return 8;
    }
    /**
     * Método que retorna o nome da operação
     * 
     * @param Object obj
     * @return String
     */
    private String nameOperator( Object operator ) {
        String out = "";
        if( ((String)operator).equals("!") ) {
            out = "fatorial";
        } else if( ((String)operator).equals("^") ) {
            out = "potência";
        } else if( ((String)operator).equals("*") ) {
            out = "multiplicação";
        } else if( ((String)operator).equals("/") ) {
            out = "divisão";
        } else if( ((String)operator).equals("%") ) {
            out = "resto divisão";
        } else if( ((String)operator).equals("+") ) {
            out = "soma";
        } else if( ((String)operator).equals("-") ) {
            out = "subtração";
        }
        return out;
    }
    /*------------------------------------------------------------------------*
     *  Fim Serviços da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Métodos de calculo da classe
     *------------------------------------------------------------------------*/
    /**
     * Metodo que realiza o calculo da seguinte expressão repassada
     * >>> 
     * <code> < número >< operador >< número ></code>
     */
    private String calcula( Object o1, Object operador, Object o2 ) {
        String resultado = "";
        switch( this.typeOperator( operador ) ) {
        	case 1: // fatorial
        	    resultado = this.fatorial( o2 );
        	    break;
        	case 2: // potência
        	    resultado = this.potencia( o1, o2 );
        	    break;
        	case 3: // multiplicação
        	    resultado = this.multiplicacao( o1, o2 );
        	    break;
        	case 4: // divisão
        	    resultado = this.divisao( o1, o2 );
        	    break;
        	case 5: // resto da divisão
        	    resultado = this.mod( o1, o2 );
        	    break;
        	case 6: // soma
        	    resultado = this.soma( o1, o2 );
        	    break;
        	case 7: // subtração
        	    resultado = this.subtracao( o1, o2 );
        	    break;
        }
        return resultado;
    }
    /**
     * Método que calcula o fatorial
     * 
     * @param Object o - número a ser calculado o fatorial
     * @return String - resultado do fatorial
     */
    private String fatorial( Object o ) {
        double num = Double.parseDouble( o.toString() );
        double tmp = 1;
        while( num > 1  ) {
            tmp *= num--;
        }
        return Double.toString( tmp );
    }
    /**
     * Método que calcula a potencia de um número pelo seu expoente
     * @param Object num
     * @param Object exp
     * @return String - resultado convertido
     */
    private String potencia( Object num, Object exp ) {
        return Double.toString( Math.pow( Double.parseDouble(num.toString()), Double.parseDouble(exp.toString()) ) );
    }
    /**
     * Método para calcular a multiplicação
     * @param Object f1 - número
     * @param Object f2 - número
     * @return String - resultado da multiplicação
     */
    private String multiplicacao( Object f1, Object f2 ) {
        return Double.toString( Double.parseDouble( f1.toString() ) * Double.parseDouble( f2.toString() ) );
    }
    /**
     * Método para calcular a divisão
     * @param Object f1 - número
     * @param Object f2 - número
     * @return String - resultado da divisão
     */
    private String divisao( Object f1, Object f2 ) {
        return Double.toString( Double.parseDouble( f1.toString() ) / Double.parseDouble( f2.toString() ) );
    }
    /**
     * Método para calcular resto da divisão
     * @param Object f1 - número
     * @param Object f2 - número
     * @return String - resultado resto da divisão
     */
    private String mod( Object f1, Object f2 ) {
        return Double.toString( Double.parseDouble( f1.toString() ) % Double.parseDouble( f2.toString() ) );
    }
    /**
     * Método para calcular soma
     * @param Object f1 - número
     * @param Object f2 - número
     * @return String - resultado soma
     */
    private String soma( Object f1, Object f2 ) {
        return Double.toString( Double.parseDouble( f1.toString() ) + Double.parseDouble( f2.toString() ) );
    }
    /**
     * Método para calcular subtração
     * @param Object f1 - número
     * @param Object f2 - número
     * @return String - resultado subtração
     */
    private String subtracao( Object f1, Object f2 ) {
        return Double.toString( Double.parseDouble( f1.toString() ) - Double.parseDouble( f2.toString() ) );
    }
    /*------------------------------------------------------------------------*
     *  Fim Métodos de calculo da classe
     *------------------------------------------------------------------------*/
}
