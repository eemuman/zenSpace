package fi.tuni.tamk.tiko.tiko2a;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fi.tuni.tamk.tiko.tiko2a.zenSpace;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		config.useImmersiveMode = true;
		initialize(new zenSpace(), config);
	}
}
