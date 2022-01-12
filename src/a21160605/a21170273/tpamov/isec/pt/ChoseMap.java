package a21160605.a21170273.tpamov.isec.pt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

public class ChoseMap extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int mapa = getIntent().getIntExtra("mapa", 0);
		setContentView(R.layout.activity_chose_map);		
		RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroupMaps);
		rg.check(rg.getChildAt(mapa).getId());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_chose_map, menu);
		return true;
	}
	public void OnClickbtnChoseMap(View v) {
		RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroupMaps);
		int radioButtonID = rg.getCheckedRadioButtonId();
		View radioButton = rg.findViewById(radioButtonID);
		int idx = rg.indexOfChild(radioButton);
		
		Intent intent = new Intent();		
		intent.putExtra("tipoMapa", idx);
		setResult(RESULT_OK, intent);
		finish();
	}

}
