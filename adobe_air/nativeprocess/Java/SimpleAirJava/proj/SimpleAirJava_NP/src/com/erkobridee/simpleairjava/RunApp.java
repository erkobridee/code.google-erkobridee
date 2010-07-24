package com.erkobridee.simpleairjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Erko Bridee
 * 
 * Classe para testar a comunicação com a aplicação Adobe AIR
 * via NativeProcess
 *
 */
public class RunApp {

	/**
	 * @param args String[]
	 */
	public static void main(String[] args) {
		
		System.out.println("\n");
		
		for(int i=0; i < args.length; i++) {
			System.out.println( "Java init arg["+i+"] : " + args[i] );
		}
		
		System.out.println("\nJava application running...\n");
		
		BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
		String text = "";

		while(true) {
			try {
				text = in.readLine();				
				System.out.println("Java program: "+ text);				
			} catch (IOException e) {
				System.err.println("Exception while reading the input. " + e);
			}
		}	
	}

}
