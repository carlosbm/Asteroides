package org.example.asteroides.activity;

import org.example.asteroides.R;
import org.example.asteroides.dataStore.AlmacenPuntuaciones;
import org.example.asteroides.dataStore.AlmacenPuntuacionesArray;
import org.example.asteroides.dataStore.AlmacenPuntuacionesFicheroExterno;
import org.example.asteroides.dataStore.AlmacenPuntuacionesFicheroInterno;
import org.example.asteroides.dataStore.AlmacenPuntuacionesPreferencias;
import org.example.asteroides.dataStore.AlmacenPuntuacionesRecurso;
import org.example.asteroides.dataStore.AlmacenPuntuacionesSQLite;
import org.example.asteroides.dataStore.AlmacenPuntuacionesXML_SAX;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Asteroides extends Activity {

	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
	private static MediaPlayer mp;

	public void lanzarPuntuaciones(View view) {

		Intent i = new Intent(this, Puntuaciones.class);
		startActivityForResult(i, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1234 & resultCode == RESULT_OK & data != null) {
			int puntuacion = data.getExtras().getInt("puntuacion");
			String nombre = "Yo";
			// Mejor leerlo desde un Dialog o una nueva actividad
			// //AlertDialog.Builder
			almacen.guardarPuntuacion(puntuacion, nombre,
					System.currentTimeMillis());
			lanzarPuntuaciones(null);
		}
	}

	public void lanzarJuego(View view) {
		Intent i = new Intent(this, Juego.class);
		startActivityForResult(i, 1234);
		// Media player tarda un rato en parar. Asi que lo paramos antes.
		if (mp.isPlaying())
			mp.pause();
	}

	public void lanzarAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDe.class);

		startActivity(i);

	}

	public void lanzarPreferencias(View view) {

		Intent i = new Intent(this, Preferencias.class);

		startActivity(i);

	}

	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado) {
		super.onSaveInstanceState(estadoGuardado);
		if (mp != null) {
			int pos = mp.getCurrentPosition();
			estadoGuardado.putInt("posicion", pos);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado) {
		super.onRestoreInstanceState(estadoGuardado);
		if (estadoGuardado != null && mp != null) {
			int pos = estadoGuardado.getInt("posicion");
			mp.seekTo(pos);
		}
	}

	protected AlmacenPuntuaciones almacenPreferencias() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		AlmacenPuntuaciones almacen = null;
		String puntuaciones = preferences.getString("puntuaciones", "2");
		int puntuacionInt = Integer.parseInt(puntuaciones);
		switch (puntuacionInt) {
		case 0:
			almacen = new AlmacenPuntuacionesArray();
			break;
		case 1:
			almacen = new AlmacenPuntuacionesPreferencias(this);
			break;
		case 2:
			almacen = new AlmacenPuntuacionesFicheroInterno(this);
			break;
		case 3:
			almacen = new AlmacenPuntuacionesFicheroExterno(this);
			break;
		case 4:
			almacen = new AlmacenPuntuacionesRecurso(this);
			break;
		case 5:
			almacen = new AlmacenPuntuacionesXML_SAX(this);
			break;
		case 6:
			almacen = new AlmacenPuntuacionesSQLite(this);
			break;
		}
		return almacen;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// almacen = almacenPreferencias(); We better load it everytime activity
		// resumes
		setContentView(R.layout.main);
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

		mp = MediaPlayer.create(this, R.raw.audio);
		mp.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
		/** true -> el menú ya está visible */
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.acercaDe:
			lanzarAcercaDe(null);
			break;
		case R.id.config:
			lanzarPreferencias(null);
			break;
		}
		return true;
		/** true -> consumimos el item, no se propaga */
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		almacen = almacenPreferencias();
		// Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		mp.start();
	}

	@Override
	protected void onPause() {
		// Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();

	}

	@Override
	protected void onStop() {
		// Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		if (mp.isPlaying())
			mp.pause();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

}
