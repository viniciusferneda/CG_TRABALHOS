package cg.avaliacao.n4.model;

/**
 * Enumeração dos estados do {@link Scenario}.
 * 
 * @author Vinícius
 */
public enum ScenarioState {
	
	/** Está ocioso. */
	IDLE,
	/** Está desenhando. */
	DRAWING,
	/** Está criando um novo objeto. */
	CREATING,
	/** Escala o objeto. */
	SCALING;
}
