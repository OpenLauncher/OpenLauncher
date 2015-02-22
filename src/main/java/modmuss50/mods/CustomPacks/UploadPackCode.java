package modmuss50.mods.CustomPacks;

import com.atlauncher.App;
import com.atlauncher.data.Constants;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

/**
 * Created by Mark on 30/11/2014.
 */
public class UploadPackCode  implements Callable<String>{

    public void setCode(String code) {
        this.code = code;
    }

    public static String code;

        @Override
        public String call() throws Exception {
        String log = code;
        String urlParameters = "";
        urlParameters += "title=" + URLEncoder.encode(Constants.LAUNCHER_NAME + " - Log", "ISO-8859-1") + "&";
        urlParameters += "language=" + URLEncoder.encode("text", "ISO-8859-1") + "&";
        urlParameters += "private=" + URLEncoder.encode("1", "ISO-8859-1") + "&";
        urlParameters += "text=" + URLEncoder.encode(log, "ISO-8859-1") + "&";
        if(App.settings.getAccount() != null){
            urlParameters += "name=" + URLEncoder.encode(App.settings.getAccount().getMinecraftUsername(), "ISO-8859-1");
        } else {
            urlParameters += "name=" + URLEncoder.encode("*user not logged in*", "ISO-8859-1");
        }
        HttpURLConnection conn = (HttpURLConnection) new URL(Constants.PASTE_API_URL).openConnection();
        conn.setDoOutput(true);
        conn.connect();
        conn.getOutputStream().write(urlParameters.getBytes());
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        String line;
        StringBuilder builder = new StringBuilder();
        InputStream stream;
        try {
            stream = conn.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            stream = conn.getErrorStream();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

}
