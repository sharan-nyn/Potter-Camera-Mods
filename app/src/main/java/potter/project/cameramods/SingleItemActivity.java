package potter.project.cameramods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SingleItemActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    int extraId;
    private TextView itemDesc,itemTitle,itemAuthor,itemDate,itemDownloads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        extraId = getIntent().getIntExtra("EXTRA_ID",0);
        itemDesc = findViewById(R.id.description_s);
        itemTitle = findViewById(R.id.title_s);
        itemAuthor = findViewById(R.id.author);
        itemDate = findViewById(R.id.update_time);
        itemDownloads = findViewById(R.id.downloads);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }
        private void jsonParse(){
            String url = "http://pottercameramods.000webhostapp.com/server.php?t=details&id="+extraId;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject mods = response.getJSONObject("data");
                        itemTitle.setText(mods.getString("name"));
                        itemDesc.setText(mods.getString("description"));
                        itemAuthor.setText("Author: "+mods.getString("author"));
                        itemDate.setText("Last Update: "+mods.getString("udate"));
                        itemDownloads.setText("Downloads: "+mods.getString("downloads"));
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

