
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.*;
import javax.swing.JOptionPane;

public class Analiza
{		
    private int renglon = 1,columna = 1, col2 = 0, cont = 0,  contador = -1;
    private int retEQ = 0, retMayEQ = 0, retMenEQ = 0, retDif = 0;
    boolean bandera = true;
    private TablaSimbolos tabla;
    private ArrayList<String> resultado = new ArrayList<String>(); 
	private ArrayList<Token> tokenRC = new ArrayList<Token>();
	public Analiza(String ruta) 
	{
		analizaCodigo(ruta);
		if(bandera)
			resultado.add("No hay errores lexicos"); 
	}
	public void analizaCodigo(String ruta)
	{	
		String linea = "", token = "";
		StringTokenizer tokenizer;
		try{
			FileReader file = new FileReader(ruta);
			BufferedReader archivoEntrada = new BufferedReader(file);
			linea = archivoEntrada.readLine();
			
			while (linea != null){
				columna = 0;
				linea = espacios(linea);
				tokenizer = new StringTokenizer(linea);
				while(tokenizer.hasMoreTokens()) //DENTRO DE ESTE WHILE, EN EL METODO analizadorLexico SE MANDA CADA TOKEN, CADA PALABRA
				{				
					columna++;
					token = tokenizer.nextToken();
					analizadorLexico(token);
				}
				linea=archivoEntrada.readLine();
				renglon++;
			}
			archivoEntrada.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,"No se encontro el archivo favor de checar la ruta","Alerta",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void analizadorLexico(String token) {
		token = Junta(token);
		if(token.equals("==") || token.equals(">=") || token.equals("<=") || token.equals("!="))
		{
			return;
		}
		String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">",   //12... Aunque no se usa como tal el "!" solo, sirve para que no lance error
							"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if", "double"};		//14   total = 26, de 0 al 25 + nums e id --> 0 - 27
		int tipo = -1;
		for (int i = 0; i < cadenas.length; i++) 
		{
			if(token.equals(cadenas[i]))
				tipo = i;
		}
		if(token.matches("^[0-9]?$")) 
		{
			
			tipo =50;
			
		}
		if(token.matches("^[0-9][0-9]?$")) {
			tipo =50;
		}
		if(token.matches("^[0-9][0-9][0-9]+?$")) {//error en numeros
			resultado.add("Error Lexico, se esperaba una longitud de 2 digitos en el numero \"" + token +"\" en la linea "+renglon+", No. de token "+columna+" ");
			tokenRC.add(new Token(token, renglon, columna, tipo));
			bandera = false;
			return;
		}
	
		if(tipo==-1) {
			Pattern pat = Pattern.compile("^[a-zA-Z]+[0-9]*+$");
			Matcher mat = pat.matcher(token);
			if(mat.find())
				tipo =52;
			else {
				resultado.add("Error Lexico en la linea \""+renglon+"\" No. de token \""+columna+"\" nombre del token \""+token+"\", algunos signos no se admiten");
				tokenRC.add(new Token(token, renglon, columna, tipo));
				bandera = false;
				return;
			}
		}
		tokenRC.add(new Token(token, renglon, columna, tipo));

	}
	public void crearTabla() {
		tabla=new TablaSimbolos();//
		tabla.setTokens(tokenRC);
		tabla.generarTabla();
	}
	public void mostrarTabla() {
		tabla.mostrar();
	}
	public void analizadorSemantico() {
		crearTabla();
		Semantico sem=new Semantico(tabla.getSimbolos(), tokenRC);
		resultado.add(sem.getErrores());
		bandera=sem.getBandera();
		
	}
	public String espacios(String linea){
		for (String cadena : Arrays.asList("(", ")", "{", "}", "=", ";", "*", "-", "+", "<", "/", ">", "!"))
		{
			if(linea.contains(cadena)) {
				linea = linea.replace(cadena, " "+cadena+" ");
			}
		}
		return linea;
	}
	public String Junta(String token) { //hicimos esto ya que se buggeaba cuando trataba de poner un token tipo != <= >= ==
		contador++;
	
		if(token.equals("<"))
			retMenEQ++;
		else if(token.equals(">"))
			retMayEQ++;
		else if(token.equals("!"))
			retDif++;
		else if(token.equals("="))
		{
			retEQ++;
			retDif++;
			retMayEQ++;
			retMenEQ++;

		}
		else {
			retEQ = 0;
			retDif = 0;
			retMayEQ = 0;
			retMenEQ = 0;
		}

		if(retEQ == 2 || retMenEQ == 2 || retMayEQ == 2 || retDif == 2)
		{
			tokenRC.remove(contador-1);
			
			if(retEQ == 2)
			{	
				tokenRC.add(new Token("==", renglon, columna, 11));
				token = "==";
			}
			else if(retMayEQ == 2)
			{	
				tokenRC.add(new Token(">=", renglon, columna, 12));
				token = ">=";
			}
			else if(retMenEQ == 2)
			{	
				tokenRC.add(new Token("<=", renglon, columna, 13));
				token = "<=";
			}
			else if(retDif == 2)
			{	
				tokenRC.add(new Token("!=", renglon, columna, 14));
				token = "!=";
			}
			contador--;
		}
		return token;
	}
	public ArrayList<String> getResultado() {
		return resultado;
	}
	public void setResultado(ArrayList<String> resultado) {
		this.resultado = resultado;
	}
	public ArrayList<Token> getTokenRC() {
		return tokenRC;
	}
	public void setTokenRC(ArrayList<Token> tokenRC) {
		this.tokenRC = tokenRC;
	}
}