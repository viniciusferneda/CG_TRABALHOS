package cg.avaliacao.n3.view;
/// \file Frame.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cg.avaliacao.n3.model.World;

public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public Frame() {		
		// Cria o frame.
		super("Trabalho N3");   
		setBounds(50,100,500,522);  // 500 + 22 da borda do t�tulo da janela
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		World renderer = new World(getWidth(), getHeight());
		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);        
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseWheelListener(renderer);
		canvas.addMouseMotionListener(renderer);
		canvas.requestFocus();
		
		setLocationRelativeTo(null);
	}		
	
	public static void main(String[] args) {
		new Frame().setVisible(true);
	}

	
}