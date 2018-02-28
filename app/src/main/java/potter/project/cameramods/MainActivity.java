package potter.project.cameramods;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    TextView err;
    SwipeRefreshLayout swipe;
    String url = "http://pottercameramods.000webhostapp.com/server.php?t=list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        err = findViewById(R.id.error);
        swipe = findViewById(R.id.swiperefresh);
        swipe.setEnabled(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(url);
            }
        });
        lv = findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        requestData(url);
    }

    public void requestData(String url) {
        err.setVisibility(View.INVISIBLE);
        lv.setVisibility(View.VISIBLE);
        swipe.setRefreshing(true);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ModsAdapter adapter = new ModsAdapter(MainActivity.this,response.getJSONArray("data"));
                    lv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                err.setVisibility(View.VISIBLE);
                lv.setVisibility(View.INVISIBLE);
                err.setText("Error can't connect to online database.\n\nCheck internet connection.\n Pull down to try again.\nWebsite could be sleeping. Try again in 1 hour");
                swipe.setRefreshing(false);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(getBaseContext(), SingleItemActivity.class);
            intent.putExtra("EXTRA_ID", ((JSONObject) adapterView.getItemAtPosition(i)).getInt("id"));
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
