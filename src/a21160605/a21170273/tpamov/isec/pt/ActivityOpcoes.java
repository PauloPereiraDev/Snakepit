package a21160605.a21170273.tpamov.isec.pt;

import Jogo.SaveOptionsScore;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ActivityOpcoes extends Activity {
	private SeekBar seekbar;
	private TextView value;
	private SaveOptionsScore saveOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveOptions = (SaveOptionsScore) getIntent().getSerializableExtra(
				"opcoes");
		setContentView(R.layout.activity_opcoes);
		
		CheckBox cb=(CheckBox)findViewById(R.id.cbInverterSensores);
		cb.setChecked(saveOptions.isIsreverseSensors());		
		
		value = (TextView) findViewById(R.id.velocidade);
		seekbar = (SeekBar) findViewById(R.id.seekBar1);
		seekbar.setProgress(saveOptions.getVelocidade());
		value.setText(Integer.toString(seekbar.getProgress() + 1));
		try {
			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					value.setText(Integer.toString(progress + 1));
					saveOptions.setVelocidade(progress);
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}
			});
		} catch (Exception e) {

		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 10) {
			if (resultCode == RESULT_OK) {
				saveOptions.setMap(data.getIntExtra("tipoMapa", 0));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void OnClickbtnChangeMap(View v) {
		Intent intent = new Intent(this, ChoseMap.class);
		intent.putExtra("mapa", saveOptions.getMap());
		startActivityForResult(intent, 10);
	}

	public void OnClickbtnApagaScore(View v) {
		saveOptions.apagaPosicoes();
	}

	public void OnClickbtnSave(View v) {
		CheckBox cb=(CheckBox)findViewById(R.id.cbInverterSensores);
		saveOptions.setIsreverseSensors(cb.isChecked());
		Intent intent = new Intent();
		
		intent.putExtra("returnOpcoes", saveOptions);
		setResult(RESULT_OK, intent);
		finish();
	}

}
