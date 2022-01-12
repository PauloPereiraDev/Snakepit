package Jogo;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveOptionsScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Scores> scores;
	private int velocidade;
	private boolean isreverseSensors;
	private int map;

	public SaveOptionsScore() {
		velocidade = 0;
		scores = new ArrayList<Scores>();
		isreverseSensors=false;
		map=0;
	}

	public boolean isIsreverseSensors() {
		return isreverseSensors;
	}

	public void setIsreverseSensors(boolean isreverseSensors) {
		this.isreverseSensors = isreverseSensors;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public void apagaPosicoes(){
		scores.clear();
	}
	public ArrayList<Scores> getScores() {
		return scores;
	}

	public void addScore(Scores s) {
		boolean acrescenta = true;
		ArrayList<Scores> as = new ArrayList<Scores>();
		for (int i = 0; i < scores.size(); i++) {
			if (Integer.parseInt(scores.get(i).getScore()) < Integer.parseInt(s
					.getScore()) && acrescenta) {
				as.add(s);
				acrescenta = false;
			}
			as.add(scores.get(i));

		}
		if (acrescenta)
			as.add(s);
		scores.clear();
		scores.addAll(as);
		if (scores.size() > 10) {
			scores.remove(10);
		}
	}

	public int getVelocidade() {
		return velocidade;
	}

	public int getVelocidadeCobra() {

		switch (velocidade) {
		case 0:
			return 300;

		case 1:
			return 200;

		case 2:
			return 150;

		case 3:
			return 100;

		case 4:
			return 75;

		}
		return 300;
	}

	public void setVelocidade(int vel) {
		this.velocidade = vel;
	}

}
