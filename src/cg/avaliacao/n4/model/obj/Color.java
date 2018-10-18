package cg.avaliacao.n4.model.obj;

/**
 * Representa uma cor em RGBA.
 * 
 * @author Vinícius
 */
public class Color {

	private final float r;
	private final float g;
	private final float b;
	private final float a;

	/**
	 * Construtor da classe
	 */
	public Color() {
		this(0.0f, 0.0f, 0.0f);
	}

	/**
	 * Construtor da classe
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public Color(float r, float g, float b) {
		this(r, g, b, 0.0f);
	}

	/**
	 * Construtor da classe
	 * 
	 * @param r vermelho
	 * @param g verde
	 * @param b azul
	 * @param a
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Obtém o valor para a escala em vermelho.
	 * 
	 * @return o valor para a escala em vermelho.
	 */
	public float getR() {
		return r;
	}

	/**
	 * Obtém o valor para a escala em verde.
	 * 
	 * @return o valor para a escala em verde.
	 */
	public float getG() {
		return g;
	}

	/**
	 * Obtém o valor para a escala em azul.
	 * 
	 * @return o valor para a escala em azul.
	 */
	public float getB() {
		return b;
	}

	/**
	 * Obtém o valor para a escala em.
	 * 
	 * @return o valor para a escala em.
	 */
	public float getA() {
		return a;
	}

}