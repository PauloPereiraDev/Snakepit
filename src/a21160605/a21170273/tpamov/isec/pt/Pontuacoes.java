package a21160605.a21170273.tpamov.isec.pt;

import java.util.ArrayList;
import java.util.HashMap;

import Jogo.SaveOptionsScore;
import Jogo.Scores;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Pontuacoes extends Activity {
	SaveOptionsScore saveoptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveoptions = (SaveOptionsScore) getIntent().getSerializableExtra(
				"scores");
		setContentView(R.layout.activity_pontuacoes);
		if (!saveoptions.getScores().isEmpty())
			lista();
	}


	ArrayList<HashMap<String, String>> tabela = null;

	void additem(String n, String nick, String pontos) {
		if (tabela == null)
			tabela = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> linha = new HashMap<String, String>();
		linha.put("posicao", n);
		linha.put("nick", nick);
		linha.put("pontuacao", pontos);
		tabela.add(linha);
	}

	public void lista() {

		ListView lv = (ListView) findViewById(R.id.lstMyList);

		// correr o array e fazer additem;
		ArrayList<Scores> scores = saveoptions.getScores();
		for (int i = 0; i < scores.size(); i++) {
			additem(Integer.toString(i + 1), scores.get(i).getUser(), scores
					.get(i).getScore());
		}
		SimpleAdapter sa = new SimpleAdapter(this, tabela,
				R.layout.activity_pontuacao_lay, new String[] { "posicao",
						"nick", "pontuacao" }, new int[] { R.id.posicao,
						R.id.nick, R.id.pontuacao });
		lv.setAdapter(sa);

	}
}
