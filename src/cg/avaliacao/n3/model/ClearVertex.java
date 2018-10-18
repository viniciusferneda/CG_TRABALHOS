package cg.avaliacao.n3.model;

import javax.media.opengl.GL;

import cg.avaliacao.n3.model.obj.Vertex;

/**
 * Representa��o do um v�rtice nulo.
 * 
 * @author teixeira
 */
public class ClearVertex extends Vertex {
	
	private static final ClearVertex INSTANCE = new ClearVertex();
	
	private ClearVertex(){
		super(0.0d, 0.0d);
	}
	
	/**
	 * Obt�m a �nica inst�ncia do objeto.
	 * 
	 * @return a �nica inst�ncia.
	 */
	public static ClearVertex get() {
		return INSTANCE;
	}
	
	@Override
	public void showVertex(int index) {
		System.out.println("Nenhum v�rtice selecionado.");
	}
	
	@Override
	public void draw(GL gl) {
	}
	
}
