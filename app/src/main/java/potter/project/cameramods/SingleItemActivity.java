package potter.project.cameramods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.List;

public class SingleItemActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private int extraId = getIntent().getIntExtra("EXTRTA_ID",1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }
        private void jsonParse(){
            String url = "http://pottercameramods.000webhostapp.com/server.php?t=details&id=1";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i = 0;i<jsonArray.length();i++)
                        {
                            JSONObject mods = jsonArray.getJSONObject(i);
                            int id = mods.getInt("id");
                            if(id == extraId ) {
                                String itemTitle = mods.getString("name");
                                String itemDesc = mods.getString("description");
                            }
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

