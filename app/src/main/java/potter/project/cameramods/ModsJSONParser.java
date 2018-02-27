package potter.project.cameramods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharan on 2/26/2018.
 */

public class ModsJSONParser {
    static List<Mods> modsList;
    public static List<Mods> parseData(JSONObject content)
    {
        JSONArray mods_array = null;
        Mods mods = null;
        try {
            mods_array = content.getJSONArray("data");
            modsList = new ArrayList<>();
            for (int i = 0; i<mods_array.length();i++)
            {
                JSONObject obj = mods_array.getJSONObject(i);
                mods = new Mods();

                mods.setTitle(obj.getString("name"));
                mods.setDesc(obj.getString("description"));
                mods.setId(obj.getInt("id"));

                modsList.add(mods);
            }
            return modsList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
