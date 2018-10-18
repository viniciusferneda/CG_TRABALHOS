package cg.avaliacao.n4.model;

import cg.avaliacao.n4.model.obj.Vertex;

/**
 * Realiza as transforma��es do pol�gono conforme a sua matriz
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
	 * Retorna a matriz de identidade do pol�gono
	 */
	public void makeIdentity() {
		for (int i = 0; i < 16; ++i) {
			matrix[i] = 0.0;
			matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
		}
	}

	/**
	 * Realiza a transla��o do pol�gono
	 * 
	 * @param translationVector V�rtice
	 */
	public void makeTranslation(Vertex translationVector) {
		makeIdentity();
		matrix[12] = translationVector.getX();
		matrix[13] = translationVector.getY();
		matrix[14] = translationVector.getZ();
	}

	/**
	 * Transforma a matriz do pol�gono
	 * 
	 * @param t Objeto de transforma��o
	 * 
	 * @return a matriz do pol�gono
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
	 * @param index �ndice
	 * 
	 * @return o elemento da matriz
	 */
	public double getElement(int index) {
		return matrix[index];
	}

	/**
	 * Retorna a matriz do pol�gono
	 * 
	 * @return a matriz do pol�gono
	 */
	public double[] getDate() {
		return matrix;
	}

	/**
	 * Exibe as informa��es da matriz do pol�gono
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
