package cg.avaliacao.n4.model.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import cg.avaliacao.n4.model.ClearVertex;
import cg.avaliacao.n4.model.Transformacao;

/**
 * Classe responsável pela ações que devem ser realizadas no polígono
 * 
 * @author Vinicius
 *
 */
public class ObjetoGrafico {

	private Transformacao matriz = new Transformacao();

	/**
	 * ATENÇÃO! SEMPRE MULTIPICAÇÃO DE MATRIZ É FEITA EM ORDEM INVERSA
	 */

	private final Vertex translacao = new Vertex();

	private final List<Vertex> vertices = new ArrayList<Vertex>();
	private final List<ObjetoGrafico> children = new ArrayList<ObjetoGrafico>();

	private final BBox bBox;
	private Vertex selectedVertex = ClearVertex.get();
	private Color cor = new Color();
	private int primitiva = GL.GL_LINE_STRIP;

	private boolean selected = true;
	
	private Vertex movingVertex = ClearVertex.get();

	/**
	 * Construtor da classe
	 * 
	 * @param parent polígono
	 */
	public ObjetoGrafico(ObjetoGrafico parent) {
		bBox = new BBox();
	}
	
	/**
	 * Determina a posição do polígono
	 * 
	 * @param vertex Vértice
	 */
	public void setMovingVertex(Vertex vertex) {
		this.movingVertex = vertex;
	}
	
	public void setPrimitiva(int primitiva) {
		this.primitiva = primitiva;
	}

	/**
	 * Adiciona um polígono filho
	 * 
	 * @param obj Polígono
	 */
	public void addChild(ObjetoGrafico obj) {
		children.add(obj);
	}

	/**
	 * Adiciona um ponto ao polígono
	 * 
	 * @param vertex Vértice
	 */
	public void addPonto(Vertex vertex) {
		if (isLine() && vertices.isEmpty()) {
			// gambi pq para usar o line_loop tem que ter no mínimo 2 vertices
			vertices.add(vertex);
		}
		vertices.add(vertex);
		refreshBBox(vertex);
	}

	/**
	 * Atualiza os valores da Bounding Box.
	 * @param vertex
	 */
	public void refreshBBox() {
		bBox.clear();
		for (Vertex v : vertices) {
			refreshBBox(v);
		}
	}

	private void refreshBBox(Vertex v) {
		bBox.refreshValues(v);
	}

	/**
	 * Verifica se é uma linha
	 * 
	 * @return true ou false
	 */
	private boolean isLine() {
		return primitiva == GL.GL_LINE_LOOP || primitiva == GL.GL_LINE_STRIP;
	}

	/**
	 * Define a cor do polígono
	 * 
	 * @param cor Objeto Cor
	 */
	public void setCor(Color cor) {
		this.cor = cor;
	}

	/**
	 * Desenha o polígono na tela
	 * 
	 * @param gl Objeto
	 */
	public void desenha(GL gl) {

		gl.glColor3f(cor.getR(), cor.getG(), cor.getB());

		gl.glPushMatrix();
			gl.glMultMatrixd(matriz.getDate(), 0);
			gl.glBegin(primitiva);
				for (Vertex p : vertices) {
					gl.glVertex2d(p.getX(), p.getY());
				}
				
			if (!movingVertex.equals(ClearVertex.get())){
				//gl.glLineWidth(0.2f);
				gl.glColor4f(1.0f, 1.0f, 0.0f, 0.0f);
				gl.glVertex2d(movingVertex.getX(), movingVertex.getY());
			}
			gl.glEnd();
			
			for (ObjetoGrafico o : children) {
				o.desenha(gl);
			}
			
		if (selected){
			desenhaBBox(gl);
			desenhaPontoSelecionado(gl);
		}
		gl.glPopMatrix();

	}

	/**
	 * Exibe a matriz
	 */
	public void exibeMatriz() {
		matriz.exibeMatriz();
	}

	/**
	 * Inicializa o polígono
	 */
	public void inicializaObjGrafico() {
		translacao.clean();
		matriz.makeIdentity();
	}

	/**
	 * Realiza a translação de uma matriz conforme o ponto passado como parâmetro
	 * 
	 * @param ponto Vértice
	 * 
	 * @return Martriz transladada
	 */
	private Transformacao matrizTranslacao(Vertex ponto) {
		translacao.sumVertex(ponto);
		Transformacao matrixTranslacao = new Transformacao();
		matrixTranslacao.makeTranslation(ponto);
		return (matrixTranslacao);
	}

	/**
	 * Realiza a translação de um polígono conforme o ponto passado como parâmetro
	 * 
	 * @param ponto Vértice
	 */
	public void matrizTranslacaoObjGrafico(Vertex ponto) {
		matriz = matriz.transformMatrix(matrizTranslacao(ponto));
	}

	/**
	 * Desenha a BBox referente ao polígono selecionado
	 * 
	 * @param gl Objeto
	 */
	public void desenhaBBox(GL gl) {
		bBox.draw(gl);
	}
	
	/**
	 * Desenha o ponto selecionado no polígono
	 * 
	 * @param gl Objeto
	 */
	public void desenhaPontoSelecionado(GL gl){
		selectedVertex.draw(gl);
	}

	/**
	 * Realiza a seleção do polígono
	 * 
	 * @param selected boolean
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (!selected){
			for (ObjetoGrafico c : children) {
				c.setSelected(selected);
			}
		}
	}

	/**
	 * Verifica se o polígono está selecionado
	 * 
	 * @return Boolean
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Verifica se o polígono está selecionado e possui um filho associado
	 * @param v Vértice
	 * @return Polígono
	 */
	public ObjetoGrafico verifySelected(Vertex v) {
		if (bBox.pointInsideBBox(v) && insideByScanline(v)){
			return this;
		}
		for(ObjetoGrafico c: children){
			ObjetoGrafico selected = c.verifySelected(v);
			if (!selected.equals(CleanObject.get())){
				return selected;
			}
		}
		return CleanObject.get();
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
    private boolean insideByScanline(Vertex v) {
        int intersectionCount = 0;
        List<Vertex> vertices = new ArrayList<>(this.vertices);

        for (int i = 0; i < vertices.size(); i++) {
            Vertex p1 = vertices.get(i);
             Vertex p2 = vertices.get((i + 1) % vertices.size());

            if (intersectionValue(p1, p2, v) != -1) {
                intersectionCount++;
            }
        }

        return intersectionCount % 2 != 0; //percisa de quantiade impar de intersções

    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @param selectedPoint
     * @return
     */
    private double intersectionValue(Vertex p1, Vertex p2, Vertex selectedPoint) {
        double t = (selectedPoint.getY() - p1.getY()) / (p2.getY() - p1.getY());

        if (t <= 1 && t >= 0) {
            double x = p1.getX() + (p2.getX() - p1.getX()) * t;

            if (selectedPoint.getX() <= x) {
                return t;
            }
        }
        return -1;
    }

	/**
	 * Retorna um ponto selecionado
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * 
	 * @return um ponto selecionado
	 */
	public Vertex getSelectedVertex(int x, int y) {
		for(Vertex v: vertices){
			if (v.isNear(x, y)){
				selectedVertex = v;
				return v;
			}
		}
		return ClearVertex.get();
	}
	
	/**
	 * Retorna o centro do objeto referente ao ponto X
	 * 
	 * @return o centro do objeto referente ao ponto X
	 */ 
	public double getMiddleX(){
		return bBox.getMiddleX();
	}

	/**
	 * Retorna o centro do objeto referente ao ponto Y
	 * 
	 * @return o centro do objeto referente ao ponto Y
	 */
	public double getMiddleY(){
		return bBox.getMiddleY();
	}

}
