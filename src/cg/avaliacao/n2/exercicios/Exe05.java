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
public class Exe05 implements GLEventListener, KeyListener {

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private double minX = -20.0f;
	private double maxX = 20.0f;
	private double minY = -20.0f;
	private double maxY = 20.0f;
	private byte parametro = 100;

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

		switch (parametro) {
		case 0:
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			desenhaPonto(-10, 10);
			gl.glColor3f(1.0f, 0.0f, 1.0f);
			desenhaPonto(-10, -10);
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			desenhaPonto(10, -10);
			gl.glColor3f(1.0f, 0.0f, .0f);
			desenhaPonto(10, 10);
			break;
		case 1:
			desenhaLinhasVerticais();
			break;
		case 2:
			desenhaQuadrado(GL.GL_LINE_LOOP);
			break;
		case 3:
			desenhaQuadrado(GL.GL_LINE_STRIP);
			break;
		case 4:
			gl.glLineWidth(2.0f);
			gl.glBegin(GL.GL_TRIANGLES);
			// inf.dir
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex2f(10.0f, -10.0f);
			// sup.dir
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(10.0f, 10.0f);
			// sup.esq
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2f(-10.0f, 10.0f);
			gl.glEnd();
			break;
		case 5:
			gl.glLineWidth(2.0f);
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
			// inf.dir
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex2f(10.0f, -10.0f);
			// sup.dir
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(10.0f, 10.0f);
			// sup.esq
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2f(-10.0f, 10.0f);
			// inf.esq
			gl.glColor3f(1.0f, 0.0f, 1.0f);
			gl.glVertex2f(-10.0f, -10.0f);
			gl.glEnd();
			break;
		case 6:
			gl.glLineWidth(2.0f);
			gl.glBegin(GL.GL_POLYGON);
			// inf.dir
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex2f(10.0f, -10.0f);
			// sup.dir
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(10.0f, 10.0f);
			// sup.esq
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2f(-10.0f, 10.0f);
			// inf.esq
			gl.glColor3f(1.0f, 0.0f, 1.0f);
			gl.glVertex2f(-10.0f, -10.0f);
			gl.glEnd();
			break;
		case 7:
			gl.glLineWidth(2.0f);
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
			// sup.dir
			gl.glColor3f(0.0f, 1.0f, 0.0f);// verde
			gl.glVertex2f(10.0f, 10.0f);
			// inf.esq
			gl.glColor3f(1.0f, 0.0f, 1.0f);// rosa
			gl.glVertex2f(-10.0f, -10.0f);
			// inf.dir
			gl.glColor3f(1.0f, 0.0f, 0.0f);// vermelho
			gl.glVertex2f(10.0f, -10.0f);
			// sup.esq
			gl.glColor3f(0.0f, 0.0f, 1.0f);// azul
			gl.glVertex2f(-10.0f, 10.0f);
			gl.glEnd();
			break;
		}

		gl.glFlush();
	}

	private void desenhaQuadrado(int type) {
		gl.glLineWidth(2.0f);
		gl.glBegin(type);
		// inf.dir
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex2f(10.0f, -10.0f);
		// sup.dir
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glVertex2f(10.0f, 10.0f);
		// sup.esq
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex2f(-10.0f, 10.0f);
		// inf.esq
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex2f(-10.0f, -10.0f);
		gl.glEnd();
	}

	private void desenhaPonto(double x, double y) {
		gl.glPointSize(3.0f);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(x, y);
		gl.glEnd();
	}

	private void desenhaLinhasVerticais() {
		gl.glLineWidth(2.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-10.0f, 10.0f);
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex2f(-10.0f, -10.0f);
		gl.glEnd();

		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(10.0f, 10.0f);
		gl.glColor3f(1.0f, 0.0f, .0f);
		gl.glVertex2f(10.0f, -10.0f);
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ') {
			if (parametro > 7) {
				parametro = 0;
			} else {
				parametro++;
			}
		}
		glDrawable.display();
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
