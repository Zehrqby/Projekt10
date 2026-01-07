package h2;

public class Feld {
//objektattribute
	private boolean boese;
	private char direction;
	
	//neues Feld Objekt erzeugen
	public Feld(boolean boese, char direction) {
        this.boese = boese;
        this.direction = direction;
    }

    public boolean isBoese() {
        return boese;
    }

    public void setBoese(boolean boese) {
        this.boese = boese;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}

