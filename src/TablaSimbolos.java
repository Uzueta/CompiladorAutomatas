import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class TablaSimbolos extends JFrame{
	String cadenas[] = {"class", "public", "private", "while","int","boolean","{","}", "=", ";","<", ">", 
			"==", "<=", ">=", "!", "!=","true","false", "(",")", "/", "+", "-", "*", "if"};		
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
	public void setTokens(ArrayList<Token> tokens) {
		this.tokens=tokens;
	}
	public void generarTabla() {
		tabla=new JTable(modelo);
		Token aux;
		Simbolo s;
		String tipo=null, nombre=null, valor=null, pos=null;
		for (int i = 0; i < tokens.size(); i++) {
			aux=tokens.get(i);
			if(aux.getTipo()==0 || aux.getTipo()==4 || aux.getTipo()==5) {
				tipo=cadenas[aux.getTipo()];
				nombre=tokens.get(i+1).getToken();
				if(tokens.get(i+2).getTipo()==8)
					valor=tokens.get(i+3).getToken();
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
	public ArrayList<Simbolo> getSimbolos() {
		return simbolos;
	}
	public void mostrar() {
		this.setVisible(true);		
	}
}
