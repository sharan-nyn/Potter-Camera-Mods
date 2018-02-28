package potter.project.cameramods;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;


public class SingleItemActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    Button downloadButton;
    private TextView itemDesc,itemTitle,itemAuthor,itemDate,itemDownloads;
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final static int REQUEST_CODE = 1010;
    ProgressDialog progressDialog;
    int modid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("downloading.....");
        progressDialog.setCancelable(false);
        downloadButton = findViewById(R.id.button_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {checkPerms();}
        });
        itemDesc = findViewById(R.id.description_s);
        itemTitle = findViewById(R.id.title_s);
        itemAuthor = findViewById(R.id.author);
        itemDate = findViewById(R.id.update_time);
        itemDownloads = findViewById(R.id.downloads);
        mQueue = Volley.newRequestQueue(this);
        modid = getIntent().getIntExtra("EXTRA_ID",0);
        jsonParse("http://pottercameramods.000webhostapp.com/server.php?t=details&id="+modid);
    }
    private void checkPerms() {
        if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        downloadFile();
    }
    private void downloadFile() {
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET,
                "http://pottercameramods.000webhostapp.com/server.php?t=download&id="+modid,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        progressDialog.dismiss();
                        // TODO handle the response
                        try {
                            if (response!=null) {
                                String name="Mod_"+modid+".zip";
                                File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/PotterCameraMods/");
                                dir.mkdirs();
                                File videoFile = new File(dir.getAbsoluteFile()+"/"+name);
                                FileOutputStream stream = new FileOutputStream(videoFile);
                                try {
                                    stream.write(response);
                                } finally {
                                    stream.close();
                                }
                                Toast.makeText(SingleItemActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("ERROR!!", "NOT DOWNLOADED");
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
        progressDialog.show();
    }

    private void jsonParse(String url){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject mods = response.getJSONObject("data");
                        itemTitle.setText(mods.getString("name"));
                        itemDesc.setText(mods.getString("description"));
                        itemAuthor.setText("Author: "+mods.getString("author"));
                        itemDate.setText("Last Updated: "+mods.getString("udate"));
                        itemDownloads.setText("Downloads: "+mods.getString("downloads"));
                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        findViewById(R.id.details).setVisibility(View.VISIBLE);
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

