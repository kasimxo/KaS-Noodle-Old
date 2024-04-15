package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modulo {
	private String nombre;
	//private List<ResultadoAprendizaje> ras;
	private HashMap<String, ResultadoAprendizaje> ras;
	
	public Modulo(String nombre) {
		nombre = nombre.trim();
		
		if(nombre.charAt(nombre.length()-1)=='.') {
			nombre = nombre.substring(0, nombre.length()-1);
		}
		
		this.nombre = nombre;
		//this.ras = new ArrayList<ResultadoAprendizaje>();
		this.ras = new HashMap<String, ResultadoAprendizaje>();
	}
	
	public void addResultadoAprendizaje(String ra) {
		ras.put(ra, new ResultadoAprendizaje(ra));
	}
	
	
	/*
	public void addResultadoAprendizaje(ResultadoAprendizaje ra) {
		ras.add(ra);
	}
	*/

	/**
	 * Método que añade un criterio de evaluación a un resultado de aprendizaje
	 * @param ra -> El resultado de aprendizaje al que le queremos meter el criterio
	 * @param criterio
	 */
	public void addCriterioEvaluacion(String ra, String criterio) {
		ras.get(ra).addCriterio(criterio);
	}
	
	@Override
	public String toString() {
		
		StringBuilder texto = new StringBuilder();
		texto.append("Módulo Profesional: "+ nombre + "\n\n");

		ras.forEach((K,V) -> {
			texto.append("\t"+V+"\n");
		});
		return texto.toString();
	}
	
}
