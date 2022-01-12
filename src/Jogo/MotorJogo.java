package Jogo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import a21160605.a21170273.tpamov.isec.pt.AreaJogo;
import a21160605.a21170273.tpamov.isec.pt.SnakePit;
import android.graphics.Point;

public class MotorJogo extends Thread {

	private static final int MIN_SNAKE_LENGTH = 5;
	private Random random;
	private boolean isNewGame;
	private boolean isGameOver;
	private boolean isSetDirection;
	private AreaJogo board;
	private boolean pausa;
	private ArrayList<Point> cobra;
	Direcao direcao;
	private int score;
	Timer time;
	protected SnakePit actividade;
	private long tempo;
	private Map mapa;
	private int tipo;
	private int countApples;
	private boolean isInverted;

	public MotorJogo(AreaJogo board, SnakePit snakepit, int velocidade,
			int tipo, boolean invert) {
		pausa = true;
		mapa = null;
		tempo = velocidade;
		time = null;
		this.tipo = tipo;
		this.actividade = snakepit;
		this.isInverted = invert;
		random = new Random();
		setupLongTimeout(tempo);
		direcao = Direcao.Norte;
		this.board = board;
		isNewGame = false;
		cobra = new ArrayList<Point>();
		isSetDirection = false;
		countApples = 0;
	}

	public Direcao getDirecao() {
		return direcao;
	}

	public Map getMapa() {
		return mapa;
	}

	public void setMapa(int largura, int altura) {
		mapa = new Map(largura, altura, tipo);
	}

	public void setDirecao(Direcao direc) {
		Direcao last = this.direcao;
		if (!isSetDirection)
			return;
		if (isInverted) {
			switch (direc) {
			case Norte:
				direc = Direcao.Este;
				break;
			case Este:
				direc = Direcao.Sul;
				break;
			case Oeste:
				direc = Direcao.Norte;
				break;
			case Sul:
				direc = Direcao.Oeste;
				break;
			}
		}
		switch (direc) {
		case Norte:
			if (last != Direcao.Sul && last != Direcao.Norte) {
				this.direcao = Direcao.Norte;
			}
			break;
		case Este:

			if (last != Direcao.Este && last != Direcao.Oeste) {
				this.direcao = Direcao.Este;
			}

			break;
		case Oeste:

			if (last != Direcao.Este && last != Direcao.Oeste) {
				this.direcao = Direcao.Oeste;
			}

			break;
		case Sul:

			if (last != Direcao.Sul && last != Direcao.Norte) {
				this.direcao = Direcao.Sul;
			}

			break;
		}
		isSetDirection = false;
	}

	synchronized void setupLongTimeout(long timeout) {
		if (time != null) {
			time.cancel();
			time = null;
		}
		if (time == null) {
			Timer time = new Timer();
			time.schedule(new TimerTask() {

				@Override
				public void run() {
					if (!pausa && isNewGame && !isGameOver) {
						updateGame();
					}
					board.interage();

				}
			}, 0, timeout);
		}
	}

	public boolean isPausa() {
		return pausa;
	}

	public void updatescore() {

		actividade.runOnUiThread(new Runnable() {
			// @Override
			public void run() {
				actividade.setScore(score);
			}
		});
	}

	public void setPausa(boolean pausa) {
		this.pausa = pausa;
	}

	@Override
	public void run() {
		super.run();
		startGame();

	}

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	private void startGame() {
		this.isNewGame = true;
		cobra = new ArrayList<Point>();
		// Point head = new Point(mapa.getTamanho_grelha_x() / 2,
		// mapa.getTamanho_grelha_y() / 2);
		Point head = new Point(mapa.getStartPoint());
		board.setTile(head, TileType.Cabeca);
		cobra.clear();

		cobra.add(head);
		this.random = new Random();
		direcao = Direcao.Norte;

		this.pausa = false;
		countApples = 0;
		spawnFruit();
	}

	private void updateGame() {
		/*
		 * Gets the type of tile that the head of the snake collided with. If
		 * the snake hit a wall, SnakeBody will be returned, as both conditions
		 * are handled identically.
		 */
		TileType collision = updateSnake();
		if (!board.isFruta())
			spawnFruit();
		if (collision == TileType.Fruta) {
			score += 10;
			updatescore();
			spawnFruit();
		} else if (collision == TileType.Bonus) {
			score += 50;
			updatescore();
		} else if (collision == TileType.Corpo) {
			isGameOver = true;
			isNewGame = false;
			pausa = true;
			actividade.gameOver(Integer.toString(score));
		}
	}

	private TileType updateSnake() {

		Point head = new Point(cobra.get(0));
		Point anterior = new Point(head);
		switch (direcao) {
		case Norte:
			head.y--;
			break;

		case Sul:
			head.y++;
			break;

		case Oeste:
			head.x--;
			break;

		case Este:
			head.x++;
			break;
		}
		isSetDirection = true;
		Point proximo = mapa.nextPoint(anterior, head);
		if (proximo == null)
			return TileType.Corpo;
		else
			head = proximo;

		TileType old = board.getTitle(head.x, head.y);

		if (old != TileType.Fruta && cobra.size() > MIN_SNAKE_LENGTH) {
			Point tail = cobra.remove(cobra.size() - 1);
			board.setTile(tail, null);
			// board.setTile(cobra.get(cobra.size() - 1), TileType.Cauda);
			old = board.getTitle(head.x, head.y);
		}

		if (old != TileType.Corpo) {
			board.setTile(cobra.get(0), TileType.Corpo);
			cobra.add(0, head);
			board.setTile(head, TileType.Cabeca);
		}

		return old;
	}

	public void resetGame() {
		this.score = 0;
		this.isNewGame = false;
		this.isGameOver = false;
		board.clearAreaJogo();
		countApples = 0;
		this.isNewGame = true;
		cobra = new ArrayList<Point>();
		Point head = new Point(mapa.getStartPoint());
		board.setTile(head, TileType.Cabeca);
		cobra.clear();
		cobra.add(head);
		this.random = new Random();
		direcao = Direcao.Norte;
		this.pausa = false;
		spawnFruit();
		updatescore();
	}

	private void spawnBonus() {
		int r = random.nextInt(mapa.getTamanho_grelha_x()
				* mapa.getTamanho_grelha_y() - cobra.size());
		int x = r % mapa.getTamanho_grelha_x();
		int y = r / mapa.getTamanho_grelha_x();
		TileType coiso = board.getTitle(x, y);

		if (coiso == null) {
			board.setTile(x, y, TileType.Bonus);
		}
	}

	private void spawnFruit() {
		if (mapa == null)
			return;
		int r = random.nextInt(mapa.getTamanho_grelha_x()
				* mapa.getTamanho_grelha_y() - cobra.size());
		int x = r % mapa.getTamanho_grelha_x();
		int y = r / mapa.getTamanho_grelha_x();
		TileType coiso = board.getTitle(x, y);
		if (countApples % 10 == 0 && countApples != 0) {
			spawnBonus();
		}
		if (coiso == null) {
			countApples++;
			board.setTile(x, y, TileType.Fruta);
			return;
		} else {
			spawnFruit();
		}
	}

	public void setvelocidade(int velocidadeCobra) {

	}

	public float getAnguloCauda() {
		if (cobra.size() < MIN_SNAKE_LENGTH)
			return 0;
		// Point cauda = cobra.get(cobra.size() - 1);
		// Point anteCauda = cobra.get(cobra.size() - 2);
		return 0;
	}
}
