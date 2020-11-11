import java.util.ArrayList;
import java.util.Arrays;

public class Semantico {
	private String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">", 
			"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if", "double"};		
	private String errores;
	private ArrayList<Simbolo> simbolos;
	private boolean bandera;
	private ArrayList<Token> tokens;
	
	public Semantico(ArrayList<Simbolo> simbolos, ArrayList<Token> tokens) {
		bandera=true;
		errores="";
		this.simbolos=simbolos;
		this.tokens=tokens;
		asignaciones();
		validarVariablesUsadas();
		validarDeclaraciones();
		validarOperandos();
		Main.consola.append(errores);
	}
	public void asignaciones() {//Historia de usuario 2
		for(Simbolo s:simbolos) {
			if(s.getTipo().equals("int") && !isNumeric(s.getValor()) && !s.getValor().equals("null")) {
				errores+="\nError semantico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta valores numericos";
				bandera=false;
			}
			
			if(s.getTipo().equals("boolean") && (!s.getValor().equals("true") && !s.getValor().equals("false"))) {
				errores+="\nError semantico: la variable "+ s.getNombre()+" de tipo "+s.getTipo()+" no puede tener valor de '"+ s.getValor()+"' este tipo solo acepta true o false";
				bandera=false;
			}
		}
	}
	public void validarVariablesUsadas() {//Historia de usuario 3
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
	public void validarOperandos() {//Historia de usuario 5
		Token aux;
		for (int i = 0; i < tokens.size(); i++) {
			if(isOperadorNumerico(tokens.get(i).getTipo())) {
				if(!(isVariableEntera(tokens.get(i-1).getToken()) || isNumeric(tokens.get(i-1).getToken())) || !(isVariableEntera(tokens.get(i+1).getToken()) || isNumeric(tokens.get(i+1).getToken()))) {
					errores+="\nSe encontro una expresion con operadores no aptos al contexto de tipos de datos usados en la posición "+tokens.get(i).getRenglon();
					continue;
				}
			}

			if(tokens.get(i).getToken().equals("!")) {
				for (int j = 0; j < simbolos.size(); j++) {
					if(tokens.get(i+1).getToken().equals(simbolos.get(i).getNombre()) && !simbolos.get(i).getTipo().equals("boolean")) {
						errores+="\nEl operador ! de la posicion "+ tokens.get(i).getRenglon() +" solo puede usarse en expresion en expresiones booelanas";
						continue;
					}
				}
			}
			aux=tokens.get(i);
			if(aux.getToken().equals("==") || aux.getToken().equals("!=")) {
				if(!getTipo(tokens.get(i-1).getToken()).equals(getTipo(tokens.get(i+1).getToken()))) {
					errores+="\nSe encontro una comparaciones de valores no aptos al contexto de tipos de datos en la posicion "+aux.getRenglon()+" ambos valores deben ser del mismo tipo";
					continue;
				}
					
			}
		}
	}
	private String getTipo(String nombre) {
		for (int i = 0; i < simbolos.size(); i++) {
			if(nombre.equals(simbolos.get(i).getNombre()))
				return simbolos.get(i).getTipo();
		}
		if(isNumeric(nombre))
			return "int";
		return "";
	}
	private boolean isVariableEntera(String v) {
		if(!isVariable(v))
			return false;
		for (int i = 0; i < simbolos.size(); i++) {
			if(v.equals(simbolos.get(i).getNombre()) && simbolos.get(i).getTipo().equals("int")) {
				return true;
			}
		}
		return false;
	}
	private boolean isOperadorNumerico(int tipo) {
		if(tipo==10 || tipo==11 || tipo==21 || tipo==23 || tipo==13 || tipo==22 || tipo==24)
			return true;
		return false;
	}
	private boolean isVariable(String v) {
		for (int i = 0; i < cadenas.length; i++) {
			if(v.equals(cadenas[i]))
				return false;
		}
		if(isNumeric(v) || v.equals("true") || v.equals("false"))
			return false;
		return true;
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
