package simulacijaUsisivac;

import java.awt.Color;
import java.awt.Graphics;

public class Kamencic extends Figura {
	public Kamencic(int x, int y) {
		super(x, y);
	}
	
	static int r=5;

	@Override
	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(getX()-getR(), getY()-getR(), 2*getR(), 2*getR());

	}
	public static int getP() {
		return r;
	}
	
	public int getR() {
		return r;
	}

}
