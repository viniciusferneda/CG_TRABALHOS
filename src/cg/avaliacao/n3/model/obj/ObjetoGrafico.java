package cg.avaliacao.n3.model.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import cg.avaliacao.n3.model.ClearVertex;
import cg.avaliacao.n3.model.Transformacao;

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
	private double angulo;

	private final ObjetoGrafico parent;
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
		this.parent = parent;
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
	 * Remove o ponto do polígono
	 * 
	 * @param ponto Vértice
	 */
	public void removePonto(Vertex ponto) {
		vertices.remove(ponto);
		
		if (selectedVertex.equals(ponto)){
			selectedVertex = ClearVertex.get();
		}
		
		if (isLine() && vertices.size() < 1) {
			// gambi pq para usar o line_loop tem que ter no mínimo 2 vertices
			vertices.add(vertices.get(0));
		}
		
		refreshBBox();
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
	 * Exibe as informações do polígono
	 */
	public void exibeObjGrafico() {
		System.out.println("__Angulo: " + angulo + "__ translacao["
				+ translacao.getX() + "," + translacao.getY() + ","
				+ translacao.getZ() + "]");
		int i = 0;
		for (Vertex v : vertices) {
			v.showVertex(i++);
		}
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
		angulo = 0;
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
	 * Realiza o escalonamento do polígono
	 * 
	 * @param sx valor de x
	 * @param sy valor de y
	 * @param sz valor de z
	 * 
	 * @return Polígono escalonado
	 */
	public Transformacao matrizEscala(double sx, double sy, double sz) {
		Transformacao matrixEscala = new Transformacao();
		matrixEscala.makeScale(sx, sy, sz);
		return (matrixEscala);
	}

	/**
	 * Realiza o escalonamento do polígono já com a transformação da matriz
	 * 
	 * @param sx valor de X
	 * @param sy valor de Y
	 * @param sz valor de Z
	 */
	public void matrizEscalaObjGrafico(double sx, double sy, double sz) {
		matriz = matriz.transformMatrix(matrizEscala(sx, sy, sz));
	}

	/**
	 * Rotaciona o polígono no sentido anti-horário
	 * 
	 * @return Polígono rotacionado
	 */
	public Transformacao matrizRotacaoAntiHoraria() {
		angulo -= 10;
		Transformacao matrixRotacao = new Transformacao();
		matrixRotacao.makeZRotation(Transformacao.DEG_TO_RAD * -10);
		return (matrixRotacao);
	}

	/**
	 * Rotaciona o polígono no sentido anti-horário realizando a sua transformação da matriz
	 */
	public void matrizRotacaoAntiHorariaObjGrafico() {
		matriz = matriz.transformMatrix(matrizRotacaoAntiHoraria());
	}

	/**
	 * Rotaciona o polígono no sentido anti-horário baseado no ponto fixo do polígono
	 * 
	 * @param deslocamento Vértice
	 */
	public void matrizRotacaoAntiHorariaPontoFixoObjGrafico(Vertex deslocamento) {
		Vertex deslocamentoInverso = new Vertex(-deslocamento.getX(),
				-deslocamento.getY(), -deslocamento.getZ(), deslocamento.getW());
		Transformacao matrixTranslacao = new Transformacao();
		matrixTranslacao = matrizTranslacao(deslocamentoInverso);

		Transformacao matrizRotacao = new Transformacao();
		matrizRotacao = matrizRotacaoAntiHoraria();

		Transformacao matrizTranslacaoInversa = new Transformacao();
		matrizTranslacaoInversa = matrizTranslacao(deslocamento);

		// multiplicar na ordem inversa as matrizes de transformacoes
		Transformacao matrixGlobal = new Transformacao();
		matrixGlobal = matrixGlobal.transformMatrix(matrizTranslacaoInversa);
		matrixGlobal = matrixGlobal.transformMatrix(matrizRotacao);
		matrixGlobal = matrixGlobal.transformMatrix(matrixTranslacao);
		matriz = matriz.transformMatrix(matrixGlobal);
	}

	/**
	 * Realiza o escalonamento do polígono baseado no ponto fixo do polígono
	 * 
	 * @param deslocamento Vértice
	 * @param amplia true para ampliar o objeto e false para diminuir
	 */
	public void matrizEscalaPontoFixoObjGrafico(Vertex deslocamento, boolean amplia) {
		
		Vertex deslocamentoInverso = new Vertex(-deslocamento.getX(),
				-deslocamento.getY(), -deslocamento.getZ(), deslocamento.getW());
		
		Transformacao matrixTranslacao = new Transformacao();
		matrixTranslacao = matrizTranslacao(deslocamentoInverso);

		Transformacao matrizEscala = new Transformacao();
		if (amplia)
			matrizEscala = matrizEscala(2, 2, 0);
		else
			matrizEscala = matrizEscala(0.5, 0.5, 0);

		Transformacao matrizTranslacaoInversa = new Transformacao();
		matrizTranslacaoInversa = matrizTranslacao(deslocamento);

		// multiplicar na ordem inversa as matrizes de transformacoes
		Transformacao matrixGlobal = new Transformacao();
		matrixGlobal = matrixGlobal.transformMatrix(matrizTranslacaoInversa);
		matrixGlobal = matrixGlobal.transformMatrix(matrizEscala);
		matrixGlobal = matrixGlobal.transformMatrix(matrixTranslacao);
		matriz = matriz.transformMatrix(matrixGlobal);
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
	 * Rotaciona o polígono no sentido horário baseado no ponto fixo do polígono
	 * 
	 * @param deslocamento Vértice
	 */
	public void matrizRotacaoHorariaPontoFixoObjGrafico(Vertex deslocamento) {
		Vertex deslocamentoInverso = new Vertex(-deslocamento.getX(),
				-deslocamento.getY(), -deslocamento.getZ(), deslocamento.getW());
		Transformacao matrixTranslacao = new Transformacao();
		matrixTranslacao = matrizTranslacao(deslocamentoInverso);

		Transformacao matrizRotacao = new Transformacao();
		matrizRotacao = matrizRotacaoHoraria();

		Transformacao matrizTranslacaoInversa = new Transformacao();
		matrizTranslacaoInversa = matrizTranslacao(deslocamento);

		// multiplicar na ordem inversa as matrizes de transformacoes
		Transformacao matrixGlobal = new Transformacao();
		matrixGlobal = matrixGlobal.transformMatrix(matrizTranslacaoInversa);
		matrixGlobal = matrixGlobal.transformMatrix(matrizRotacao);
		matrixGlobal = matrixGlobal.transformMatrix(matrixTranslacao);
		matriz = matriz.transformMatrix(matrixGlobal);
	}

	/**
	 * Rotaciona o polígono no sentido anti-horário
	 */
	public void matrizRotacaoHorariaObjGrafico() {
		matriz = matriz.transformMatrix(matrizRotacaoHoraria());
	}

	/**
	 * Rotaciona o polígono no sentido anti-horário
	 * 
	 * @return o polígono no sentido anti-horário
	 */
	public Transformacao matrizRotacaoHoraria() {
		angulo += 10;
		Transformacao matrixRotacao = new Transformacao();
		matrixRotacao.makeZRotation(Transformacao.DEG_TO_RAD * 10);
		return (matrixRotacao);
	}
	
	/**
	 * Retorna o objeto pai do polígono
	 * 
	 * @return o objeto pai do polígono
	 */
	public ObjetoGrafico getParent() {
		return parent;
	}

	/**
	 * Remove um polígono filho
	 * 
	 * @param selected Polígono
	 */
	public void removeChild(ObjetoGrafico selected) {
		children.remove(selected);
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
