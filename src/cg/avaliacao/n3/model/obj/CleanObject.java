package cg.avaliacao.n3.model.obj;

import javax.media.opengl.GL;


/**
 * Representa um objeto gráfico nulo.
 * 
 * @author teixeira
 */
public final class CleanObject extends ObjetoGrafico {
	
	private static final CleanObject INSTANCE = new CleanObject();
	
	public static CleanObject get(){
		return INSTANCE;
	}
	
	private CleanObject(){
		super(INSTANCE);
	}
	
	@Override
	public void desenha(GL gl) {
	}
	
	@Override
	public void desenhaBBox(GL gl) {
	}
	
	@Override
	public void exibeObjGrafico() {
		System.out.println("Nenhuma objeto selecionado.");;
	}
	
	@Override
	public void exibeMatriz() {
		System.out.println("Nenhum objeto selecionado.");
	}
	
}
