package potter.project.cameramods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static List<Mods> modsList;
    ListView lv;
    TextView err;
    Button refreshbutton;
    String url = "http://pottercameramods.000webhostapp.com/server.php?t=list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       err = findViewById(R.id.error);
       refreshbutton = findViewById(R.id.refresh);
       lv = findViewById(R.id.listView);
        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData(url);
            }
        });
       requestData(url);
       lv.setOnItemClickListener(this);
    }

    public void requestData(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                err.setVisibility(View.INVISIBLE);
                refreshbutton.setVisibility(View.INVISIBLE);
                modsList = ModsJSONParser.parseData(response);
                ModsAdapter adapter = new ModsAdapter(MainActivity.this,modsList);
                lv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                err.setVisibility(View.VISIBLE);
                refreshbutton.setVisibility(View.VISIBLE);
                err.setText("Error can't connect to online database.\n\nCheck internet connection. Click the refresh button to try again.\nWebsite could be sleeping. Try again in 1 hour");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Mods clickedMod = (Mods) adapterView.getItemAtPosition(i);
        int id = clickedMod.getId();
        Intent intent = new Intent(getBaseContext(),SingleItemActivity.class);
        intent.putExtra("EXTRTA_ID",id);
        Toast.makeText(MainActivity.this,
                "Clicked item:" + id,Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}
