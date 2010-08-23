/*
 * Created on 09/04/2005
 */
package com.erkobridee.calculadora.lexica.util;

import java.util.LinkedList;

/**
 * Implementação de uma classe que fornece os serviços de pilha
 * 
 * @author <a href="mailto:bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class Pilha {
    
    /*------------------------------------------------------------------------*
     *  Declaração das variáveis da classe
     *------------------------------------------------------------------------*/
    /** Objeto para representar a pilha */
    private LinkedList pilha = new LinkedList();
    /*------------------------------------------------------------------------*
     *  Fim Declaração das variáveis da classe
     *------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*
     *  Construtores da classe
     *------------------------------------------------------------------------*/
    /** Construtor sem parametros da classe */
    public Pilha() { this.setPilha( new LinkedList() ); }
    /*------------------------------------------------------------------------*
     *  Fim Construtores da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    /**
     * @return LinkedList pilha.
     */
    public LinkedList getPilha() {
        return pilha;
    }
    /**
     * @param LinkedList pilha.
     */
    public void setPilha(LinkedList pilha) {
        this.pilha = pilha;
    }
    /*------------------------------------------------------------------------*
     *  Fim Métodos de acesso a variáveis da classe
     *------------------------------------------------------------------------*/
    
    /*------------------------------------------------------------------------*
     *  Serviços da classe
     *------------------------------------------------------------------------*/
    /**
     * Método para empilhar
     * 
     * @param Object object
     */
    public void push( Object object ) {
        this.getPilha().addLast( object );
    }
    /**
     * Método para desempilhar
     * 
     * @return Object
     */
    public Object pop() {
        return this.getPilha().removeLast();
    }
    /**
     * Método que retorna o objeto do topo da pilha
     * 
     * @return Object
     */
    public Object stackTop() {
        return this.getPilha().getLast();
    }
    /**
     * Indica se a pilha está ou não vazia
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return this.getPilha().isEmpty();
    }
    /**
     * Método que retorna a quantidade atual de elementos na pilha
     * 
     * @return int
     */
    public int size() {
        return this.getPilha().size();
    }
    /*------------------------------------------------------------------------*
     *  Fim Serviços da classe
     *------------------------------------------------------------------------*/
}
