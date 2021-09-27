package com.defv.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.SearchView;
import android.widget.ListView;
//import android.widget.SearchView;

import java.util.ArrayList;

public class list_view_articulos extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayAdapter adaptador;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;

    String[] version = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo","GingerBread","HoneyComb","IceCream Sandwich","Jelly Bean","Kitkat","Lolipop","Marshmallow","Nought","Oreo"};

    ConexionSQlite conexion = new ConexionSQlite(this);
    Dto datos = new Dto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_articulos);

        listViewPersonas = findViewById(R.id.listviewPersonas);
        searchView = findViewById(R.id.searcView);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.ConsultaListaArticulos1());
        listViewPersonas.setAdapter(adaptador);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String text = s;
                adaptador.getFilter().filter(text);
                return false;
            }
        });

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String informacion = "Código: " + conexion.ConsultaListaArticulos().get(pos).getCodigo()+"\n";
                informacion += "Descripción: " + conexion.ConsultaListaArticulos().get(pos).getDescripcion()+"\n";
                informacion += "Precio: " + conexion.ConsultaListaArticulos().get(pos).getPrecio();

                Dto articulos = conexion.ConsultaListaArticulos().get(pos);
                Intent intent = new Intent(list_view_articulos.this, detalles_articulos.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("articulo", articulos);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}