package app.taskcentaurosolutionsapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.tasks.TasksScopes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.RecyclerAdapter;
import controllers.ServiceController;
import utils.HelperCreateOrUpdateSharedTask;
import utils.HelperDeleteList;
import utils.HelperTask;
import views.SharedModelView;
import views.TaskListView;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    //Shared Preference
    SharedPreferences userDetails;
    private final static String TOKEN = "token";
    SwipeRefreshLayout swipeRefreshLayout;
    List<TaskListView> listView;
    List<SharedModelView> sharedModelView;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter adapter;
    //Gson
    Gson gson;
    String token;
    ServiceController serviceController;

    //Dialog Progress
    public ProgressDialog mProgress;

    GoogleAccountCredential mCredential;

    //Account Name
    private static final String PREF_ACCOUNT_NAME = "accountName";

    //Scopes
    private static final String[] SCOPES = { TasksScopes.TASKS_READONLY, TasksScopes.TASKS };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializamos el shared Preference
         userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);

        //Instancia del Gson
        gson = new Gson();
        sharedModelView = new ArrayList<SharedModelView>();
        serviceController = new ServiceController();

      /* swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

        });

        // Seteamos los colores que se usarán a lo largo de la animación
        swipeRefreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );*/

        //Inicializamos el Progress Dialog
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Cargando...");
        mProgress.setCancelable(false);
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        //Codigo del boton Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Agregamos accion de click al boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTask();
            }
        });

        //Se activa el boton para regresar a la actividad anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        
    }



    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  onRequest();

            }
        }, 1000);
    }

    public void onClickShow(int position){
        Intent intent = new Intent(getApplicationContext(),CreateActivity.class);
        intent.putExtra("ListView",listView.get(position));
        startActivityForResult(intent,1);
    }

    public void onRequest()  {
        String myEmail = userDetails.getString(PREF_ACCOUNT_NAME, null);

        try {
            serviceController.jsonArrayRequest(getString(R.string.api_url)+"/haveShared?email="+ URLEncoder.encode(myEmail,"UTF-8"), Request.Method.GET, null, this, this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void onGetAllTasks(){
        new HelperTask(mCredential,this,sharedModelView).execute();
    }

    public void onResponseAllTasks(List<TaskListView> response){
          listView = response;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(this,response);
        recyclerView.setAdapter(adapter);

        mProgress.hide();
        Toast.makeText(getApplicationContext(),"Se Agrego y/o actualizó la tarea", Toast.LENGTH_LONG).show();
    }

    public void onCreateTask(){
        Intent intent = new Intent(getApplicationContext(),CreateActivity.class);

        startActivityForResult(intent,1);
    }

    public void onDeleteList(int position){

        //new HelperDeleteList(mCredential,this,listView.get(position).getId()).execute();

        if(sharedModelView.size() > 0){
            for(SharedModelView shared : sharedModelView){

                if(listView.get(position).getId().equals(shared.getList_id())){
                    shared.setSync(0);
                    shared.getShared_list_id().setSync(0);
                    shared.getShared_task_id().setSync(0);
                }

            }

            Type type = new TypeToken<List<SharedModelView>>(){}.getType();
            String sharedRequest = gson.toJson(sharedModelView,type);

            try {
                // serviceController.jsonArrayRequest(getString(R.string.api_url)+"/updateSync?shared="+URLEncoder.encode(sharedRequest,"UTF-8"), Request.Method.GET, null, this, this);
                //serviceController.jsonArrayRequest(getString(R.string.api_url)+"/haveShared?email="+ URLEncoder.encode(prueba,"UTF-8"), Request.Method.GET, null, this, this);

                serviceController.jsonArrayRequest(getString(R.string.api_url)+"/updateDeleteList?shared="+ URLEncoder.encode(sharedRequest,"UTF-8"), Request.Method.POST, null, this, this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
        new HelperDeleteList(mCredential,this,listView.get(position).getId()).execute();
        listView.remove(position);
    }

    public void onShared(int position){
        Intent intent = new Intent(getApplicationContext(),SharedActivity.class);
        intent.putExtra("ListView",listView.get(position));
        startActivity(intent);
    }

    public void onSharedFinish(List<SharedModelView> response){

            Type type = new TypeToken<List<SharedModelView>>(){}.getType();
            String sharedRequest = gson.toJson(response,type);

        try {
            serviceController.jsonArrayRequest(getString(R.string.api_url)+"/updateSync?shared="+URLEncoder.encode(sharedRequest,"UTF-8"), Request.Method.POST, null, this, this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //serviceController.jsonArrayRequest(getString(R.string.api_url)+"/haveShared?email="+ URLEncoder.encode(prueba,"UTF-8"), Request.Method.GET, null, this, this);

        Map<String, String> map = new HashMap<>();

            map.put("shared", sharedRequest);

        //serviceController.jsonArrayRequest(getString(R.string.api_url)+"/updateSync?shared", Request.Method.POST, map, this, this);
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                onSignOut();
                return true;
            /*case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }

    public void onSignOut(){
        String email = userDetails.getString(PREF_ACCOUNT_NAME, null);
        String token = userDetails.getString(TOKEN, null);
        try {
            // serviceController.jsonArrayRequest(getString(R.string.api_url)+"/updateSync?shared="+URLEncoder.encode(sharedRequest,"UTF-8"), Request.Method.GET, null, this, this);
            //serviceController.jsonArrayRequest(getString(R.string.api_url)+"/haveShared?email="+ URLEncoder.encode(prueba,"UTF-8"), Request.Method.GET, null, this, this);

            serviceController.jsonArrayRequest(getString(R.string.api_url)+"/signOut?email="+ URLEncoder.encode(email,"UTF-8") + "&token="+ URLEncoder.encode(token,"UTF-8"), Request.Method.POST, null, this, this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


   /* //Response Listeners
    public void onResponse(List<TaskListView> response) {
        listView = response;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(this,response);
        recyclerView.setAdapter(adapter);

       // Toast.makeText(getApplicationContext(),"Refrescando",Toast.LENGTH_LONG).show();

    }*/

    @Override
    public void onStart(){
        super.onStart();

       String accountName = userDetails.getString(PREF_ACCOUNT_NAME, null);
            //String accountName = null;
        if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                mProgress.show();
                onRequest();

        } else {
            // Start a dialog from which the user can choose an account
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivityForResult(intent,2);
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray response) {


        try {
            if((response.length() > 0 && response != null) && !response.get(0).equals("Delete")){

                    if(response.get(0).equals("signOut")){
                        userDetails.edit().remove(PREF_ACCOUNT_NAME).commit();
                        listView = new ArrayList<>();
                        onStart();
                    }else{
                        //Convertimos la respuesta json a una lista de TaskListView
                        Type type = new TypeToken<List<SharedModelView>>(){}.getType();
                        sharedModelView = (List<SharedModelView>) gson.fromJson(response.toString(),type);
                        new HelperCreateOrUpdateSharedTask(mCredential,this,sharedModelView).execute();
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                    }


                }else{
                    onGetAllTasks();
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
