package h2;

public class Spielstein {
	
	private int currentRow;
	private int currentCol;
	private Spielbrett brett;
	
	//Getter-Setter
	
	public int getCurrentRow () {
		return currentRow;
	}
	public void setCurrentRow (int currentRow) {
		this.currentRow = currentRow;
	}
	public int getCurrentCol () {
		return currentCol;
	}
	public void setCurrentCol (int currentCol) {
		this.currentCol = currentCol;
	}

	public Spielbrett getBrett  () {
		return brett;
	}
	public void setBrett (Spielbrett brett) {
		this.brett = brett;
		
	}
	//Konstruktor
	
	public Spielstein(Spielbrett brett, int indexRow, int indexCol) {
		this.brett =brett;
		this.currentRow=indexRow;
		this.currentCol=indexCol;
	}
	// methode movesOut
	private boolean movesOut () {
		char direction = brett.getBrett()[currentRow][currentCol].getDirection();
		int dim = brett.getDim(); //wie hoch und breit ist das Brett
		
		//wenn ich schon ganz oben oder unten falle ich raus, oder L, R
		if (direction == 'U' && currentRow == 0) 
	        return true;
	    
	    if (direction == 'D' && currentRow == dim - 1) 
	        return true;
	    
	    if (direction == 'L' && currentCol == 0) 
	        return true;
	    
	    if (direction == 'R' && currentCol == dim - 1) 
	        return true;
	    

	    return false;
	}
	
	//Methode Go
	
	public void go (int n) {
		
		for (int step = 0; step < n; step++){
	
			Feld aktuellesFeld = brett.getBrett()[currentRow][currentCol]; 
			
			//Feld böse?
			if (aktuellesFeld.isBoese()) {
			continue;
			}
			//
			if (movesOut ()) {
				continue;
			}
			//dircetion raushollen
			char direction = aktuellesFeld.getDirection();

			
			//bewegung
			
			if (direction == 'U') {
				currentRow--;
	        } else if (direction == 'D') {
	            currentRow++;
	        } else if (direction == 'L') {
	            currentCol--;
	        } else if (direction == 'R') {
	            currentCol++;
	       
	            
			}
			
		}
	}
}

	
	
	

