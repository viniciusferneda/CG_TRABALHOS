package cg.avaliacao.n4.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;

import com.sun.opengl.util.GLUT;

import cg.avaliacao.n4.model.obj.CleanObject;
import cg.avaliacao.n4.model.obj.ObjetoGrafico;
import cg.avaliacao.n4.model.obj.Vertex;

/**
 * Representa um mundo 3D.
 * 
 * Medidas do ortho: 0 | 500 | 500 | 0
 * @author Vinícius
 */
public class Scenario implements GLEventListener, KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	private double xEye, yEye, zEye;
	private double xCenter, yCenter, zCenter;
	private final double xUp = 0.0f, yUp = 1.0f, zUp = 0.0f;

	private List<ObjetoGrafico> objects = new ArrayList<ObjetoGrafico>();
	
	private ObjetoGrafico selected = CleanObject.get();
	private Vertex selectedVertex = ClearVertex.get();
	
	private ScenarioState state = ScenarioState.IDLE;
	
	private float corRed[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	
	/**
	 * Construtor da classe Scenario
	 */
	public Scenario(){
	}

	/**
	 * Inicialza o as propriedades da tela
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		xEye = 20.0f;
		yEye = 20.0f;
		zEye = 20.0f;
		xCenter = 0.0f;
		yCenter = 0.0f;
		zCenter = 0.0f;
		
	    float posLight[] = { 5.0f, 5.0f, 10.0f, 0.0f };
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
	    gl.glEnable(GL.GL_LIGHT0);

	    gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_DEPTH_TEST);
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	/**
	 * Responsável pela atualização da tela
	 */
	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	    setCamera();

        gl.glLoadIdentity();
		drawAxis();
		drawCube(2.0f,2.0f,2.0f);
		gl.glPushMatrix();
		gl.glTranslated(0.0f, 0.0f, 1.5f);
			drawCube(1.0f,1.0f,1.0f);
		gl.glPopMatrix();
		
		gl.glFlush();
	}
	
	private void setCamera() {
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 0.1, 100);
		glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, xUp, yUp, zUp);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }
	
	public void drawAxis() {
		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();
		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();
		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}
	
	private void drawCube(float xS, float yS, float zS) {
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corRed, 0);
	    gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
			gl.glScalef(xS,yS,zS);
			glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
		
		gl.glDisable(GL.GL_LIGHTING);
	}
	
	@Override
	public void keyTyped(KeyEvent e) { 
	}
		
	/**
	 * Evento acionado ao pressionar uma tecla
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		tryShortCurt(keyCode);
		if (tryMove(keyCode)){
			glDrawable.display();
		}
	}

	/**
	 * Exibe os atalhos em um Help para o usuário
	 * 
	 * @param keyCode tecla pressionada 
	 */
	private void tryShortCurt(int keyCode) {
		if (keyCode == KeyEvent.VK_F1){
			JOptionPane.showMessageDialog(null, "\nR: Pinta o polígono de vermelho." +
												"\nG: Pinta o polígono de verde." +
												"\nB: Pinta o polígono de azul." +
												"\nTeclas direcionais: move o polígono para a direita, esquerda, acima, abaixo." +
												"\nA: Aumenta o polígono." +
												"\nCtrl+A: Aumenta o polígono em relação ao centro." +
												"\nD: Diminui o polígono." +
												"\nCtrl+D: Diminui o polígono em relação ao centro." +
												"\nHome: Gira o polígono no sentido anti-horário." +
												"\nCtrl+Home: Gira o polígono no sentido anti-horário em relação ao centro." +
												"\nEnd: Gira o polígono no sentido horário." +
												"\nCtrl+End: Gira o polígono no sentido horário em relação ao centro." +
												"\nEnter: Exibe as informações do objeto selecionado." + 
												"\n*Delete: Realiza a exclusão do polígono." +
												"\n*Mouse: Mudar posição do vértice." + 
												"\n\n*Deve ter um vertice selecionado." +
												"\nPoligonos filhos são criados através dos poligonos selecionados.", 
												"Help", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Move o poligono nos sentidos acima, abaixo, direita e esquerda
	 * Utiliza as setas direcionais para mover o objeto 
	 * @param keyCode tecla pressionada
	 * @return boolean
	 */
	private boolean tryMove(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_RIGHT:
			selected.matrizTranslacaoObjGrafico(new Vertex(2, 0));
			break;
		case KeyEvent.VK_LEFT:
			selected.matrizTranslacaoObjGrafico(new Vertex(-2, 0));
			break;
		case KeyEvent.VK_UP:
			selected.matrizTranslacaoObjGrafico(new Vertex(0, -2));
			break;
		case KeyEvent.VK_DOWN:
			selected.matrizTranslacaoObjGrafico(new Vertex(0, 2));
			break;
		default:
			return false;
		}
		return true;
		
	}

	/**
	 * Evento ao realizar um click no mouse
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON2) {
			return;
		}
		
		// Se não está criado e clicou com o button1, 
		// pode estar tentando fazer uma seleção de vértice ou polígono.
		if (e.getButton() == MouseEvent.BUTTON1 && state != ScenarioState.CREATING) {
			boolean change = false;
			
			//Se tem um polígono selecionado, verifica se clicou em um vertice.
			if (!selected.equals(CleanObject.get())){
				Vertex vertex = selected.getSelectedVertex(e.getX(), e.getY());
				if (!vertex.equals(ClearVertex.get())){
					selectedVertex = vertex;
					change = true;
				}
			}
			
			if (!change){
				ObjetoGrafico nowSelected = trySelect(e.getX(), e.getY());
				
				if (!nowSelected.equals(selected)){
					selected.setSelected(false);
					
					selected = nowSelected;
					selected.setSelected(true);
					selectedVertex = ClearVertex.get();
					
					change = true;
				}
			}
			glDrawable.display();	 
			return;
		}
		
		if (state != ScenarioState.CREATING){
			ObjetoGrafico obj = new ObjetoGrafico(selected);
			
			if (!selected.equals(CleanObject.get())){
				selected.addChild(obj);
				selected.setSelected(false);
				obj.setSelected(true);
			}else{
				objects.add(obj);
			}
			
			state = ScenarioState.CREATING;
			selected = obj;
			selectedVertex = ClearVertex.get();
		}
		
		//cria o novo ponto
		Vertex vertex = new Vertex(e.getX(), e.getY());
		selected.addPonto(vertex);
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			state = ScenarioState.IDLE;
			selected.setMovingVertex(ClearVertex.get());
			selected.setPrimitiva(GL.GL_LINE_LOOP);
		}
		glDrawable.display();	 
	}
	
	/**
	 * Realiza a seleção do vértice do polígono
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * 
	 * @return Polígono
	 */
	private ObjetoGrafico trySelect(int x, int y) {
		Vertex v = new Vertex(x, y);
		for(ObjetoGrafico o : objects){
			ObjetoGrafico selected = o.verifySelected(v);
			if (!selected.equals(CleanObject.get())){
				return selected;
			}
		}
		return CleanObject.get();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!selectedVertex.equals(ClearVertex.get())){
			state = ScenarioState.SCALING;
			System.err.println("Pressionado.");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!selectedVertex.equals(ClearVertex.get())){
			state = ScenarioState.IDLE;
			System.err.println("Não-Pressionado.");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (state == ScenarioState.SCALING){
			selectedVertex.change(e.getX(), e.getY());
			selected.refreshBBox();
			glDrawable.display();
			selectedVertex.showVertex(0);
		}
	}

	/**
	 * Evento realizado ao mover o mouse
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (state == ScenarioState.CREATING){
			selected.setMovingVertex(new Vertex(e.getX(), e.getY()));
			glDrawable.display();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) { }

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) { }

}
