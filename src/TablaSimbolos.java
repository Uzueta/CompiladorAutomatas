import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;


import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class TablaSimbolos extends JFrame{
	String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">", 
			"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if", "double"};		
	JTable tabla;
	DefaultTableModel modelo;
	private ArrayList <String> opExp;
	private String expresion;
	private ArrayList<Simbolo> simbolos=new ArrayList<Simbolo>();
	String [] columnas= {"Tipo", "Nombre", "Valor", "Posición"};
	private ArrayList<Token> tokens;
	public TablaSimbolos() {
		super("Tabla de Simbolos");
		setSize(500, 300);
		setLocationRelativeTo(null);
		modelo = new DefaultTableModel(columnas, 0);
		String datos[][]=new String[5][3];

	}
	public void generarTabla() {
		tabla=new JTable(modelo);
		Token aux;
		Simbolo s;
		String tipo=null, nombre=null, valor=null, pos=null;
		for (int i = 0; i < tokens.size(); i++) {
			aux=tokens.get(i);
			if(aux.getTipo()==0 || aux.getTipo()==4 || aux.getTipo()==5 || aux.getTipo()==26) {
				tipo=cadenas[aux.getTipo()];
				nombre=tokens.get(i+1).getToken();
				if(tokens.get(i+2).getTipo()==8)
					if(isExpresion(tokens, i+3))
						valor=calcular()+"";
					else
					valor=tokens.get(i+3).getToken();
				else
					if(tipo.equalsIgnoreCase("int") || tipo.equalsIgnoreCase("double"))
						valor="null";
					else
						valor=" ";
				pos=aux.getRenglon()+"";
			}
			else
				continue;
			s=new Simbolo(tipo, nombre, valor, Integer.parseInt(pos));
			simbolos.add(s);
			Object filas []= {tipo, nombre, valor, pos};
			modelo.addRow(filas);
		}
		JScrollPane panel=new JScrollPane(tabla);
		add(panel);
	}
	private boolean isExpresion(ArrayList<Token> tokens, int index) {
		opExp=new ArrayList<String>();
		if(tokens.get(index+1).getTipo()==9)
			return false;
		expresion="";
		for (int i = index; i < tokens.size(); i++) {
			if(tokens.get(i).getTipo()==9)
				break;
			expresion+=tokens.get(i).getToken();
			opExp.add(tokens.get(i).getToken());
		}
		
		return true;
	}
	private double calcular() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine eng = mgr.getEngineByName("JavaScript");
		String res="";
		try {
			res = eng.eval(expresion)+"";
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return Double.parseDouble(res);
	}
	private boolean isNumeric(String str) {
		  try {  
			    Double.parseDouble(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
	}
	public ArrayList<Simbolo> getSimbolos() {
		return simbolos;
	}
	public void mostrar() {
		this.setVisible(true);		
	}
	public ArrayList<Token> getTokens() {
		return tokens;
	}
	public void setTokens(ArrayList<Token> tokens) {
		this.tokens=tokens;
	}
}
