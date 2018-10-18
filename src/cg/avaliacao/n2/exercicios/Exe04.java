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
public class Exe04 implements GLEventListener, KeyListener {

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private double minX = -21.0f;
	private double maxX = 21.0f;
	private double minY = -21.0f;
	private double maxY = 21.0f;

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
		glu.gluOrtho2D(minX, maxX, minY, maxY);

		// configurar cor de desenho (valores r, g, b)
		gl.glColor3f(1.0f, 0.0f, 0.0f);

		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, 0.0f);
		gl.glVertex2f(10.0f, 0.0f);
		gl.glEnd();

		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, 0.0f);
		gl.glVertex2f(0.0f, 10.0f);
		gl.glEnd();

		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(2f);
		gl.glBegin(GL.GL_POINTS);
		double inc = 0.01;
		double aux = 0.000002;
		double k = 0;
		for (int v = 0; v < 10; v++) {
			for (int i = 0; i < (v == 9 ? 180 : 360); i++) {
				gl.glVertex2d(RetornaX(i, inc), RetornaY(i, inc));
				inc += (k * aux);
				k += 1.2;
				aux += 0.000000000009;
			}
		}
		gl.glEnd();

		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, 0.0f);
		gl.glVertex2f(-(float) inc, 0.0f);
		gl.glEnd();

		gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
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

	public double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}

}
