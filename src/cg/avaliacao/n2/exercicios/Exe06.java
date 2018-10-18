package cg.avaliacao.n2.exercicios;

import java.awt.Point;
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
public class Exe06 implements GLEventListener, KeyListener {

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	protected int pontoSel = 3;
    protected int qtdPontos = 20;
    protected Point[] pontos = {new Point(10, -10), new Point(10, 10), new Point(-10, 10), new Point(-10, -10)};

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

        glu.gluOrtho2D(-20, 20, -20, 20);

        //pinta o fundo de cinza
        gl.glColor3f(0.94f, 0.94f, 0.94f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex2f(-500, 500);
		gl.glVertex2f(500, 500);
		gl.glVertex2f(500, -500);
		gl.glVertex2f(-500, -500);
		gl.glEnd();
		
        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2f(0.0f, 0.0f);
        gl.glVertex2f(0.0f, 10.0f);

        gl.glColor3f(1, 0, 0);
        gl.glVertex2f(0.0f, 0.0f);
        gl.glVertex2f(10.0f, 0.0f);
        gl.glEnd();

        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glColor3f(0.0f, 1.0f, 1.0f);
        for (int i = 0; i < this.pontos.length; i++)
        {
            gl.glVertex2f(this.pontos[i].x, this.pontos[i].y);
        }
        gl.glEnd();

        desenhaPontos(pontoSel);

        gl.glColor3f(0.0f, 0.0f, 0.40f);
        gl.glBegin(GL.GL_LINE_STRIP);

        float posPonto = (1f / qtdPontos);
        double x = 0, y = 0, posFinal = 0;

        for (int i = 0; i <= qtdPontos; i++) {
            x = realizaCalculoBezier(pontos[0].x, pontos[1].x, pontos[2].x, pontos[3].x, posFinal);
            y = realizaCalculoBezier(pontos[0].y, pontos[1].y, pontos[2].y, pontos[3].y, posFinal);

            gl.glVertex2d(x, y);
            posFinal = posFinal + posPonto;
        }
        gl.glEnd();
        gl.glFlush();
    }

    /**
     * Desenha os pontos que serão modificados
     * 
     * @param pontoSelecionado
     */
    private void desenhaPontos(int pontoSelecionado) {
        gl.glPointSize(11.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2f(pontos[pontoSelecionado].x, pontos[pontoSelecionado].y);
        gl.glEnd();
    }
    
    /**
     * Realiza o calculo de Bezier para a mudança dos pontos
     * 
     * @param ponto1
     * @param ponto2
     * @param ponto3
     * @param ponto4
     * @param posFinal
     * @return
     */
    public double realizaCalculoBezier(double ponto1, double ponto2, double ponto3, double ponto4, double posFinal) {
        return (Math.pow((1f - posFinal), 3f) * ponto1)
                + (3f * posFinal * (Math.pow((1f - posFinal), 2f)) * ponto2)
                + (3 * (Math.pow(posFinal, 2f)) * (1f - posFinal) * ponto3)
                + ((Math.pow(posFinal, 3f) * ponto4));
    }

    public void keyReleased(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {     
            case KeyEvent.VK_1: {
                this.pontoSel = 3;
                this.desenhaPontos(this.pontoSel);
                break;
            }
            case KeyEvent.VK_2: {
                this.pontoSel = 2;
                this.desenhaPontos(this.pontoSel);
                break;
            }
            case KeyEvent.VK_3: {
                this.pontoSel = 1;
                this.desenhaPontos(this.pontoSel);
                break;
            }
            case KeyEvent.VK_4: {
                this.pontoSel = 0;
                this.desenhaPontos(this.pontoSel);
                break;
            }
            case KeyEvent.VK_Q: {
                if (this.qtdPontos > 1) {
                    this.qtdPontos--;
                }
                break;
            }
            case KeyEvent.VK_W: {
                this.qtdPontos++;
                break;
            }
            case KeyEvent.VK_E: {
                this.pontos[this.pontoSel].x--;
                break;
            }
            case KeyEvent.VK_D: {
                this.pontos[this.pontoSel].x++;
                break;
            }
            case KeyEvent.VK_C: {
                this.pontos[this.pontoSel].y++;
                break;
            }
            case KeyEvent.VK_B: {
                this.pontos[this.pontoSel].y--;
                break;
            }
        }
        glDrawable.display();
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}
    
}
