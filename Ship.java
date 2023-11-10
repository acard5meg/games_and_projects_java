
public class Ship {

	private String name;
	private int size, hits;
	private boolean isSunk;
	private String nameAbbr;
	private String[] cells;
	
	public Ship(String name, int size, String nameAbbr) {
		this.name = name;
		this.size = size;
		this.hits = 0;
		this.isSunk = false;
		this.nameAbbr = nameAbbr;
		this.cells = new String[size];
	}
	
	public Ship() {
		this("None",0,"N");
	}
	
	public void setCells(String cell, int position) {
		cells[position] = cell;
	}
	
	public String[] getCells() { 
		return cells;
	}
	
	public void shipHit() {
		hits++;
		if (hits == size)
			isSunk = true;
	}
	
	public boolean shipSunk() {
		return isSunk;
	}
	
	public String getAbbr() {
		return nameAbbr;
	}
	
	public String getName() {
		return name;
	}
}
