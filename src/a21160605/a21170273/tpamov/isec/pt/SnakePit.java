package a21160605.a21170273.tpamov.isec.pt;

import Jogo.Direcao;
import Jogo.MotorJogo;
import Jogo.SaveOptionsScore;
import Jogo.Scores;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SnakePit extends Activity implements SensorEventListener {
	private static final float SENSABILIDADE = 10;
	private Sensor orientation;
	private SensorManager manager;
	private MotorJogo jogo;
	private TextView lbPontos;
	private FrameLayout flareajogo;
	private AreaJogo aJogo;
	private SaveOptionsScore saveOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveOptions = (SaveOptionsScore) getIntent().getSerializableExtra(
				"opcoes");
		setContentView(R.layout.activity_jogo);
		flareajogo = (FrameLayout) findViewById(R.id.AreaJogo);

		lbPontos = (TextView) findViewById(R.id.Labpontos);
		
		aJogo = new AreaJogo(this);

		flareajogo.addView(aJogo);

		
		setScore(0);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		jogo = new MotorJogo(aJogo, this, saveOptions.getVelocidadeCobra(),saveOptions.getMap(),saveOptions.isIsreverseSensors());
		// accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		orientation = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		aJogo.SetJogo(jogo);
	}
public int getMapa(){
	return 0;
}


	public void setScore(int score) {
		lbPontos.setText(Integer.toString(score));
	}

	@Override
	protected void onPause() {
		manager.unregisterListener(this);
		jogo.setPausa(true);
		super.onPause();
	}

	public void gameOver(String Pontos) {
		Intent intent = new Intent(this, ActivityGameOver.class);
		intent.putExtra("pontos", Pontos);
		startActivityForResult(intent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {

				Scores s = (Scores) data.getSerializableExtra("user");
				saveOptions.addScore(s);

				Intent intent = new Intent();
				intent.putExtra("returnScore", saveOptions);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.registerListener(this, orientation,
				SensorManager.SENSOR_DELAY_GAME);
		jogo.setPausa(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			if (Math.abs(event.values[1]) > Math.abs(event.values[2])) {
				if (event.values[1] < SENSABILIDADE) {
					jogo.setDirecao(Direcao.Este);
				} else if(event.values[1] > SENSABILIDADE) {
					jogo.setDirecao(Direcao.Oeste);
				}
			} else {
				if (event.values[2] > SENSABILIDADE) {
					jogo.setDirecao(Direcao.Sul);
				} else if (event.values[2] < SENSABILIDADE) {
					jogo.setDirecao(Direcao.Norte);
				}
			}
		}
	}
}
