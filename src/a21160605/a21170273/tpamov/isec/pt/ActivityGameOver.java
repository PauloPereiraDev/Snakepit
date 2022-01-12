package a21160605.a21170273.tpamov.isec.pt;

import Jogo.Scores;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityGameOver extends Activity {
	String pontos;
	TextView tv;
	EditText et;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		pontos = getIntent().getStringExtra("pontos");
		tv = (TextView) findViewById(R.id.tvpontos);
		et = (EditText) findViewById(R.id.TbNick);
		tv.setText(pontos);
	}


	public void OnClickbtnSave(View v) {

		Scores s = new Scores();
		String nick = et.getText().toString();
		if (nick.length() == 0) {
			nick = getString(R.string.Anonimo);
		}
		s.putPontuacao(nick, pontos);
		Intent intent = new Intent();
		intent.putExtra("user", s);

		setResult(RESULT_OK, intent);
		finish();
	}
}
