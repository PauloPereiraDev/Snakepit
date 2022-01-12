package a21160605.a21170273.tpamov.isec.pt;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import Jogo.SaveOptionsScore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {
	SaveOptionsScore saveOptions;
	private static final String FILENAME = "opcoes.sav";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		saveOptions = loadOpcoes();
	}

	@Override
	protected void onDestroy() {
		SaveOpcoes(saveOptions);
		super.onDestroy();
	}

	private SaveOptionsScore loadOpcoes() {
		InputStream instream = null;
		SaveOptionsScore opcoes = new SaveOptionsScore();
		try {
			instream = openFileInput(FILENAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new SaveOptionsScore();
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(instream);

			try {
				opcoes = (SaveOptionsScore) ois.readObject();
				instream.close();
				return opcoes;
			} catch (ClassNotFoundException e) {
				android.util.Log.e("Class não encontrada:" + e.getMessage(),
						e.toString());
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			android.util.Log.e("Ficheiro Corrupto:" + e.getMessage(),
					e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			android.util.Log.e("IO exceptions:" + e.getMessage(), e.toString());
			e.printStackTrace();

		}
		return new SaveOptionsScore();
	}

	private void SaveOpcoes(SaveOptionsScore opcoes) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			ObjectOutput out = new ObjectOutputStream(baos);
			out.writeObject(opcoes);

			byte[] buf = baos.toByteArray();

			FileOutputStream fos = this.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fos.write(buf);
			fos.close();
		} catch (IOException ioe) {
		}
	}



	public void OnClickbtnNovoJogo(View v) {

		Intent intent = new Intent(this, SnakePit.class);
		intent.putExtra("opcoes", saveOptions);
		startActivityForResult(intent, 1);
	}

	public void OnClickbtnSair(View v) {
		finish();
	}

	public void OnClickbtnScore(View v) {
		Intent intent = new Intent(this, Pontuacoes.class);
		intent.putExtra("scores", saveOptions);
		startActivity(intent);
	}

	public void OnClickbtnOpcoes(View v) {
		Intent intent = new Intent(this, ActivityOpcoes.class);
		intent.putExtra("opcoes", saveOptions);
		startActivityForResult(intent, 143);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 143) {
			if (resultCode == RESULT_OK) {
				saveOptions = (SaveOptionsScore) data
						.getSerializableExtra("returnOpcoes");
			}
		}
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				saveOptions = (SaveOptionsScore) data
						.getSerializableExtra("returnScore");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void OnClickbtnCreditos(View v) {

		Intent intent = new Intent(this, Activity_creditos.class);
		// intent.putExtra("mynotes", nota);
		startActivity(intent);
	}
	public void OnClickbtnInstrucoes(View v) {
		Intent intent = new Intent(this, Instrucoes.class);
		startActivity(intent);
	}

}
