package h2;

//objekt attribute
public class Spielbrett {
	private Feld [] [] brett;
	private int dim;
	
	
	
	//setter, getter
	public Feld [] [] getBrett () {
		return brett;
	}
	public void setBrett(Feld [][] brett ) {
		this.brett=brett;
	}
	
	public int getDim() {
	    return dim;
	}

	public void setDim(int dim) {
	    this.dim = dim;
	}

	//konstrukto für Spielbrett
	
	public Spielbrett (int dim) {
		this.dim = dim;
		this.brett = new Feld [dim][dim];//leeres Spielfeld mit dimxdidm
		
				
		}
	}


	



