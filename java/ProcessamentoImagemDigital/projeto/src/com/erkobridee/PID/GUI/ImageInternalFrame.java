/*
 * Created on 29/09/2005
 */
package com.erkobridee.PID.GUI;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import com.erkobridee.PID.GUI.util.Img;
import com.erkobridee.PID.util.ImageIOController;


/**
 * @author <a href="mailto:erko.bridee@gmail.com">Erko Bridee de Almeida Cabrera</a>
 * 
 * <br><br>
 * <b>Descri��o:</b><br>
 * Frame interno de exibi��o de image que est� sendo manipulada
 */
public class ImageInternalFrame extends JInternalFrame {
	
//  -----------------------------------------------------------
//   Atributos da classe
//  -----------------------------------------------------------
    /**
     * referencia do objeto
     */
    private File imageFile;
    /**
     * Imagem que est� carregada no Buffer
     */
    private BufferedImage bufferedImage;
    /**
     * Painel de exibi��o da imagem
     */
    private ImagePanel imagePanel;
    /**
     * �rea de barra de rolagem onde ser� colocado 
     * o painel da imagem
     */
    private JScrollPane scrollPane;
//  -----------------------------------------------------------
//   Fim dos atributos da classe	
//  -----------------------------------------------------------	

//  -----------------------------------------------------------
//   Construtores da classe
//  -----------------------------------------------------------
  	/**
  	 * Construtor sem parametros da classe
  	 */
    public ImageInternalFrame() { this.init(); }
//  -----------------------------------------------------------
//   Fim dos construtores da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de acesso aos atributos da classe
//  -----------------------------------------------------------
	/**
	 * @return File imageFile.
	 */
	public File getImageFile() {
	    return imageFile;
	}
	/**
	 * @param File imageFile
	 */
	public void setImageFile(File imageFile) {
	    this.imageFile = imageFile;
	}
	/**
	 * @return BufferedImage buffer.
	 */
	public BufferedImage getBufferedImage() {
	    return bufferedImage;
	}
	/**
	 * @param BufferedImage buffer
	 */
	public void setBufferedImage(BufferedImage buffer) {
	    this.bufferedImage = buffer;
	}
	//---- M�TODOS PRIVADOS ----
    /**
	 * @return ImagePanel imagePanel.
	 */
	private ImagePanel getImagePanel() {
	    return imagePanel;
	}
	/**
	 * @param ImagePanel imagePanel
	 */
	private void setImagePanel(ImagePanel imagePanel) {
	    this.imagePanel = imagePanel;
	}
    /**
     * @return JScrollPane scrollPane.
     */
    private JScrollPane getScrollPane() {
        return scrollPane;
    }
    /**
     * @param JScrollPane scrollPane
     */
    private void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
//  -----------------------------------------------------------
//   Fim dos m�todos de acesso ao atributos da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos de servi�os da classe
//  -----------------------------------------------------------	  
    /**
     * M�todo para inserir uma imagem no painel de exibi��o
     * 
     * @param BufferedImage bufferedImage - imagem a ser exibida, buffer
     */
    public void paintPanel( BufferedImage bufferedImage ) {
        this.getImagePanel().setPreferredSize( new java.awt.Dimension( bufferedImage.getWidth(null), bufferedImage.getHeight(null)));
        this.getImagePanel().draw( bufferedImage );
    }
    
    /**
     * M�todo que faz a leitura do disco da imagem original
     */
    public void carregaOriginal() {
        this.loadImage();
        this.paintPanel( this.getBufferedImage() );
    }
    
    /**
     * M�todo que recarrega a imagem que est� no buffer para a janela
     */
    public void reload() {
        this.paintPanel( this.getBufferedImage() );
    }
//  -----------------------------------------------------------
//   Fim dos m�todos de servi�os da classe
//  -----------------------------------------------------------	
  	
//  -----------------------------------------------------------
//   M�todos auxiliares da classe
//  -----------------------------------------------------------
  	/**
  	 * M�todo de inicializa��o da classe e seus atributos
  	 */
    private void init() {
        
        this.setImageFile( null );
        
        // inicializando os atributos da imagem
        this.setBufferedImage( null );
        
        // instanciando os objetos componentes
  	    this.setImagePanel( new ImagePanel() );
  	    this.setScrollPane( new JScrollPane() );
        
  	    /*
  	     * Defini��es do frame interno
  	     */
        this.getContentPane().setLayout(new java.awt.GridLayout());
        this.setClosable(true);
        this.setIconifiable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setAutoscrolls(true);
        this.setVisible(true);
        this.setSize( 600, 500 );

        // definindo os tratadores de eventos
        this.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                onClose();    
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        
        /*
         * defini��es da barra de rolagem
         */
        this.getScrollPane().setBackground(new java.awt.Color(153, 153, 255));
        this.getScrollPane().setBorder(new javax.swing.border.TitledBorder(null, null, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 204)));
        this.getScrollPane().setPreferredSize(new java.awt.Dimension(769, 60));
        this.getScrollPane().setViewportView( this.getImagePanel() );
        
        // adicionando a barra de rolagem na janela do frame interno
        getContentPane().add( this.getScrollPane(), new java.awt.GridLayout());
        this.getImagePanel().setBackground(new java.awt.Color(153, 153, 153));

        // setando icone da janela
        this.setFrameIcon( Img.getIcon("16x16/run.png") );
        
        this.pack();
  	}
    
    /**
     * M�todo que re-l� a imagem do arquivo em disco
     */
    private void loadImage() {
        // carrega o buffer com a imagem lida
        this.setBufferedImage( ImageIOController.read( this.getImageFile() ) );
    }
    
    /**
     * M�todo que trata do evento de fechamento da janela
     */
    private void onClose() {
        this.setBufferedImage( null );
        this.setImageFile( null );
  	    this.setImagePanel( null );
  	    this.setScrollPane( null );
  	    // chama o coletor de lixo
  	    System.gc();
    }
//  -----------------------------------------------------------
//   Fim dos m�todos auxiliares de classe
//  -----------------------------------------------------------	

}
