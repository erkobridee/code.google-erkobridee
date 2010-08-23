/*
 * Created on 27/09/2005
 */
package com.erkobridee.PID.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Erko Bridee de Almeida Cabrera
 *
 * <br><br>
 * Classe que controla a leitura e gravação de imagem em disco
 */
public class ImageIOController {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	/**
  	 * Construtor da classe sem parametros
  	 */
    public ImageIOController() {}
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de acesso aos atributos da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos métodos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos de serviços da classe
//  -----------------------------------------------------------	
    /**
     * Método estático para a leitura de uma image
     * do disco para um buffer
     * 
     * @param String arquivo - localização da imagem a ser lida
     */
    public static BufferedImage read( String arquivo ) {
		try { 
			return( ImageIO.read( new File( arquivo ) ) ); 
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar imagem: " + arquivo );
		}
	}
    
    /**
     * Método estático para a leitura de uma image
     * do disco para um buffer
     * 
     * @param File arquivo - localização da imagem a ser lida
     */
    public static BufferedImage read( File arquivo ) {
		try { 
			return( ImageIO.read( arquivo ) ); 
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar imagem: " + arquivo );
		}
	}
    
    /**
     * Método que faz a saida para disco da imagem de um buffer
     * 
     * @param BufferedImage image - imagem a ser salva
     * @param String arquivo - localização + nome do arquivo
     */
    public static void save( BufferedImage image, String arquivo  ) {         
		try { 
			String sufixo = arquivo.substring(arquivo.lastIndexOf('.') + 1);
			ImageIO.write( image, sufixo, new File(arquivo));
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar imagem: " + arquivo );
		}
	}
    
    /**
     * Método que faz a saida para disco da imagem de um buffer
     * 
     * @param BufferedImage image - imagem a ser salva
     * @param File arquivo - localização + nome do arquivo
     */
    public static void save( BufferedImage image, File arquivo  ) {         
		try { 
			String sufixo = arquivo.getPath().substring( arquivo.getPath().lastIndexOf('.') + 1 );
			ImageIO.write( image, sufixo, arquivo );
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar imagem: " + arquivo );
		}
	}
    
//  -----------------------------------------------------------
//   Fim dos métodos de serviços da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   Métodos auxiliares da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos métodos auxiliares de classe
//  -----------------------------------------------------------	

}
