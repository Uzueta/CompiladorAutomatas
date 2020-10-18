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
	}
	public void asignaciones() {
		for(Simbolo s:simbolos) {
			if(s.getTipo().equals("int") && !isNumeric(s.getValor())) {
				errores+="\nError semántico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta valores numéricos";
				bandera=false;
			}
			
			if(s.getTipo().equals("boolean") && (s.getValor()!="true" && s.getValor()!="false")) {
				errores+="\nError semántico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta true o false";
				bandera=false;
			}
		}
		Main.consola.append(errores);
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
