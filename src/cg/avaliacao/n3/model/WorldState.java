package cg.avaliacao.n3.model;

/**
 * Enumera��o dos estados do {@link World}.
 * 
 * @author teixeira
 */
public enum WorldState {
	
	/** Est� ocioso. */
	IDLE,
	/** Est� desenhando. */
	DRAWING,
	/** Est� criando um novo objeto. */
	CREATING,
	/** Escala o objeto. */
	SCALING;
}
