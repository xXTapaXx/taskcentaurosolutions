package app.taskcentaurosolutionsapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import Adapters.RecyclerAdapterShared;
import controllers.ServiceController;
import views.SharedModelView;
import views.SharedView;
import views.TaskListView;

public class SharedActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    //Shared Preference
    SharedPreferences userDetails;
    //Account Name
    private static final String PREF_ACCOUNT_NAME = "accountName";
    //Gson
    Gson gson;

    ServiceController serviceController;
    List<String> listEmails;

    SharedView sharedView;
    TaskListView taskListView;

    //Recycler View
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EditText edit_text_shared;
    RecyclerAdapterShared adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializamos el edit text
        edit_text_shared = (EditText) findViewById(R.id.edit_text_shared);

        //Inicializamos el shared Preference
        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);

        //Instancia del Gson
        gson = new Gson();

        serviceController = new ServiceController();

        //Inicializamos el RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_shared_view);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Inicializamos la lista
        listEmails = new ArrayList<String>();

        //Inicializamos el customAdapter
        adapter = new RecyclerAdapterShared(this,listEmails);

        taskListView = (TaskListView) getIntent().getSerializableExtra("ListView");


        //Se activa el boton para regresar a la actividad anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_email);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddEmailShared();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //llamar a tu metodora guardar
        try {
            onSaveShared();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //llamar a tu metodora guardar
        try {
            onSaveShared();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.onSupportNavigateUp();
    }

    public void onAddEmailShared(){

        //Agregamos un item nuevo a listTaskView
        if (edit_text_shared.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
        {
            listEmails.add(new String(edit_text_shared.getText().toString()));
            adapter.notifyDataSetChanged();

            recyclerView.setAdapter(adapter);

            edit_text_shared.setText("");
        }
        else
        {
            Toast.makeText(SharedActivity.this, "Debe insertar un correo válido", Toast.LENGTH_SHORT).show();
        }

        // listView.setAdapter(adapter);

    }

    public void onSaveShared() throws UnsupportedEncodingException {
        if (edit_text_shared.getText().toString().isEmpty() && listEmails.size() > 0)
        {
            String myEmail = userDetails.getString(PREF_ACCOUNT_NAME, null);
            sharedView = new SharedView(myEmail,listEmails,taskListView);
            //onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

            //Convertimos la respuesta json a una lista de TaskListView
            String shared = gson.toJson(sharedView, SharedView.class);

            serviceController.stringRequest(getString(R.string.api_url)+"/insertShared?shared="+ URLEncoder.encode(shared,"UTF-8"), Request.Method.GET, null, this, this);

        }
        else
        {
            new AlertDialog.Builder(SharedActivity.this)
                    .setTitle("Compartir")
                    .setMessage("Estas seguro que desea Salir no se compartira la lista de tarea?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }
            ).show();
        }

        //insertShared?shared="+shared

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        finish();

    }
}
