package model;

public class CriterioEvaluacion {
	private String criterio;
	
	public CriterioEvaluacion(String criterio) {
		this.criterio = criterio;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	@Override
	public String toString() {
		return criterio;
	}

	
	
	
}
