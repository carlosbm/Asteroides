package org.example.asteroides.activity;

import org.example.asteroides.R;
import org.example.asteroides.R.id;
import org.example.asteroides.R.layout;
import org.example.asteroides.view.VistaJuego;

import android.app.Activity;
import android.os.Bundle;

public class Juego extends Activity {

	private VistaJuego vistaJuego;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);

		vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);
		vistaJuego.setPadre(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		vistaJuego.getThread().pausar();

		VistaJuego.getmSensorManager().unregisterListener(vistaJuego);

	}

	@Override
	protected void onResume() {
		super.onResume();
		vistaJuego.getThread().reanudar();
		vistaJuego.registrarSensorOrientacion();
	}

	@Override
	protected void onDestroy() {
		vistaJuego.getThread().detener();
		super.onDestroy();
	}

}