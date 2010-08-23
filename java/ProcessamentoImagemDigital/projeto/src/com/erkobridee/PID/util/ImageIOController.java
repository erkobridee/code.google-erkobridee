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
 * Classe que controla a leitura e grava��o de imagem em disco
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
//   M�todos de acesso aos atributos da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos m�todos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de servi�os da classe
//  -----------------------------------------------------------	
    /**
     * M�todo est�tico para a leitura de uma image
     * do disco para um buffer
     * 
     * @param String arquivo - localiza��o da imagem a ser lida
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
     * M�todo est�tico para a leitura de uma image
     * do disco para um buffer
     * 
     * @param File arquivo - localiza��o da imagem a ser lida
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
     * M�todo que faz a saida para disco da imagem de um buffer
     * 
     * @param BufferedImage image - imagem a ser salva
     * @param String arquivo - localiza��o + nome do arquivo
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
     * M�todo que faz a saida para disco da imagem de um buffer
     * 
     * @param BufferedImage image - imagem a ser salva
     * @param File arquivo - localiza��o + nome do arquivo
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
//   Fim dos m�todos de servi�os da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos auxiliares da classe
//  -----------------------------------------------------------
  	// TODO: IMPLEMENTAR
//  -----------------------------------------------------------
//   Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	

}
