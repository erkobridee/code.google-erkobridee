/*
 * Created on 09/04/2005
 */
package com.erkobridee.calculadora.lexica.testes;

import com.erkobridee.calculadora.lexica.Analise;
import com.erkobridee.calculadora.lexica.util.InfixToPostfix;
import com.erkobridee.calculadora.lexica.util.PostfixEvaluator;


/**
 * Classe para testar a convers�o de nota��o infixa para p�s-fixa
 * 
 * @author <a href="mailto:bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 */
public class InfixToPostfixTest {

    // classe a ser testada
    private InfixToPostfix infixToPostfix; 
    private PostfixEvaluator postfixEvaluator;
    
    private Analise analise;
    
    public InfixToPostfixTest() {
        
        //infixToPostfix = new InfixToPostfix();
                
        //String notacao = "(1+6.2)*4+(7/6-9)+2+4";
        String notacao = " (-1 + 6.2 * 4 + ( 7 / 6 - 9 ) + 2^8 + 4!)-200";
        //String notacao = "4!";
        /*
        infixToPostfix.convertToPostfix( notacao );
        System.out.println("\nResultado, nota��o p�s-fixa:\n");
        System.out.println( infixToPostfix.getPostfix() );
        
        postfixEvaluator = new PostfixEvaluator( infixToPostfix );
        System.out.println( postfixEvaluator.getResultado()[0].toString() );
        */
        analise = new Analise( notacao );
        System.out.println("Express�o:");
        System.out.println( notacao );
        System.out.println("Resultado:");
        System.out.println( analise.getResultado() );
    }
    
    public static void main(String[] args) {
        new InfixToPostfixTest();
        System.exit(0);
    }
}
