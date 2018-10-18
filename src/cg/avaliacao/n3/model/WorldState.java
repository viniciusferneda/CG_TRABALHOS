package cg.avaliacao.n3.model;

/**
 * Enumeração dos estados do {@link World}.
 * 
 * @author teixeira
 */
public enum WorldState {
	
	/** Está ocioso. */
	IDLE,
	/** Está desenhando. */
	DRAWING,
	/** Está criando um novo objeto. */
	CREATING,
	/** Escala o objeto. */
	SCALING;
}
