package com.defv.crud_sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

/*'setSupportActionBar(androidx.appcompat.widget.Toolbar)' in 'androidx.appcompat.app.AppCompatActivity'*/


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, Btn_consultar2, btn_eliminar, btn_actualizar;
    private TextView tv_resultado;

    boolean inputEt = false;
    boolean inputEd = false;
    boolean input1 = false;
    int resultadoinsert = 0;

    Modal ventanas = new Modal();
    ConexionSQlite conexion = new ConexionSQlite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_close)
                .setTitle("Warning")
                .setMessage("¿Desea salir?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite 2021");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.black));
        toolbar.setTitle("David E. Flores");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });

        et_codigo = findViewById(R.id.et_codigo);
        et_descripcion = findViewById(R.id.et_descripcion);
        et_precio = findViewById(R.id.et_precio);
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_consultar1 = findViewById(R.id.btn_consultar1);
        Btn_consultar2 = findViewById(R.id.btn_consultar2);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        btn_actualizar = findViewById(R.id.btn_actualizar);

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");

                if(senal.equals("1")){
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);
                }
            }

        }catch (Exception e){

        }
    }

    private void confirmacion(){
        String mensaje = "¿Desea salir?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.listaArticulos){

            Intent intent = new Intent(MainActivity.this, consulta_spinner.class);
            startActivity(intent);
            return true;

        }else if(id == R.id.listaArticulos2){

            Intent intent2 = new Intent(MainActivity.this, list_view_articulos.class);
            startActivity(intent2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardar(View view) {

        if(et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else{
            inputEt = true;
        }

        if(et_descripcion.getText().toString().length() == 0){
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        }else{
            inputEd = true;
        }

        if(et_precio.getText().toString().length() == 0){
            et_precio.setError("Campo obligatorio");
            input1 = false;
        }else{
            input1 = true;
        }

        if(inputEt && inputEd && input1){
            try{

                datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                datos.setDescripcion(et_descripcion.getText().toString());
                datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));

                if (conexion.InserTradicional(datos)){
                    Toast.makeText(this, "Registro Insertado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }else{
                    Toast.makeText(this, "Error. Ya existe un registro\n" +
                            " Código: " + et_codigo.getText().toString(), Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }

            }catch (Exception e){
                Toast.makeText(this, "Error. Ya existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void mensaje(String mensaje){
        Toast.makeText(this, ""+ mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos(){
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }

    public void consultaPorCodigo(View view) {

        if(et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else{
            inputEt = true;
        }

        if(inputEt){
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));

            if(conexion.consultaArticulos(datos)){
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+ datos.getPrecio());

            }else{
                Toast.makeText(this, "No existe el articulo con este código", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this, "Ingrese el código.", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultaPorDescripcion(View view) {

        if(et_descripcion.getText().toString().length() == 0){
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        }else{
            inputEd = true;
        }

        if (inputEd){
            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);

            if (conexion.consultarDescripcion(datos)){
                et_codigo.setText("" + datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText("" + datos.getPrecio());

            }else{
                Toast.makeText(this, "No existe ningun articulo con esta descripción", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this, "Ingrese la descripció del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view) {

        if(et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else{
            inputEt = true;
        }

        if(inputEt){
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));

            if(conexion.bajaCodido(MainActivity.this, datos)){
                mensaje("¿Eliminar?");
            }else{
                mensaje("No existe un articulo con este código");
            }
        }
    }

    public void modificacion(View view) {

        if(et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else{
            inputEt = true;
        }

        if(inputEt){
            String codigo = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());

            datos.setCodigo(Integer.parseInt(codigo));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);

            if (conexion.modificar(datos)){
                Toast.makeText(this, "Registro modificado correctamente!", Toast.LENGTH_SHORT).show();
            }else{
                mensaje("No existe el registro a modificar");
            }
        }
    }

}