import java.util.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CodigoIntermedio {
	private String cuadruplo;
	private ArrayList<String> auxExp;
	private ArrayList<String> expresion;
	private ArrayList<Double>UZ=new ArrayList<Double>();
	public CodigoIntermedio(int cont, ArrayList<String> expresion, String cuadruplo) {
		auxExp=new ArrayList<String>();
		this.expresion=expresion;
		this.cuadruplo="----------------------------------------------------------------------------------\nCuadruplo "+(cont)+"\n"+cuadruplo+"\nOperador\tOperando 1\tOperando2\tResultado";
		while(expresion.contains("(") || expresion.contains(")"))
			checarParentesis(0, expresion.size());
		checarJerarquia(0, expresion.size());
		this.cuadruplo=this.cuadruplo+"\n=\tUZ"+(UZ.size())+"\t\t"+cuadruplo.substring(0,1);
		this.cuadruplo=this.cuadruplo+"\n"+cuadruplo.substring(0,2)+UZ.get(UZ.size()-1);
		Main.consola.append(this.cuadruplo+"\n");
	}

	private boolean checarParentesis(int prim, int ult) {
		int inicio=-1, fin=-1;
		for (int i = prim; i < ult; i++) {
			if(expresion.get(i).equals("("))
				inicio=i;
			if(expresion.get(i).equals(")"))
				fin=i;
		}
		if(inicio==-1 || fin==-1) {
			checarJerarquia(prim, ult);
			return false;
		}
		checarParentesis(inicio, fin);
		return true;
	}


	private void checarJerarquia(int inicio, int fin) {
		String num1, num2;
				System.out.println(inicio+" inicio y fin "+fin);
		for (int i = inicio; i < fin; i++) {
			if(expresion.get(i).equals("/") || expresion.get(i).equals("*")) {
				num1=expresion.get(i-1);
				num2=expresion.get(i+1);
				System.out.println(num1+"------"+expresion.get(i)+"------"+num2);
				hacerOperacion(num1, expresion.get(i), num2);
				if(!((i+2)>=expresion.size()) && expresion.get(i+2).equals(")"))
						expresion.remove(i+2);
				expresion.remove(i+1);
				expresion.remove(i);
				expresion.set(i-1, "UZ"+UZ.size());
				if(!((i-2)<=0) && expresion.get(i-2).equals("("))
					expresion.remove(i-2);
				//i=4
				//0		1	2	3	4	5	6	7	8	9	10	11	12	13	14
				//2		*	(	3	/	4	*	U1	)	*	7;
				
				
				for (int j = 0; j < expresion.size(); j++) {
					System.out.print(expresion.get(j)+"\t");
				}
				if(!expresion.contains("(") || !expresion.contains(")"))
					i=0;
				fin=expresion.size()-1;
				continue;
			}
			if(expresion.get(i).equals("+") || expresion.get(i).equals("-")) {
				num1=expresion.get(i-1);
				num2=expresion.get(i+1);
				System.out.println(num1+"------"+expresion.get(i)+"------"+num2);
				hacerOperacion(num1, expresion.get(i), num2);
				if(!((i+2)>=expresion.size()) && expresion.get(i+2).equals(")"))
						expresion.remove(i+2);
				expresion.remove(i+1);
				expresion.remove(i);
				expresion.set(i-1, "UZ"+UZ.size());
				if(!((i-2)<=0) && expresion.get(i-2).equals("("))
					expresion.remove(i-2);
				
				for (int j = 0; j < expresion.size(); j++) {
					System.out.print(expresion.get(j)+"\t");
				}
				if(!expresion.contains("(") || !expresion.contains(")"))
					i=0;
				fin=expresion.size()-1;
				continue;
			}
		}
	}
	private void hacerOperacion(String num1, String op, String num2) {
		String num1o, num2o;
		num1o=num1;
		num2o=num2;
		if(num1.contains("UZ"))
			num1=UZ.get(Integer.parseInt(num1.substring(2, 3))-1)+"";
		if(num2.contains("UZ"))
			num2=UZ.get(Integer.parseInt(num2.substring(2, 3))-1)+"";
		String exp=num1+op+num2;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine eng = mgr.getEngineByName("JavaScript");
		try {
			exp = eng.eval(exp)+"";
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		UZ.add(Double.parseDouble(exp));
		cuadruplo=cuadruplo+"\n"+op+"\t"+num1o+"\t"+num2o+"\tUZ"+UZ.size();
		System.out.println(cuadruplo);
	}
	private String concatenar(ArrayList<String> lista) {
		String cadena="";
		for (int i = 0; i < lista.size(); i++) {
			cadena+=lista.get(i);
		}
		return cadena;
	}
	private boolean isNumeric(String str) {
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	//	private void asingarFinal() {
	//		String variable=
	//	}

}
