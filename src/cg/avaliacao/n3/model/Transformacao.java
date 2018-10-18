package cg.avaliacao.n3.model;

import cg.avaliacao.n3.model.obj.Vertex;

/**
 * Realiza as transformações do polígono conforme a sua matriz
 * 
 * @author Vinicius
 *
 */
public class Transformacao {
	
	public static final double DEG_TO_RAD = 0.017453292519943295769236907684886;

	private double[] matrix = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

	/**
	 * Construtor da classe
	 */
	public Transformacao() {
	}

	/**
	 * Retorna a matriz de identidade do polígono
	 */
	public void makeIdentity() {
		for (int i = 0; i < 16; ++i) {
			matrix[i] = 0.0;
			matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
		}
	}

	/**
	 * Realiza a translação do polígono
	 * 
	 * @param translationVector Vértice
	 */
	public void makeTranslation(Vertex translationVector) {
		makeIdentity();
		matrix[12] = translationVector.getX();
		matrix[13] = translationVector.getY();
		matrix[14] = translationVector.getZ();
	}

	/**
	 * Realiza a rotação do polígono com base no X
	 * 
	 * @param radians valor de x
	 */
	public void makeXRotation(double radians) {
		makeIdentity();
		matrix[5] = Math.cos(radians);
		matrix[9] = -Math.sin(radians);
		matrix[6] = Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	/**
	 * Realiza a rotação do polígono com base no Y
	 * 
	 * @param radians valor de y
	 */
	public void makeYRotation(double radians) {
		makeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[8] = Math.sin(radians);
		matrix[2] = -Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	/**
	 * Realiza a rotação do polígono com base no Z
	 * 
	 * @param radians valor de z
	 */
	public void makeZRotation(double radians) {
		makeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[4] = -Math.sin(radians);
		matrix[1] = Math.sin(radians);
		matrix[5] = Math.cos(radians);
	}

	/**
	 * Realiza o escalonamento do polígono
	 * 
	 * @param sX valor de x
	 * @param sY valor de y
	 * @param sZ valor de z
	 */
	public void makeScale(double sX, double sY, double sZ) {
		makeIdentity();
		matrix[0] = sX;
		matrix[5] = sY;
		matrix[10] = sZ;
	}

	/**
	 * Transformação dos pontos
	 * 
	 * @param point Vértice
	 * 
	 * @return Vértice transformado
	 */
	public Vertex transformPoint(Vertex point) {
		Vertex pointResult = new Vertex(matrix[0] * point.getX() + matrix[4]
				* point.getY() + matrix[8] * point.getZ() + matrix[12]
				* point.getW(), matrix[1] * point.getX() + matrix[5]
				* point.getY() + matrix[9] * point.getZ() + matrix[13]
				* point.getW(), matrix[2] * point.getX() + matrix[6]
				* point.getY() + matrix[10] * point.getZ() + matrix[14]
				* point.getW(), matrix[3] * point.getX() + matrix[7]
				* point.getY() + matrix[11] * point.getZ() + matrix[15]
				* point.getW());
		return pointResult;
	}

	/**
	 * Transforma a matriz do polígono
	 * 
	 * @param t Objeto de transformação
	 * 
	 * @return a matriz do polígono
	 */
	public Transformacao transformMatrix(Transformacao t) {
		Transformacao result = new Transformacao();
		for (int i = 0; i < 16; ++i)
			result.matrix[i] = matrix[i % 4] * t.matrix[i / 4 * 4]
					+ matrix[(i % 4) + 4] * t.matrix[i / 4 * 4 + 1]
					+ matrix[(i % 4) + 8] * t.matrix[i / 4 * 4 + 2]
					+ matrix[(i % 4) + 12] * t.matrix[i / 4 * 4 + 3];
		return result;
	}

	/**
	 * Retorna o elemento da matriz
	 * 
	 * @param index índice
	 * 
	 * @return o elemento da matriz
	 */
	public double getElement(int index) {
		return matrix[index];
	}

	/**
	 * Retorna a matriz do polígono
	 * 
	 * @return a matriz do polígono
	 */
	public double[] getDate() {
		return matrix;
	}

	/**
	 * Exibe as informações da matriz do polígono
	 * 
	 */
	public void exibeMatriz() {
		System.out.println("______________________");
		System.out.println("|" + getElement(0) + " | "
				+ getElement(1) + " | " + getElement(2) + " | "
				+ getElement(3));
		System.out.println("|" + getElement(4) + " | "
				+ getElement(5) + " | " + getElement(6) + " | "
				+ getElement(7));
		System.out.println("|" + getElement(8) + " | "
				+ getElement(9) + " | " + getElement(10) + " | "
				+ getElement(11));
		System.out.println("|" + getElement(12) + " | "
				+ getElement(13) + " | " + getElement(14) + " | "
				+ getElement(15));
	}
	
}
