package simulacijaUsisivac;

import java.awt.Graphics;

public abstract class Figura {
	private int x;
	private int y;
	static int r;
	Figura(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public abstract int getR();
	abstract void draw(Graphics g);
	
	int getDistance(Figura f) {
		return (int) Math.sqrt(Math.pow((this.x-f.x), 2)+Math.pow((this.y-f.y), 2));
	}
	boolean isOverlapped(Figura f) {
		return this.getDistance(f)<=this.getR()+f.getR();
	}
	boolean isContained(Figura f) {
		return this.getDistance(f)<=this.getR()-f.getR();
	}

}
