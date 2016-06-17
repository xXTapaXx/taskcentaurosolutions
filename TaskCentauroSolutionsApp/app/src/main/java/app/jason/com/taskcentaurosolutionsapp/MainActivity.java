package app.jason.com.taskcentaurosolutionsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import Adapters.CustomAdapterListTasks;
import controllers.ServiceController;
import views.TaskListView;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    ServiceController serviceController;
    GridView gridView;
    GridView clickGridView;
    CustomAdapterListTasks adaptador;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

        });


            //Inicializamos el ServiceController
        serviceController = new ServiceController();

        //Llamamos el metodo para realizar al request
        onRequest();

        //Inicializamos el GridView
        clickGridView = (GridView) findViewById(R.id.grid_view);

        //Agregamos accion de click item al gridView
        clickGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        //Codigo del boton Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Agregamos accion de click al boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowCreateTask();
            }
        });
    }

    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRequest();

            }
        }, 1000);
    }


    public void onRequest(){
        //Llamamos el request
        serviceController.jsonArrayRequest(getString(R.string.api_url)+"/taskslists", Request.Method.GET, null, this, this);
    }
    public void onShowCreateTask(){
        Intent intent = new Intent(getApplicationContext(),CreateTaskActivity.class);

        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            onRequest();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Response Listeners

    @Override
    public void onResponse(JSONArray response) {

        //Instancia del Gson
        Gson gson = new Gson();
        //Convertimos la respuesta json a una lista de TaskListView
        Type type = new TypeToken<List<TaskListView>>(){}.getType();
        List<TaskListView> listView = (List<TaskListView>) gson.fromJson(response.toString(),type);
        //Instancia del GridView
        gridView = (GridView)findViewById(R.id.grid_view);
        //Inicializar el adaptador con los datos de la respuesta
        adaptador = new CustomAdapterListTasks(this, listView);
        //Relacionando el GridView con el adaptador

        gridView.setAdapter(adaptador);

        Toast.makeText(getApplicationContext(),"Refrescando",Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);



    }

    //Error Response
    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyError errorPrueba = error;
    }
}
