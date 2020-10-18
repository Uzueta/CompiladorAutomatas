import java.util.ArrayList;
import java.util.Arrays;

public class Semantico {
	private String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">", 
			"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if"};		
	private String errores;
	ArrayList<Simbolo> simbolos;
	private boolean bandera;
	ArrayList<Token> tokens;
	
	public Semantico(ArrayList<Simbolo> simbolos, ArrayList<Token> tokens) {
		bandera=true;
		errores="";
		this.simbolos=simbolos;
		this.tokens=tokens;
		asignaciones();
<<<<<<< HEAD
		validarVariablesUsadas();
		validarDeclaraciones();
		validarOperandos();
=======
		validarDeclaraciones();
>>>>>>> 5873be354de22d0ee812ee8e997e6cac3b864a5a
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
<<<<<<< HEAD
		ArrayList<String> variables=new ArrayList<String>();
		for(Simbolo s:simbolos) 
			variables.add(s.getNombre());
		
		for (int i = 0; i < tokens.size(); i++) {
			if(isVariable(tokens.get(i).getToken())) {
				if(!variables.contains(tokens.get(i).getToken())) 
					errores+="\nError semantico: La variable '"+tokens.get(i).getToken()+"' en la posición "+tokens.get(i).getRenglon()+" no ha sido declarada";
			}
		}
	}
	public void validarDeclaraciones() {//Historia de usuario 4 
=======
		
	}
	public void validarDeclaraciones() { //Historia de usuario 4 
>>>>>>> 5873be354de22d0ee812ee8e997e6cac3b864a5a
		for (int i = 0; i < simbolos.size(); i++) {
			if(simbolos.get(i).getTipo().equals("int") || simbolos.get(i).getTipo().equals("boolean")) {
				for (int j = i+1; j < simbolos.size(); j++) {
					if(simbolos.get(i).getNombre().equals(simbolos.get(j).getNombre())) {
						errores+="\nLa variable '"+simbolos.get(i).getNombre()+"' ya ha sido declarada en la linea "+simbolos.get(i).getFila()+" y se esta intentando declarar de nuevo en la linea "+simbolos.get(j).getFila();
					}
				}
			}
		}
<<<<<<< HEAD
	}
	public void validarOperandos() {//Historia de usuario 5
		
	}
	private boolean isVariable(String v) {
		for (int i = 0; i < cadenas.length; i++) {
			System.out.println(v+"-----"+cadenas[i]);
			if(v.equals(cadenas[i]))
				return false;
		}
		if(isNumeric(v) || v.equals("true") || v.equals("false"))
			return false;
		return true;
=======
>>>>>>> 5873be354de22d0ee812ee8e997e6cac3b864a5a
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
