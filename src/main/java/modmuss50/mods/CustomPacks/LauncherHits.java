package modmuss50.mods.CustomPacks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class LauncherHits implements Callable {

	@Override
	public String call() throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL("http://rushmead.playat.ch/launcher/hits.php").openConnection();
		conn.connect();
		return "";
	}

}
