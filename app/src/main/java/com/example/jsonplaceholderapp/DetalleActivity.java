package com.example.jsonplaceholderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DetalleActivity extends AppCompatActivity {

    private TextView txtTitulo, txtCuerpo;
    private final String baseUrl = "https://jsonplaceholder.typicode.com/posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtCuerpo = findViewById(R.id.txtCuerpo);

        int id = getIntent().getIntExtra("id", 1);
        cargarPost(id);
    }

    private void cargarPost(int id) {
        String url = baseUrl + id;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        txtTitulo.setText(response.getString("title"));
                        txtCuerpo.setText(response.getString("body"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar el post", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error al obtener el post", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}