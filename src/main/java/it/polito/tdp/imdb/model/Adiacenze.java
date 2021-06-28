package it.polito.tdp.imdb.model;

public class Adiacenze implements Comparable<Adiacenze> {
	
	private Director d1;
	private Director d2;
	private int peso;
	public Adiacenze(Director d1, Director d2, int peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public Director getD2() {
		return d2;
	}
	public void setD2(Director d2) {
		this.d2 = d2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenze o) {
		// TODO Auto-generated method stub
		return -(this.getPeso()-o.getPeso());
	}
	@Override
	public String toString() {
		return  d2 + "# attori condivisi " + peso + "\n";
	}
	
	
	

}
