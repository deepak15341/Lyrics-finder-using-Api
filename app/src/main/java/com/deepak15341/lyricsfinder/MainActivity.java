package com.deepak15341.lyricsfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    TextView textResult;
    EditText editTextArtist,editTextSong;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = findViewById(R.id.textResult);
        editTextArtist = findViewById(R.id.editTextArtist);
        editTextSong = findViewById(R.id.editTextSong);
        button = findViewById(R.id.button);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String LRYCS_URL ="https://deepak15341.000webhostapp.com/lyrics.json";
        /*StringRequest request =new StringRequest(Request.Method.GET, LRYCS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textTest.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
        */




    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LRYCS_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray songs = response.getJSONArray("Songs");
                        for (int i=0;i<songs.length();i++){
                            JSONObject song = (JSONObject) songs.get(i);
                            if (song.getString("Artist").equalsIgnoreCase(editTextArtist.getText().toString().trim())){
                                if (song.getString("Song_Name").equalsIgnoreCase(editTextSong.getText().toString().trim())){
                                    textResult.setText(song.getString("Lryics"));
                                    Log.i("responce", song.getString("Lryics"));
                                    break;
                                }


                            }
                            else{
                                textResult.setText("");
                                Toast.makeText(MainActivity.this, "No Results Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    });
    }
}