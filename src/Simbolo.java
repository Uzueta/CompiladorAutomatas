
public class Simbolo {
	
	private String nombre;
	private String valor;
	private int columna;
	private String tipo;
	
	public Simbolo(String tipo, String nombre, String valor, int c) {
		this.nombre=nombre;
		columna = c;
		this.tipo = tipo;
		this.valor=valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
