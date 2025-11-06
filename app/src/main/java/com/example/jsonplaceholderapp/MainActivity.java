package com.example.jsonplaceholderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnRefrescar;
    private ArrayList<String> listaPosts = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private final String url = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Oculta la barra azul superior
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        // Vincular elementos
        listView = findViewById(R.id.listViewPosts);
        btnRefrescar = findViewById(R.id.btnRefrescar);

        // Configurar adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPosts);
        listView.setAdapter(adapter);

        // Cargar posts al iniciar
        cargarPosts();

        // Botón amarillo "Salvar" recarga los posts
        btnRefrescar.setOnClickListener(v -> cargarPosts());

        // Click en un post abre DetalleActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            int postId = ids.get(position);
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("id", postId);
            startActivity(intent);
        });
    }

    private void cargarPosts() {
        listaPosts.clear();
        ids.clear();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject post = response.getJSONObject(i);
                            int id = post.getInt("id");
                            String title = post.getString("title");
                            listaPosts.add("Item " + id + ": " + title);
                            ids.add(id);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}