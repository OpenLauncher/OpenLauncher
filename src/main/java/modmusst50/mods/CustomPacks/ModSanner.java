package modmusst50.mods.CustomPacks;

import com.atlauncher.App;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ModSanner {

    public static ArrayList<IMod> customMods = new ArrayList<IMod>();

    public static void loadCustomMods() throws Exception {
        JSONParser parser = new JSONParser();

        JSONArray a = (JSONArray) parser.parse(new FileReader(new File(App.settings.getJSONDir(), "mods.json")));

        for (Object o : a) {
            JSONObject jsonObject = (JSONObject) o;

            final String name = (String) jsonObject.get("name");
            final Long id = (Long) jsonObject.get("id");
            final String fileName = (String) jsonObject.get("fileName");
            final String version = (String) jsonObject.get("version");
            IMod iMod = new IMod() {
                @Override
                public String name() {
                    return name;
                }

                @Override
                public int id() {
                    return id.intValue();
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


}
