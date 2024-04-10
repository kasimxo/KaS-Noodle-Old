package model;

public class ResultadoAprendizaje {
	
	private String descripcion;
	
	public ResultadoAprendizaje(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "ResultadoAprendizaje: " + descripcion;
	}

}
