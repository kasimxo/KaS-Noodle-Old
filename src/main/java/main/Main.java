package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;

import model.Modulo;
import model.ResultadoAprendizaje;
import noodleconfig.NoodleConfig;

public class Main {
	
	//public static List<Modulo> modulos;
	public static HashMap<String, Modulo> modulos;
	public static String modu;
	public static String ultimora;

	public static void main(String[] args) {
		
		//modulos = new ArrayList<Modulo>();
		modulos = new HashMap<String, Modulo>();
		modu = "";
		ultimora = "";
		
		
		System.out.println("Hola");
		System.out.println(NoodleConfig.CVsPath);
		
		File f = new File(NoodleConfig.CVsPath);
		
		File ff = f.listFiles()[0];
		
		File output = new File("./cvs/output.txt");

		if(!output.exists()) {
			System.out.println("Se ha creado el archivo output de pruebas");
			try {
				output.createNewFile();
			} catch (IOException e) {
				System.out.println("Ha fallado la creación del archivo de pruebas");
				e.printStackTrace();
			}
		}
		
		
		
		try {
			
			
			
			PDDocument document = Loader.loadPDF(ff);
			
			
			
			int paginas = document.getNumberOfPages();
			
			//List<String>  = new ArrayList<String>();
			HashMap<String, Integer> irrelevante = new HashMap();
			

			Boolean modulo = false;
			Boolean resultadoAprendizaje = false;
			Boolean criteriosEvaluacion = false;
			
			
			for(int i = 0; i<paginas; i++) {
				
				
				//Identificar header/footeer/coincidencias
				PDFTextStripper reader = new PDFTextStripper();
	            reader.setStartPage(i);
	            reader.setEndPage(i);
	            String pageText = reader.getText(document);
	            
	            String[] lineas = pageText.split("\n"); //Aquí ya estamos quitando todos los saltos de línea, por lo que no es necesario verlos
	            
	            String frase = "";
	            
	            for(String linea : lineas) {
	            	
	            	//Procesa una lína para asegurarse de que termina correctamente
	            	if(linea.length()>2 && linea.charAt(linea.length()-1)=='\r') {
	            		linea.substring(0, linea.length()-1);
	            		linea = linea.replaceAll("\\s+$", "");
	            		
	            		if(linea.length()>1 && (linea.charAt(linea.length()-1)=='.' || linea.charAt(linea.length()-1)==':')) {
	            			System.out.println(frase);
	            			frase = "";
	            			//Esto no tiene mucho sentido porque una línea puede terminar en un punto por casualidad

	            		}
	            		//System.out.print(linea);
	            	} else if(linea.compareTo("")==0){
	            		System.out.println(frase);
	            		frase = "";
	            	}
	            	frase += linea;
	            	
	            	//Identifica módulos profesionales
	            	if( (linea.trim().toLowerCase().startsWith("módulo profesional") || linea.trim().toLowerCase().startsWith("modulo profesional")) && linea.toLowerCase().contains(":") ) {
	            	
	            		//System.out.println(linea);
	            		String mod = linea.substring(linea.indexOf(":")+1);
	            		//System.out.println(mod);
	            		
	            		//Comprueba si están en el mapa y si no es así los mete
	            		if(!modulos.containsKey(mod)) {
	            			modulos.put(mod, new Modulo(mod));
	            			modu = mod;
	            		}
	            			//modulos.add(new Modulo(mod));
	            		modulo = true;
	            		
	            	}
	            	
	            	if(modulo) {
	            		//System.out.print(linea);
	            		//System.out.println("PENE");
        				//System.exit(0);
	            		if(linea.trim().toLowerCase().startsWith("resultados de aprendizaje y criterios de evaluaci")){
	            			resultadoAprendizaje = true;
	            		}
	            		if(resultadoAprendizaje) {
	            			//Aquí vamos a procesar la línea porque estamos dentro de los resultados de aprendizaje
	            			
	            			if(linea.matches("^[1-9]\\.\\s.*")) {
	            				//Aquí hacemos match de RA
	            				
	            				modulos.get(modu).addResultadoAprendizaje(linea);
	            				ultimora = linea;
	            				//System.out.println("PENE");
	            				System.out.println(linea);
	            				frase ="";
	            			}
	            			if(linea.trim().toLowerCase().startsWith("criterios de evaluaci")) {
	            				criteriosEvaluacion = true;
	            			}
	            			if(linea.trim().toLowerCase().matches("^[a-z]\\).*")){
	            				
	            				modulos.get(modu).addCriterioEvaluacion(ultimora, linea);
	            				System.out.println(frase);
	            				System.out.println(linea);
	            				//System.exit(0);
	            			}
	            		}
	            	}

	            	if(linea.trim().toLowerCase().startsWith("contenidos")  ) {
	            		modulo = false;
	            		resultadoAprendizaje = false;
	            		criteriosEvaluacion = false;
	            		ultimora = "";
	            		modu = "";
	            		System.out.println("------");
	            	}
	            }
			}
			
			irrelevante.forEach((K,V) -> {
				if(V>1) {
					//System.out.println(V+" -> "+K);
				}
			});
			
			System.out.println(ff.getName());
			
			System.out.println(irrelevante.size()); //Número de líneas en el documento
			
			modulos.forEach((K,V) -> {
				System.out.println(V);
			});
			
			
			document.close();
			
			FileWriter myWriter = new FileWriter(output);
			//myWriter.write(text);
			myWriter.close();
        	
        } catch (Exception e){
            e.printStackTrace();
        }
		
	

	}

}
