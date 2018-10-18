package cg.avaliacao.n3.model;

import javax.media.opengl.GL;

import cg.avaliacao.n3.model.obj.Vertex;

/**
 * Representação do um vértice nulo.
 * 
 * @author teixeira
 */
public class ClearVertex extends Vertex {
	
	private static final ClearVertex INSTANCE = new ClearVertex();
	
	private ClearVertex(){
		super(0.0d, 0.0d);
	}
	
	/**
	 * Obtém a única instância do objeto.
	 * 
	 * @return a única instância.
	 */
	public static ClearVertex get() {
		return INSTANCE;
	}
	
	@Override
	public void showVertex(int index) {
		System.out.println("Nenhum vértice selecionado.");
	}
	
	@Override
	public void draw(GL gl) {
	}
	
}
