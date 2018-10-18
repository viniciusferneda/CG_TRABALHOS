package cg.avaliacao.n3.model.obj;

import javax.media.opengl.GL;

/**
 * Represeta��o de uma v�rtice.
 * 
 * @author teixeira
 */
public class Vertex {

	private double x;
	private double y;
	private double z;
	private double w;

	/**
	 * Construtor da classe
	 */
	public Vertex() {
		this(0.0d, 0.0d);
	}

	/**
	 * Construtor da classe
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 */
	public Vertex(double x, double y) {
		this(x, y, 0.0d, 1.0d);
	}

	/**
	 * Construtor da classe
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * @param z valor de z
	 */
	public Vertex(double x, double y, double z) {
		this(x, y, z, 1.0d);
	}

	/**
	 * Construtor da classe
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * @param z valor de z
	 * @param w valor de w
	 */
	public Vertex(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Verifica se o vertice est� pr�ximo
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * 
	 * @return boolean
	 */
	public boolean isNear(double x, double y){
		return isNear(x, y, 0.0);
	}
	
	/**
	 * Verifica se o vertice est� pr�ximo
	 * 
	 * @param x valor de x
	 * @param y valor de y
	 * @param z valor de z
	 * 
	 * @return boolean
	 */
	public boolean isNear(double x, double y, double z){
		double moreX = withMoreMargin(this.x);
		double lessX = withLessMargin(this.x);
		
		if(lessX > x || x > moreX) {
			return false;
		}
		
		double moreY = withMoreMargin(this.y);
		double lessY = withLessMargin(this.y);
		
		if(lessY > y || y > moreY) {
			return false;
		}
		
		double moreZ = withMoreMargin(this.z);
		double lessZ = withLessMargin(this.z);
		
		if(lessZ > z || z > moreZ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Verifica se o vertice est� pr�ximo conforme a marge determinada
	 * 
	 * @param value valor do v�rtice
	 * 
	 * @return posi��o do v�rtice
	 */
	private double withMoreMargin(double value){
		return value + 30.0;
	}
	
	/**
	 * Verifica se o vertice est� pr�ximo conforme a marge determinada
	 * 
	 * @param value valor do v�rtice
	 * 
	 * @return posi��o do v�rtice
	 */
	private double withLessMargin(double value){
		return value - 30.0;
	}

	/**
	 * Obt�m o valor de X do v�rtice.
	 * 
	 * @return o valor de X.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Permite informar o valor de X.
	 * 
	 * @param x valor para X.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Obt�m o valor de Y do v�rtice.
	 * 
	 * @return o valor de Y.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Permite informar o valor de Y.
	 * 
	 * @param x valor para Y.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Obt�m o valor de Z do v�rtice.
	 * 
	 * @return o valor de Z.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Permite informar o valor de Z.
	 * 
	 * @param x valor para Z.
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Obt�m o valor de W do v�rtice.
	 * 
	 * @return o valor de W.
	 */
	public double getW() {
		return w;
	}

	/**
	 * Permite informar o valor de W.
	 * 
	 * @param x valor para W.
	 */
	public void setW(double w) {
		this.w = w;
	}

	/**
	 * Soma os valor do v�rtice.
	 * 
	 * @param vertex v�rtice que deve-se somar.
	 */
	public void sumVertex(Vertex vertex) {
		this.x += vertex.getX();
		this.y += vertex.getY();
		this.z += vertex.getZ();
	}

	/**
	 * Imprime os dados do v�rtice no console.
	 * 
	 * @param index ind�ce
	 */
	public void showVertex(int index) {
		System.out.println(String.format("P%s[%s, %s, %s, %s]", index, getX(), getY(), getZ(), getW()));
	}

	/**
	 * Limpa os valores do v�rtice.
	 */
	public void clean() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * Realiza o desenho do v�rtice
	 * 
	 * @param gl Objeto
	 */
	public void draw(GL gl) {
		gl.glPointSize(5.0f);
		gl.glColor4f(1.0f, 0.0f, 1.0f, 0.0f);

		gl.glBegin(GL.GL_POINTS);
			gl.glVertex3d(x, y, z);
		gl.glEnd();
	}
	
	/**
	 * Muda os valores do v�rtice.
	 * 
	 * @param x valor de X.
	 * @param y valor de Y.
	 */
	public void change(double x, double y) {
		change(x, y, 0.0);
	}
	
	/**
	 * Mudas os valores do v�rtice.
	 * 
	 * @param x valor de X.
	 * @param y valor de Y.
	 * @param z valor de Z.
	 */
	public void change(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
