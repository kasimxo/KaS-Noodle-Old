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
import noodleconfig.NoodleConfig;

public class Main {
	
	public static List<Modulo> modulos;

	public static void main(String[] args) {
		
		modulos = new ArrayList<Modulo>();
		
		System.out.println("Hola");
		System.out.println(NoodleConfig.CVsPath);
		
		File f = new File(NoodleConfig.CVsPath);
		
		File ff = f.listFiles()[4];
		
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
			
			/*
			
			PDPageTree paginas = document.getPages();
			
			Iterator<PDPage> it = paginas.iterator();
			
			while(it.hasNext()) {
				PDPage page = (PDPage) it.next();
				
				String text = pdfStripper.getText(page);
			}
			
			*/
			
			int paginas = document.getNumberOfPages();
			
			//List<String>  = new ArrayList<String>();
			HashMap<String, Integer> irrelevante = new HashMap();
			

			Boolean modulo = false;
			Boolean resultadoAprendizaje = false;
			Boolean criterioEvaluacion = false;
			
			
			for(int i = 0; i<paginas; i++) {
				
				
				//Identificar header/footeer/coincidencias
				PDFTextStripper reader = new PDFTextStripper();
	            reader.setStartPage(i);
	            reader.setEndPage(i);
	            String pageText = reader.getText(document);
	            
	            String[] lineas = pageText.split("\n"); 
	            
	            String frase = "";
	            
	            for(String linea : lineas) {
	            	
	            	/*
	            	if(irrelevante.containsKey(linea)) {
	            		int cont = irrelevante.get(linea);
	            		//System.out.println("Actual: "+cont);
	            		cont++;
	            		irrelevante.put(linea, cont);
	            		//System.out.println("Updated: "+cont);
	            		//System.out.println("Se ha encontrado una coincidencia");
	            		
	            	} else {
	            		irrelevante.put(linea, 0);
	            		//
	            	}
	            	*/
	            	
	            	//Procesa una lína para asegurarse de que termina correctamente
	            	if(linea.length()>2 && (linea.charAt(linea.length()-1)=='\n' || linea.charAt(linea.length()-1)=='\r')) {
	            		linea.substring(0, linea.length()-1);
	            		linea = linea.replaceAll("\\s+$", "");
	            		
	            		if(linea.length()>1 && (linea.charAt(linea.length()-1)=='.' || linea.charAt(linea.length()-1)==':')) {
	            			System.out.println(frase);
	            			frase = "";
	            			//Esto no tiene mucho sentido porque una línea puede terminar en un punto por casualidad
	            			
	            			
	            		}
	            		//System.out.print(linea);
	            	} else if(linea.startsWith("\n")||linea.startsWith("\r")){
	            		System.out.println(frase);
	            		frase = "";
	            	}
	            	frase += linea;
	            	
	            	//Identifica módulos profesionales
	            	if( (linea.trim().toLowerCase().startsWith("módulo profesional") || linea.trim().toLowerCase().startsWith("modulo profesional")) && linea.toLowerCase().contains(":") ) {
	            	
	            		//System.out.println(linea);
	            		String mod = linea.substring(linea.indexOf(":")+1);
	            		//System.out.println(mod);
	            		modulos.add(new Modulo(mod));
	            		modulo = true;
	            		
	            	}
	            	
	            	if(modulo) {
	            		//System.out.print(linea);
	            		
	            		if(linea.trim().toLowerCase().startsWith("resultados de aprendizaje y criterios de evaluaci")){
	            			resultadoAprendizaje = true;
	            		}
	            		if(resultadoAprendizaje) {
	            			System.out.print(linea);
	            		}
	            	}
	            	
	            	
	            	
	            	if(linea.trim().toLowerCase().startsWith("contenidos")  ) {
	            		modulo = false;
	            		resultadoAprendizaje = false;
	            		criterioEvaluacion = false;
	            		System.out.println("------");
	            	}
	            	
	            	
	            	
	            	
	            	

	            	/*
	            	if(linea.length()>=2 && (linea.charAt(linea.length()-1)=='\n' || linea.charAt(linea.length()-1)=='\r') && (linea.charAt(linea.length()-2)=='.' || linea.charAt(linea.length()-2)==':')) {

	            		System.out.print(linea);
	            	} else if(linea.length()>=2) {
	            		System.out.print(linea.substring(0, linea.length()-1));
	            	} else {
	            		//System.out.print(linea);
	            	}
	            	*/
	            	
	            	//System.out.println("*");
	            }

	            //System.out.println(pageText);
	            //System.out.println("\n------++++++------");
			}
			
			irrelevante.forEach((K,V) -> {
				if(V>1) {
					//System.out.println(V+" -> "+K);
				}
			});
			
			System.out.println(ff.getName());
			
			System.out.println(irrelevante.size()); //Número de líneas en el documento
			
			for(Modulo mod : modulos) {
				System.out.print(mod);
			}
			/*
			for(int i = 0; i<paginas; i++) {
				PDFTextStripper reader = new PDFTextStripper();
	            reader.setStartPage(i);
	            reader.setEndPage(i);
	            String pageText = reader.getText(document);
	            
	            String[] lineas = pageText.split("\n"); 
	            for(String linea : lineas) {

	            	System.out.println(linea);
	            	System.out.println("*");
	            }
	            
	            //System.out.println(pageText);
	            System.out.println("------++++++------");
			}
			 */
			
			document.close();
			
			FileWriter myWriter = new FileWriter(output);
			//myWriter.write(text);
			myWriter.close();
        	
        } catch (Exception e){
            e.printStackTrace();
        }
		
	

	}

}
