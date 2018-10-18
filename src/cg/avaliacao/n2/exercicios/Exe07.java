package cg.avaliacao.n2.exercicios;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * @author Vinicius Ferneda de Lima
 */
public class Exe07 implements GLEventListener, KeyListener {

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private double raioCircunferneciaMaior = 15;
	private double raioCircunferenciaMaiorX2 = raioCircunferneciaMaior * raioCircunferneciaMaior;
	private double raioCircunferenciaMenor = 3;
	private float x = 0;
	private float y = 0;

	private float quadradoX1, quadradoY1, quadradoX2, quadradoY2, quadradoX3, quadradoY3, quadradoX4, quadradoY4;

	// "render" feito logo após a inicialização do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		// configurar window
		glu.gluOrtho2D(-20.0f, 20.0f, -20.0f, 20.0f);

		desenhaCircunferenciaMaior();
		desenhaQuadradoCentral();
		desenhaCircunferenciaMenor();

		gl.glFlush();
	}

	/**
	 * Realiza o desenho da circunferência de maior tamanho (externa)
	 */
	private void desenhaCircunferenciaMaior() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(2);
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i < 361; i++) {
			float x = retornaX(i, raioCircunferneciaMaior);
			float y = retornaY(i, raioCircunferneciaMaior);
			gl.glVertex2f(x, y);
		}
		gl.glEnd();
	}

	/**
	 * Desenha a circunferência de menor tamanho (interna)
	 */
	private void desenhaCircunferenciaMenor() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(2);
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i < 361; i++) {
			float x = retornaX(i, raioCircunferenciaMenor) + this.x;
			float y = retornaY(i, raioCircunferenciaMenor) + this.y;
			gl.glVertex2f(x, y);
		}
		gl.glEnd();
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(this.x, this.y);
		gl.glEnd();
	}

	/**
	 * Desenha o quadrado central
	 */
	private void desenhaQuadradoCentral() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(2);
		gl.glBegin(GL.GL_LINE_LOOP);

		quadradoX1 = retornaX(135, raioCircunferneciaMaior);
		quadradoY1 = retornaY(135, raioCircunferneciaMaior);
		quadradoX2 = retornaX(45, raioCircunferneciaMaior);
		quadradoY2 = retornaY(45, raioCircunferneciaMaior);
		quadradoX3 = retornaX(315, raioCircunferneciaMaior);
		quadradoY3 = retornaY(315, raioCircunferneciaMaior);
		quadradoX4 = retornaX(225, raioCircunferneciaMaior);
		quadradoY4 = retornaY(225, raioCircunferneciaMaior);

		gl.glVertex2f(quadradoX1, quadradoY1);
		gl.glVertex2f(quadradoX2, quadradoY2);
		gl.glVertex2f(quadradoX3, quadradoY3);
		gl.glVertex2f(quadradoX4, quadradoY4);

		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (isLimiteCircunferneciaMaior(x, y + 1)) {
				this.y++;
			}
			glDrawable.display();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (isLimiteCircunferneciaMaior(x, y - 1)) {
				this.y--;
			}
			glDrawable.display();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (isLimiteCircunferneciaMaior(x - 1, y)) {
				this.x--;
			}
			glDrawable.display();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (isLimiteCircunferneciaMaior(x + 1, y)) {
				this.x++;
			}
			glDrawable.display();
		}
	}

	/**
	 * Determina o limite da circunferência maior (externa)
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isLimiteCircunferneciaMaior(float x, float y) {
		double distancia = (x * x) + (y * y);
		if (distancia <= raioCircunferenciaMaiorX2) {
			return true;
		}
		return false;
	}

	public float retornaX(double angulo, double raio) {
		return (float) (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public float retornaY(double angulo, double raio) {
		return (float) (raio * Math.sin(Math.PI * angulo / 180.0));
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

}