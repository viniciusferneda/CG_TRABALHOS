package cg.avaliacao.n3.model;

import java.awt.event.InputEvent;
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

import cg.avaliacao.n3.model.obj.CleanObject;
import cg.avaliacao.n3.model.obj.Color;
import cg.avaliacao.n3.model.obj.ObjetoGrafico;
import cg.avaliacao.n3.model.obj.Vertex;

/**
 * Representa um mundo 2D.
 * 
 * Medidas do ortho: 0 | 500 | 500 | 0
 * @author teixeira
 */
public class World implements GLEventListener, KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private List<ObjetoGrafico> objects = new ArrayList<ObjetoGrafico>();
	
	private ObjetoGrafico selected = CleanObject.get();
	private Vertex selectedVertex = ClearVertex.get();
	
	private WorldState state = WorldState.IDLE;
	
	private final int width;
	private final int height;
	
	private final Color red;
	private final Color green;
	private final Color blue;
	
	/**
	 * Construtor da classe World
	 * @param width altura da tela
	 * @param height largura da tela
	 */
	public World(int width, int height){
		this.width = width;
		this.height = height;
		this.red = new Color(1, 0, 0);
		this.green = new Color(0, 1, 0);
		this.blue = new Color(0, 0, 1);
	}

	/**
	 * Inicialza o as propriedades da tela
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	/**
	 * Responsável pela atualização da tela
	 */
	@Override
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_PROJECTION);
		 gl.glLoadIdentity();
		 glu.gluOrtho2D(0, width, height, 0);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();
		 
		 drawSRU();
		 for (ObjetoGrafico o : objects) {
			o.desenha(gl);
		 }

		gl.glFlush();
	}
	
	/**
	 * 
	 */
	public void drawSRU() {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(40, height /2);
			gl.glVertex2f(width - 40.0f, height /2);
		gl.glEnd();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(width /2, 40.0f);
			gl.glVertex2f(width /2, height - 40.0f);
		gl.glEnd();
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
		
		boolean controlPressed = isControlPressed(e.getModifiersEx());
		tryShortCurt(keyCode);
		if (tryRemove(keyCode)
				|| tryMove(keyCode)
				|| tryRotate(keyCode, controlPressed)
				|| tryColor(keyCode)
				|| tryShow(keyCode)
				|| tryEscala(keyCode, controlPressed)){
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
	 * Realiza a operação de escalonar o objeto em relação ao centro do proprio objeto ou o objeto grafico em relacao ao ortho
	 * A = Aumenta
	 * D = Diminui
	 * ctrl+A = Aumenta
	 * ctrl+D = Diminui
	 * 
	 * @param keyCode tecla pressionada
	 * @param controlPressed se a tecla ctrl está pressionada
	 * 
	 * @return boolean
	 */
	private boolean tryEscala(int keyCode, boolean controlPressed) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			if (controlPressed){
				selected.matrizEscalaPontoFixoObjGrafico(new Vertex(selected.getMiddleX(), selected.getMiddleY(), 0.0, 1.0), true);
			}else{
				selected.matrizEscalaObjGrafico(2, 2, 0);
			}
			break;
		case KeyEvent.VK_D:
			if (controlPressed){
				selected.matrizEscalaPontoFixoObjGrafico(new Vertex(selected.getMiddleX(), selected.getMiddleY(), 0.0, 1.0), false);
			}else{
				selected.matrizEscalaObjGrafico(0.5, 0.5, 0);
			}
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * Exibe o objeto gráfico através da tecla Enter
	 * @param keyCode tecla pressionada
	 * 
	 * @return boolean
	 */
	private boolean tryShow(int keyCode) {
		if (keyCode == KeyEvent.VK_ENTER){
			selected.exibeObjGrafico();
		}
		return false;
	}

	/**
	 * Remove o vertice ou poligono selecionado
	 * @param keyCode tecla pressionada
	 * @return boolean
	 */
	private boolean tryRemove(int keyCode) {
		if (keyCode == KeyEvent.VK_DELETE){
			if (!selectedVertex.equals(ClearVertex.get())) {
				selected.removePonto(selectedVertex);
				selectedVertex = ClearVertex.get();
			} else if (!selected.equals(CleanObject.get())){
				if (!selected.getParent().equals(CleanObject.get())) {
					selected.getParent().removeChild(selected);
				}
				objects.remove(selected);
				selected = CleanObject.get();
				selectedVertex = ClearVertex.get();
			}
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a tecla ctrl está pressionada
	 * @param modifiersEx tecla pressionada
	 * @return boolean
	 */
	private boolean isControlPressed(int modifiersEx) {
		return (modifiersEx == InputEvent.CTRL_DOWN_MASK);
	}

	/**
	 * Rotaciona o poligono sobre o proprio centro e também sobre o primeiro vertice 
	 * no sentido horario e anti-horario
	 * 
	 * Home = Sentido anti-horário
	 * ctrl+Home = Sentido anti-horário
	 * End = Sentido horário
	 * ctrl+End = Sentido horário
	 * 
	 * @param keyCode tecla pressionada
	 * @param controlDown tecla ctrl pressionada
	 * 
	 * @return boolean
	 */
	private boolean tryRotate(int keyCode, boolean controlDown) {
		switch (keyCode) {
		case KeyEvent.VK_HOME:
			if (controlDown){
				selected.matrizRotacaoAntiHorariaPontoFixoObjGrafico(new Vertex(selected.getMiddleX(), selected.getMiddleY()));
			}else{
				selected.matrizRotacaoAntiHorariaObjGrafico();
			}
			break;
		case KeyEvent.VK_END:
			if (controlDown){
				selected.matrizRotacaoHorariaPontoFixoObjGrafico(new Vertex(selected.getMiddleX(), selected.getMiddleY()));
			} else {
				selected.matrizRotacaoHorariaObjGrafico();
			}
			break;
		default:
			return false;
		}
		return true;
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
	 * Determina a cor do poligono
	 * R = Pinta de vermelho
	 * G = Pinta de verde
	 * B = Pinta de azul
	 * 
	 * @param keyCode tecla pressionada
	 * @return boolean
	 */
	private boolean tryColor(int keyCode){
		switch (keyCode) {
		case KeyEvent.VK_R:
			selected.setCor(red);
			break;
		case KeyEvent.VK_G:
			selected.setCor(green);
			break;
		case KeyEvent.VK_B:
			selected.setCor(blue);
			break;
		default:
			return false;
		}
		return true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) { }

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) { }

	public void zoom(){ }
	
	public void pan(){ }

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
		if (e.getButton() == MouseEvent.BUTTON1 && state != WorldState.CREATING) {
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
		
		if (state != WorldState.CREATING){
			ObjetoGrafico obj = new ObjetoGrafico(selected);
			
			if (!selected.equals(CleanObject.get())){
				selected.addChild(obj);
				selected.setSelected(false);
				obj.setSelected(true);
			}else{
				objects.add(obj);
			}
			
			state = WorldState.CREATING;
			selected = obj;
			selectedVertex = ClearVertex.get();
		}
		
		//cria o novo ponto
		Vertex vertex = new Vertex(e.getX(), e.getY());
		selected.addPonto(vertex);
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			state = WorldState.IDLE;
			selected.setMovingVertex(ClearVertex.get());
			if(isControlPressed(e.getModifiersEx())){
				selected.setPrimitiva(GL.GL_LINE_LOOP);
			}
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
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!selectedVertex.equals(ClearVertex.get())){
			state = WorldState.SCALING;
			System.err.println("Pressionado.");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!selectedVertex.equals(ClearVertex.get())){
			state = WorldState.IDLE;
			System.err.println("Não-Pressionado.");
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) {
		if (state == WorldState.SCALING){
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
		if (state == WorldState.CREATING){
			selected.setMovingVertex(new Vertex(e.getX(), e.getY()));
			glDrawable.display();
		}
	}

}
