package simulacijaUsisivac;

import java.awt.Color;
import java.awt.Graphics;

public class Usisivac extends Figura {
	Usisivac(int x, int y) {
		super(x, y);
	}

	int r=15;
	@Override
	public int getR() {
		return r;
	}
	public int getOffset() {
		return r/2;
	}

	@Override
	void draw(Graphics g) {
		g.setColor(Color.RED);
		int x1 = (int) (getX() - getR() * Math.cos(Math.PI / 6));
        int y1 = (int) (getY() + getR() * Math.sin(Math.PI / 6));
        int x2 = getX();
        int y2 = (int) (getY() - getR());
        int x3 = (int) (getX() + getR() * Math.cos(Math.PI / 6));
        int y3 = (int) (getY() + getR() * Math.sin(Math.PI / 6));

        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x2, y2, x3, y3);

        int[] pointsX = {x1, x2, x3};
        int[] pointsY = {y1, y2, y3};

        g.fillPolygon(pointsX, pointsY, 3);
	}

}
