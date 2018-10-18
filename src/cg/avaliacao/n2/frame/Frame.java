package cg.avaliacao.n2.frame;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cg.avaliacao.n2.exercicios.Exe07;

public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//private Exe00 renderer = new Exe00();
	//private Exe01 renderer = new Exe01();
	//private Exe02 renderer = new Exe02();
	//private Exe03 renderer = new Exe03();
	//private Exe04 renderer = new Exe04();
	//private Exe05 renderer = new Exe05();
	//private Exe06 renderer = new Exe06();
	private Exe07 renderer = new Exe07();
	
	public Frame() {		
		// Cria o frame.
		super("01_Modelo_Eclipse_Jogl");   
		setBounds(50,100,500,500); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/* Cria um objeto GLCapabilities para especificar 
		 * o número de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);        
		canvas.addKeyListener(renderer);
		canvas.requestFocus();			
	}		
	
	public static void main(String[] args) {
		new Frame().setVisible(true);
	}

	
}
