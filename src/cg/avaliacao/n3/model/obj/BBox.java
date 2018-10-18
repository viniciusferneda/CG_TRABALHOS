package cg.avaliacao.n3.model.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;


/**
 * Representação de uma bounding box de uma {@link ObjetoGrafico}.
 * 
 * @author teixeira
 */
public class BBox {

	private double xMin = Double.MAX_VALUE;
	private double xMax = Double.MIN_VALUE;

	private double yMin = Double.MAX_VALUE;
	private double yMax = Double.MIN_VALUE;

	private double zMin = 0.0;
	private double zMax = 0.0;
	
	private final List<Vertex> vertices = new ArrayList<Vertex>();

	/**
	 * Obtém o menor valor para X.
	 * 
	 * @return o menor valor para X.
	 */
	public double getxMin() {
		return xMin;
	}

	/**
	 * Obtém o maior valor para X.
	 * 
	 * @return o maior valor para X.
	 */
	public double getxMax() {
		return xMax;
	}

	
	/**
	 * Obtém o menor valor para Y.
	 * 
	 * @return o menor valor para Y.
	 */
	public double getyMin() {
		return yMin;
	}

	/**
	 * Obtém o maior valor para Y.
	 * 
	 * @return o maior valor para Y.
	 */
	public double getyMax() {
		return yMax;
	}

	/**
	 * Obtém o menor valor para Z.
	 * 
	 * @return o menor valor para Z.
	 */
	public double getzMin() {
		return zMin;
	}

	/**
	 * Obtém o maior valor para Z.
	 * 
	 * @return o maior valor para Z.
	 */
	public double getzMax() {
		return zMax;
	}

	/**
	 * Atualiza os valor da {@link BBox} com o {@link Vertex} passado.
	 * 
	 * @param ponto {@link Vertex} de referência para atualizar os valores da {@link BBox}.
	 */
	public void refreshValues(Vertex ponto) {
		boolean changed = false;
		if (ponto.getX() < xMin) {
			xMin = ponto.getX();
			changed = true;
		}
		if (ponto.getX() > xMax) {
			xMax = ponto.getX();
			changed = true;
		}
		if (ponto.getY() < yMin) {
			yMin = ponto.getY();
			changed = true;
		}
		if (ponto.getY() > yMax) {
			yMax = ponto.getY();
			changed = true;
		}

		if (changed){
			refreshVertices();
		}
	}

	/**
	 * Verificar se o {@link Vertex} está dentro da {@link BBox}.
	 * 
	 * @param ponto {@link Vertex} que deve ser verificado se está contido na {@link BBox}.
	 * 
	 * @return <code>true</code> caso o {@link Vertex} esteja contigo na {@link BBox}.
	 */
	public boolean pointInsideBBox(Vertex ponto) {
		// SENTA QUE LÁ VEM HISTÓRIA.
		// Devido a tela ser incializada de forma invertida: Menor Y é em cima e maior é em baixo,
		// A verificação do Y nas medidas da boundingbox deve ser invertida também.
		// Ou seja, o Y do clique precisa ser ma
		return xMin <= ponto.getX() 
				&& xMax >= ponto.getX()
				&& yMin <= ponto.getY() 
				&& yMax >= ponto.getY()
				&& zMin <= ponto.getZ() 
				&& zMax >= ponto.getZ();
	}

	/**
	 * Desenha o {@link BBox} via api OpenGl.
	 * 
	 * @param gl middleware para desenhar a {@link BBox}.
	 */
	public void draw(GL gl) {
		gl.glLineWidth(0.2f);
		gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);

		gl.glBegin(GL.GL_LINE_LOOP);
		for (Vertex p : getVertices()) {
			gl.glVertex3d(p.getX(), p.getY(), p.getZ());
		}
		gl.glEnd();
	}

	/**
	 * Obtém a lista de {@link Vertex} para a {@link BBox}.
	 * @return
	 */
	private List<Vertex> getVertices() {
		return vertices;
	}
	
	/**
	 * Atualiza os pontos do polígono
	 */
	private void refreshVertices(){
		vertices.clear();
		vertices.add(new Vertex(xMin, yMin, zMin));
		vertices.add(new Vertex(xMax, yMin, zMin));
		vertices.add(new Vertex(xMax, yMax, zMin));
		vertices.add(new Vertex(xMin, yMax, zMin));
	}
	
	/**
	 * Retorna o centro do objeto referente ao ponto X
	 * 
	 * @return Centro do objeto referente ao ponto X
	 */
	public double getMiddleX(){
		return xMin + ((xMax - xMin)/2);
	}

	/**
	 * Retorna o centro do objeto referente ao ponto Y
	 * 
	 * @return Centro do objeto referente ao ponto Y
	 */
	public double getMiddleY(){
		return yMin + ((yMax - yMin)/2);
	}
	
	public void clear() {
		xMin = Double.MAX_VALUE;
		xMax = Double.MIN_VALUE;

		yMin = Double.MAX_VALUE;
		yMax = Double.MIN_VALUE;

		zMin = 0.0;
		zMax = 0.0;	
	}

}
