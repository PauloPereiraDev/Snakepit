package Jogo;

import java.util.ArrayList;

import android.graphics.Point;

public class Map {
	public final int TAMANHO_CELULA = 12;
	//public final int NUM_MIN_CELULAS = 20;
	public final int TAM_MIN_CELULA = 15;

	// private int largura_ecra;
	// private int altura_ecra;
	private int tamanho_celula;
	private int tamanho_grelha_x;
	private int tamanho_grelha_y;
	private ArrayList<Linha> linhas_v;
	private ArrayList<Linha> linhas_h;
	private Point startPoint;

	public Map(int largura_ecra, int altura_ecra, int tipo) {

		linhas_h = new ArrayList<Linha>();
		linhas_v = new ArrayList<Linha>();

		tamanho_grelha_x = (largura_ecra-1) / TAMANHO_CELULA;
		tamanho_grelha_y = (altura_ecra-1) / TAMANHO_CELULA;
		tamanho_celula = TAMANHO_CELULA;

		switch (tipo) {// selecciona o mapa
		case 0:
			classic();
			break;
		case 1:
			nowalls();
			break;
		case 2:
			cross();
			break;
		case 3:
			maze4();
			break;
		}
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public ArrayList<Linha> getLinhas_v(int index) {
		ArrayList<Linha> auxLinhas = new ArrayList<Linha>();
		for (int i = 0; i < linhas_v.size(); i++) {
			if (linhas_v.get(i).getP1().x == index)
				auxLinhas.add(linhas_v.get(i));
		}
		return auxLinhas;
	}

	public ArrayList<Linha> getLinhas_h(int index) {
		ArrayList<Linha> auxLinhas = new ArrayList<Linha>();
		for (int i = 0; i < linhas_h.size(); i++) {
			if (linhas_h.get(i).getP1().y == index)
				auxLinhas.add(linhas_h.get(i));
		}
		return auxLinhas;
	}

	public int getTamanho_celula() {
		return tamanho_celula;
	}

	public int getTamanho_grelha_x() {
		return tamanho_grelha_x;
	}

	public int getTamanho_grelha_y() {
		return tamanho_grelha_y;
	}

	public Point nextPoint(Point p1, Point p2) {
		ArrayList<Linha> auxLinhas;
		int buscalinha;
		if (p1.y == p2.y)// desloca-se na horizontal
		{
			buscalinha = p2.x;
			if (p1.x > p2.x)
				buscalinha += 1;
			auxLinhas = getLinhas_v(buscalinha);
			for (int i = 0; i < auxLinhas.size(); i++) {
				if (auxLinhas.get(i).getP1().y <= p1.y
						&& auxLinhas.get(i).getP2().y >= p1.y + 1) {
					if (auxLinhas.get(i).isAtravessavel()) {
						if (p2.x < 0)
							p2.x = tamanho_grelha_x - 1;
						else if (p2.x >= tamanho_grelha_x)
							p2.x = 0;
						return p2;
					} else
						return null;
				}
			}
		} else {
			buscalinha = p2.y;
			if (p1.y > p2.y)
				buscalinha += 1;
			auxLinhas = getLinhas_h(buscalinha);
			for (int i = 0; i < auxLinhas.size(); i++) {
				if (auxLinhas.get(i).getP1().x <= p1.x
						&& auxLinhas.get(i).getP2().x >= p1.x + 1) {

					if (auxLinhas.get(i).isAtravessavel()) {
						if (p2.y < 0)
							p2.y = tamanho_grelha_y - 1;
						else if (p2.y >= tamanho_grelha_y)
							p2.y = 0;
						return p2;
					} else
						return null;
				}
			}
		}

		return p2;
	}

	private void classic() {
		linhas_v.add(new Linha(new Point(0, 0), new Point(0, tamanho_grelha_y),
				false));
		linhas_v.add(new Linha(new Point(tamanho_grelha_x, 0), new Point(
				tamanho_grelha_x, tamanho_grelha_y), false));
		linhas_h.add(new Linha(new Point(0, 0), new Point(tamanho_grelha_x, 0),
				false));
		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y), new Point(
				tamanho_grelha_x, tamanho_grelha_y), false));
		
		startPoint = new Point(tamanho_grelha_x / 2, tamanho_grelha_y / 2);
	}

	private void nowalls() {
		linhas_v.add(new Linha(new Point(0, 0), new Point(0, tamanho_grelha_y),
				true));
		linhas_v.add(new Linha(new Point(tamanho_grelha_x, 0), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));
		linhas_h.add(new Linha(new Point(0, 0), new Point(tamanho_grelha_x, 0),
				true));
		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));
		startPoint = new Point(tamanho_grelha_x / 2, tamanho_grelha_y / 2);
	}

	private void cross() {
		linhas_v.add(new Linha(new Point(0, 0), new Point(0, tamanho_grelha_y),
				true));
		linhas_v.add(new Linha(new Point(tamanho_grelha_x, 0), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));

		linhas_v.add(new Linha(new Point(tamanho_grelha_x / 2, 0), new Point(
				tamanho_grelha_x / 2, tamanho_grelha_y), false));

		linhas_h.add(new Linha(new Point(0, 0), new Point(tamanho_grelha_x, 0),
				true));
		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));

		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y / 2), new Point(
				tamanho_grelha_x, tamanho_grelha_y / 2), false));

		startPoint = new Point(tamanho_grelha_x / 4, tamanho_grelha_y / 4);
	}

	private void maze4() {
		linhas_v.add(new Linha(new Point(0, 0), new Point(0, tamanho_grelha_y),
				true));
		linhas_v.add(new Linha(new Point(tamanho_grelha_x, 0), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));

		linhas_v.add(new Linha(new Point(tamanho_grelha_x / 8 * 3, 0),
				new Point(tamanho_grelha_x / 8 * 3, tamanho_grelha_y / 2),
				false));

		linhas_v.add(new Linha(new Point(tamanho_grelha_x / 8 * 5,
				tamanho_grelha_y / 2), new Point(tamanho_grelha_x / 8 * 5,
				tamanho_grelha_y), false));

		linhas_h.add(new Linha(new Point(0, 0), new Point(tamanho_grelha_x, 0),
				true));
		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y), new Point(
				tamanho_grelha_x, tamanho_grelha_y), true));

		linhas_h.add(new Linha(new Point(0, tamanho_grelha_y / 4 * 3),
				new Point(tamanho_grelha_x / 8 * 3, tamanho_grelha_y / 4 * 3),
				false));

		linhas_h.add(new Linha(new Point(tamanho_grelha_x / 8 * 5,
				tamanho_grelha_y / 4), new Point(tamanho_grelha_x,
				tamanho_grelha_y / 4), false));

		startPoint = new Point(tamanho_grelha_x / 2, tamanho_grelha_y / 2);

	}

}
