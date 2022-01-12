package Jogo;

import android.graphics.Point;

public class Linha {
	private Point p1, p2;
	private boolean isAtravessavel;

	public Linha(Point p1, Point p2, boolean isAtravessavel) {
		this.p1 = p1;
		this.p2 = p2;
		this.isAtravessavel = isAtravessavel;
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public boolean isAtravessavel() {
		return isAtravessavel;
	}
	
}
