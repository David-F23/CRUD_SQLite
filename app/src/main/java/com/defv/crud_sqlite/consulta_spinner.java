package com.defv.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class consulta_spinner extends AppCompatActivity {

    private Spinner sp_options;
    private TextView tv_cod, tv_descripcion, tv_precio;

    ConexionSQlite conexion = new ConexionSQlite(this);
    Dto datos = new Dto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);

        sp_options = findViewById(R.id.sp_options);
        tv_cod = findViewById(R.id.tv_cod);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        tv_precio = findViewById(R.id.tv_precio);

        conexion.ConsultaListaArticulos();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conexion.obtenerListaArticulos());
        sp_options.setAdapter(adaptador);

        sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    tv_cod.setText("Código: " + conexion.ConsultaListaArticulos().get(position-1).getCodigo());
                    tv_descripcion.setText("Descripción: " + conexion.ConsultaListaArticulos().get(position-1).getDescripcion());
                    tv_precio.setText("Precio: " + conexion.ConsultaListaArticulos().get(position-1).getPrecio());
                }else{
                    tv_cod.setText("Código: ");
                    tv_descripcion.setText("Descripción: ");
                    tv_precio.setText("Precio: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}