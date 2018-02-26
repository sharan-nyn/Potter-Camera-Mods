package potter.project.cameramods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    ModsAdapter modsAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonparse = findViewById(R.id.button_parse);

        modsAdapter = new ModsAdapter(this,R.layout.row_layout);
        listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(modsAdapter);

        mQueue = Volley.newRequestQueue(this);

        buttonparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){

        String url = "http://61.3.120.244/server.php?t=list";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject mod = jsonArray.getJSONObject(i);
                        String name = mod.getString("name");
                        String desc = mod.getString("description");
                        int id = mod.getInt("id");
                        Mods mods = new Mods(name,desc);
                        modsAdapter.add(mods);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
