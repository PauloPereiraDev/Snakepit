package Jogo;

import java.io.Serializable;

public class Scores implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	String score;

	public Scores() {
		User = "";
		score = "0";
	}

	public String getUser() {
		return User;
	}

	public String getScore() {
		return score;
	}

	public void putPontuacao(String User, String score) {
		this.User = User;
		this.score = score;
	}
}
