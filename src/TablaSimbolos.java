import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class TablaSimbolos extends JFrame{
	String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">", 
			"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if", "double"};		
	JTable tabla;
	DefaultTableModel modelo;
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
			System.out.println(aux.getTipo());
			if(aux.getTipo()==0 || aux.getTipo()==4 || aux.getTipo()==5 || aux.getTipo()==26) {
				tipo=cadenas[aux.getTipo()];
				nombre=tokens.get(i+1).getToken();
				if(tokens.get(i+2).getTipo()==8)
					//					if(isExpresion(tokens, i))
					//						System.out.println("");
					//					else
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
		if(tokens.get(index+4).getTipo()==9)
			return false;
		index+=3;
		String expresion="";
		for (int i = index; i < tokens.size(); i++) {
			if(tokens.get(index).getToken().equals("int") || tokens.get(index).getToken().equals("boolean")) {
				Main.consola.append("Falta un ; en la linea "+tokens.get(index-1).getRenglon());
				return false;
			}
			if(tokens.get(index).getTipo()==9)
				break;
			expresion+=tokens.get(index).getToken();
		}


		return true;
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
