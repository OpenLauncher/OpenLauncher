package openlauncher.utils;


import com.atlauncher.data.Constants;
import com.atlauncher.data.LauncherVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionUtils {

	//This loads the build number from file
	public static void loadVersionProperties() {
		Properties props = new Properties();
		InputStream propsIn = ClassLoader.getSystemClassLoader().getResourceAsStream("build.properties");
		if(propsIn != null) {
			try {
				props.load(propsIn);
				String version = props.getProperty("version.build");
				if(version.equals("@BUILD@")){
					System.out.println("You have not set the build number in the build.properties. - This can be left in dev env.");
				}else {
					int ver = Integer.parseInt(version);
					if(ver != 0){
						Constants.VERSION = new LauncherVersion(Constants.VERSION.getReserved(), Constants.VERSION.getMajor(), Constants.VERSION.getMinor(), Constants.VERSION.getRevision(), ver);
						System.out.println("Using build number of:"  + ver);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					propsIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
