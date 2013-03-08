package org.example.asteroides.activity;

import org.example.asteroides.R;
import org.example.asteroides.R.xml;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferencias extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferencias);

	}

}
