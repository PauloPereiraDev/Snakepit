package a21160605.a21170273.tpamov.isec.pt;

import java.util.ArrayList;

import Jogo.Linha;
import Jogo.MotorJogo;
import Jogo.TileType;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class AreaJogo extends View implements GestureDetector.OnGestureListener {
	private static String pausa;
	private static String msgcomeca1;
	private static String msgcomeca2;
	private static String gameOver;

	int tamCelula = -1;
	int tamGrelha_x;
	int tamGrelha_y;

	TileType tiles[][];
	private MotorJogo jogo;
	private GestureDetector gd;
	private Paint paint;

	public AreaJogo(Context context) {
		super(context);
		pausa = context.getString(R.string.pause);
		msgcomeca1 = context.getString(R.string.msgprimanoecra);
		msgcomeca2 = context.getString(R.string.msgparacomecar);
		gameOver = context.getString(R.string.gameover);
		paint = new Paint(Paint.DITHER_FLAG);
		tiles = null;
		gd = new GestureDetector(context, this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gd != null)
			return gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		this.setMeasuredDimension(parentWidth, parentHeight);
	}

	public boolean onDown(MotionEvent arg0) {
		if (jogo.isGameOver()) {
			jogo.resetGame();
			return false;
		}
		if (!jogo.isNewGame()) {
			jogo.start();
		}
		if (jogo.isPausa())
			jogo.setPausa(false);
		else
			jogo.setPausa(true);
		return false;
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	public void onLongPress(MotionEvent arg0) {
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	public void onShowPress(MotionEvent arg0) {
	}

	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

	public void clearAreaJogo() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++)
				tiles[i][j] = null;
		}
	}

	public int getTamCelula() {
		return tamCelula;
	}

	public void setTamCelula(int tamCelula) {
		this.tamCelula = tamCelula;
	}

	public void setTile(Point p, TileType tipo) {
		setTile(p.x, p.y, tipo);
	}

	public void setTile(int x, int y, TileType tipo) {

		if (tiles == null) {
			tiles = new TileType[tamGrelha_x][tamGrelha_y];
		}
		tiles[x][y] = tipo;

	}

	public TileType getTitle(int x, int y) {
		if (tiles == null)
			return null;
		return tiles[x][y];
	}

	public void interage() {
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (jogo == null)
			return;
		if (jogo.getMapa() == null) {
			jogo.setMapa(getMeasuredWidth(), getMeasuredHeight());
			tamGrelha_x = jogo.getMapa().getTamanho_grelha_x();
			tamGrelha_y = jogo.getMapa().getTamanho_grelha_y();
			tamCelula = jogo.getMapa().getTamanho_celula();
			tiles = new TileType[tamGrelha_x][tamGrelha_y];
		}

		paint.setColor(Color.rgb(0, 0, 0));
		paint.setStyle(Paint.Style.FILL);
		ArrayList<Linha> linhas;

		// desenha linhas verticais

		for (int i = 0; i <= tamGrelha_x; i++) {
			linhas = jogo.getMapa().getLinhas_v(i);
			for (int j = 0; j < linhas.size(); j++) {
				if (linhas.get(j).isAtravessavel())
					paint.setColor(Color.BLUE);
				else
					paint.setColor(Color.BLACK);
				canvas.drawLine(i * tamCelula, linhas.get(j).getP1().y
						* tamCelula, i * tamCelula, linhas.get(j).getP2().y
						* tamCelula, paint);// Norte
			}
		}
		// desenha linhas horizontais
		for (int i = 0; i <= tamGrelha_y; i++) {
			linhas = jogo.getMapa().getLinhas_h(i);
			for (int j = 0; j < linhas.size(); j++) {
				if (linhas.get(j).isAtravessavel())
					paint.setColor(Color.BLUE);
				else
					paint.setColor(Color.BLACK);
				canvas.drawLine(linhas.get(j).getP1().x * tamCelula, i
						* tamCelula, linhas.get(j).getP2().x * tamCelula, i
						* tamCelula, paint);// Norte
			}
		}

		if (jogo.isNewGame() && !jogo.isGameOver()) {
			for (int i = 0; i < tamGrelha_x; i++) {
				for (int j = 0; j < tamGrelha_y; j++) {
					if (tiles[i][j] != null) {
						drawTile(i * tamCelula, j * tamCelula, tiles[i][j],
								canvas);
					}
				}
			}
		}

		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(24);
		paint.setColor(Color.BLUE);
		if (jogo.isGameOver()) {
			canvas.drawText(gameOver, (tamCelula * tamGrelha_x) / 2,
					(tamCelula * tamGrelha_y) / 2, paint);
			return;
		}

		if (jogo.isPausa()) {
			canvas.drawText(pausa, (tamCelula * tamGrelha_x) / 2,
					(tamCelula * tamGrelha_y) / 2, paint);
		}
		paint.setTextSize(18);
		if (!jogo.isNewGame()) {
			canvas.drawText(msgcomeca1, (tamCelula * tamGrelha_x) / 2,
					(tamCelula * tamGrelha_y) / 2 - 9, paint);
			canvas.drawText(msgcomeca2, (tamCelula * tamGrelha_x) / 2,
					(tamCelula * tamGrelha_y) / 2 + 9, paint);
		}

	}

	public boolean isFruta() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j] == TileType.Fruta)
					return true;
			}
		}
		return false;
	}

	private void drawTile(int x, int y, TileType tipo, Canvas canvas) {
		Paint paint = new Paint();
		Bitmap b;
		Bitmap resized;
		paint.setColor(Color.BLACK);
		switch (tipo) {
		case Cabeca:
			paint.setColor(Color.BLACK);
			b = BitmapFactory.decodeResource(getResources(), R.drawable.head);
			resized = Bitmap.createScaledBitmap(b, tamCelula, tamCelula, true);
			canvas.drawBitmap(resized, x, y, paint);
			break;
		case Corpo:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.body);
			resized = Bitmap.createScaledBitmap(b, tamCelula, tamCelula, true);
			canvas.drawBitmap(resized, x, y, paint);
			break;
		case Cauda:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.tail);
			resized = Bitmap.createScaledBitmap(b, tamCelula, tamCelula, true);
			canvas.drawBitmap(resized, x, y, paint);
			// Matrix mat = new Matrix();
			// mat.postRotate(jogo.getAnguloCauda());
			// b = Bitmap.createBitmap(resized, 0,
			// 0,resized.getWidth(),resized.getHeight(), mat, true);
			break;
		case Fruta:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
			resized = Bitmap.createScaledBitmap(b, tamCelula, tamCelula, true);
			canvas.drawBitmap(resized, x, y, paint);
			break;
		case Bonus:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.bonus);
			resized = Bitmap.createScaledBitmap(b, tamCelula, tamCelula, true);
			canvas.drawBitmap(resized, x, y, paint);
			break;
		}

	}

	public void SetJogo(MotorJogo jogo) {
		this.jogo = jogo;

	}
}
