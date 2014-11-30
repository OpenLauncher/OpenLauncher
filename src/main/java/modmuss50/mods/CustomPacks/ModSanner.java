package modmuss50.mods.CustomPacks;

import com.atlauncher.App;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ModSanner {

    public static ArrayList<IMod> customMods = new ArrayList<IMod>();

    public static ArrayList<IRepo> repoVersions = new ArrayList<IRepo>();

    public static void loadCustomMods(String repo) throws Exception {
        customMods.clear();
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(new File(App.settings.getJSONDir(), "mods" + repo + ".json")));

        for (Object o : a) {
            JSONObject jsonObject = (JSONObject) o;

            final String name = (String) jsonObject.get("name");
            final String id = (String) jsonObject.get("id");
            final String fileName = (String) jsonObject.get("fileName");
            final String version = (String) jsonObject.get("version");
            IMod iMod = new IMod() {
                @Override
                public String name() {
                    return name;
                }

                @Override
                public String id() {
                    return id;
                }

                @Override
                public String fileName() {
                    return fileName;
                }

                @Override
                public String version() {
                    return version;
                }
            };
            customMods.add(iMod);
        }
    }

    //This downloads all of the repo versions and then saves that in an array list.
    public static void loadRepo() throws Exception {
        JSONParser parser = new JSONParser();

        JSONArray a = (JSONArray) parser.parse(new FileReader(new File(App.settings.getJSONDir(), "repo.json")));

        for (Object o : a) {
            JSONObject jsonObject = (JSONObject) o;

            final String version = (String) jsonObject.get("version");
            final String minecraftVersion = (String) jsonObject.get("minecraftVersion");
            final String forgeVersion = (String) jsonObject.get("forgeVersion");
            IRepo iRepo = new IRepo() {

                @Override
                public String version() {
                    return version;
                }

                @Override
                public String minecraftVersion() {
                    return minecraftVersion;
                }

                @Override
                public String forgeVersion() {
                    return forgeVersion;
                }
            };
            repoVersions.add(iRepo);
        }
    }

}
