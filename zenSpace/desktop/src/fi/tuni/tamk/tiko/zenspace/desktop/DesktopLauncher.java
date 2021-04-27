package fi.tuni.tamk.tiko.zenspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fi.tuni.tamk.tiko.zenspace.zenSpace;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 8;
		new LwjglApplication(new zenSpace(), config);
	}
}
