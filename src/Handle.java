import java.awt.Point;


public class Handle extends Point {
	public int type;
	public boolean highlight = false;
	
	public Handle(int type) {
		this.type = type;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
