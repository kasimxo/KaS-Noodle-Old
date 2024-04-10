package model;

import java.util.ArrayList;
import java.util.List;

public class Modulo {
	private String nombre;
	private List<ResultadoAprendizaje> ras;
	
	public Modulo(String nombre) {
		nombre = nombre.trim();
		
		if(nombre.charAt(nombre.length()-1)=='.') {
			nombre = nombre.substring(0, nombre.length()-1);
		}
		
		this.nombre = nombre;
		this.ras = new ArrayList<ResultadoAprendizaje>();
	}
	
	public void addResultadoAprendizaje(ResultadoAprendizaje ra) {
		ras.add(ra);
	}

	@Override
	public String toString() {
		
		String texto = String.format("Módulo Profesional: %s\n", nombre);
		for(ResultadoAprendizaje ra : ras) {
			texto += ra + "\n";
		}
		return  texto;
	}
	
}
