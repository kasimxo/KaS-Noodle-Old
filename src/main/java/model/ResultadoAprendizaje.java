package model;

import java.util.HashMap;

public class ResultadoAprendizaje {
	
	private String descripcion;
	private HashMap<String, CriterioEvaluacion> criterios;
	
	public ResultadoAprendizaje(String descripcion) {
		this.descripcion = descripcion;
		this.criterios = new HashMap<String, CriterioEvaluacion>();
	}
	
	public void addCriterio(String criterio) {
		if(!criterios.containsKey(criterio)) {
			criterios.put(criterio, new CriterioEvaluacion(criterio));
		}
	}

	@Override
	public String toString() {
		StringBuilder texto = new StringBuilder();
		texto.append("ResultadoAprendizaje: "+ descripcion + "\n\n");

		criterios.forEach((K,V) -> {
			texto.append("\t\t"+V+"\n");
		});
		return texto.toString();
	}

}
