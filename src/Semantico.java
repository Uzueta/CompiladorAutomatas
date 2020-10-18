import java.util.ArrayList;

public class Semantico {
	private String errores;
	ArrayList<Simbolo> simbolos;
	private boolean bandera;
	
	public Semantico(ArrayList<Simbolo> simbolos) {
		bandera=true;
		errores="";
		this.simbolos=simbolos;
		asignaciones();
		validarDeclaraciones();
		Main.consola.append(errores);
	}
	public void asignaciones() {//Historia de usuario 2
		for(Simbolo s:simbolos) {
			if(s.getTipo().equals("int") && !isNumeric(s.getValor())) {
				errores+="\nError semantico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta valores numericos";
				bandera=false;
			}
			
			if(s.getTipo().equals("boolean") && (s.getValor()!="true" && s.getValor()!="false")) {
				errores+="\nError semantico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta true o false";
				bandera=false;
			}
		}
	}
	public void validarVariablesUsadas() {//Historia de usuario 3
		
	}
	public void validarDeclaraciones() { //Historia de usuario 4 
		for (int i = 0; i < simbolos.size(); i++) {
			if(simbolos.get(i).getTipo().equals("int") || simbolos.get(i).getTipo().equals("boolean")) {
				for (int j = i+1; j < simbolos.size(); j++) {
					if(simbolos.get(i).getNombre().equals(simbolos.get(j).getNombre())) {
						errores+="\nLa variable '"+simbolos.get(i).getNombre()+"' ya ha sido declarada en la linea "+simbolos.get(i).getFila()+" y se esta intentando declarar de nuevo en la linea "+simbolos.get(j).getFila();
					}
				}
			}
		}
	}
	private boolean isNumeric(String str) {
		  try {  
			    Double.parseDouble(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
	}
	public String getErrores() {
		return errores;
	}
	public boolean getBandera() {
		return bandera;
	}
}
