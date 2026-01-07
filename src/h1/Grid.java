package h1;

public class Grid {
	
	private Cell [ ][ ] gridArray;
	
	// setter und getter :
public Cell [ ][ ] getGridArray () {
	return gridArray;
}
public void setGridArray (Cell [ ][ ]gridArray) {
	this.gridArray= gridArray;
}


//Konstruktor 

public Grid(Cell [] cells, int gridRows, int gridCols) {
	//grid erzeugen
	gridArray = new Cell [gridRows][gridCols]; 
		// zellen erzeugen 
		for ( int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
			gridArray [i][j]= new Cell (i, j, false);
		}
	}
		
	// lebendig setzen
	if (cells!=null ) {
		for (Cell c : cells ) {
		 if (c ==null) continue;
		 
			int r = c.getIndexRow ();
		int col =c.getIndexCol();
		
		//liegt die zelle im grid ?
		if (r >= 0 && r < gridRows && col >= 0 && col < gridCols) {
            gridArray[r][col].setAlive(true);
		}
	}
		
}
	//Nachbarn initial zählen
	for (int i = 0; i < gridRows; i++) {
        for (int j = 0; j < gridCols; j++) {
            gridArray[i][j].countLivingNeighbors(gridArray);
            
        }
	}	
		
}
//neue runde berechnen
public void computeNextGen() {

    
    for (int i = 0; i < gridArray.length; i++) {
        for (int j = 0; j < gridArray[0].length; j++) {
            gridArray[i][j].countLivingNeighbors(gridArray);
        }
    }

    for (int i = 0; i < gridArray.length; i++) {
        for (int j = 0; j < gridArray[0].length; j++) {
            gridArray[i][j].setAlive( gridArray[i][j].isAliveNextGen());
        }
    }
    
    for (int i = 0; i < gridArray.length; i++) {
        for (int j = 0; j < gridArray[0].length; j++) {
            gridArray[i][j].countLivingNeighbors(gridArray);
        }
    }
}
//
public void computeGeneration(int n) {

    for (int i = 0; i < n; i++) {
        computeNextGen();
    	}
	}
}


