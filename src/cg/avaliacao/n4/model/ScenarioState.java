package cg.avaliacao.n4.model;

/**
 * Enumera��o dos estados do {@link Scenario}.
 * 
 * @author Vin�cius
 */
public enum ScenarioState {
	
	/** Est� ocioso. */
	IDLE,
	/** Est� desenhando. */
	DRAWING,
	/** Est� criando um novo objeto. */
	CREATING,
	/** Escala o objeto. */
	SCALING;
}
