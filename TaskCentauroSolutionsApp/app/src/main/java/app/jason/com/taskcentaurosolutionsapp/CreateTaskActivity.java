package app.jason.com.taskcentaurosolutionsapp;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.tasks.TasksScopes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapters.CustomAdapterEditTask;
import Adapters.RecyclerAdapterEditTask;
import controllers.ServiceController;
import utils.HelperDeleteList;
import utils.HelperDeleteTask;
import utils.HelperInsertOrUpdateTask;
import views.SharedModelView;
import views.SharedView;
import views.TaskListView;
import views.TaskView;

public class CreateTaskActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    //Shared Preference
    SharedPreferences userDetails;
    String date;
    List<TaskView> listTaskView;
    TaskListView taskListView;
    String idList = null;
    String tag;
    TextView textViewTitleTask;
    ListView listView;
    LayoutInflater inflater;
   // RecyclerAdapterEditTask adapter;
    CustomAdapterEditTask adapter;
    SharedView sharedView;

    //Gson
    Gson gson;
    ServiceController serviceController;

    //RecyclerView recyclerView;
   // LinearLayoutManager linearLayoutManager;

    //Dialog Progress
    public ProgressDialog mProgress;

    GoogleAccountCredential mCredential;
    //Account Name
    private static final String PREF_ACCOUNT_NAME = "accountName";
    //Scopes
    private static final String[] SCOPES = { TasksScopes.TASKS_READONLY, TasksScopes.TASKS };

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
        private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Date
        calendar = Calendar.getInstance();
        //dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        dateFormat = new SimpleDateFormat(DATE_PATTERN,Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        //Inicializamos el shared Preference
        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);

        //Instancia del Gson
        gson = new Gson();

        serviceController = new ServiceController();

        //Inicializamos el RecyclerView
        /*recyclerView = (RecyclerView) findViewById(R.id.recycler_view_edit);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        //Inicializamos la lista
        listTaskView = new ArrayList<TaskView>();

        //Inicializamos el customAdapter
        adapter = new CustomAdapterEditTask(this,listTaskView);
        //adapter = new RecyclerAdapterEditTask(this,listTaskView);

        //Inicializamos el listView
        listView = (ListView) findViewById(R.id.list_view_tasks);
        listView.setAdapter(adapter);
        //Inicializamos el inflate
        inflater = (LayoutInflater) getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Se inicializa el texto del titulo de la tarea
         textViewTitleTask = (TextView) findViewById(R.id.title_list);

        //Inicializamos el Progress Dialog
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");


        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
        getApplicationContext(), Arrays.asList(SCOPES))
        .setBackOff(new ExponentialBackOff());

        //Se activa el boton para regresar a la actividad anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Codigo del boton Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);

        //Agregamos accion de click al boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTask();
            }
        });

       /* TextView button = (TextView) findViewById(R.id.add_task);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTask();
            }
        });*/

        taskListView = (TaskListView) getIntent().getSerializableExtra("ListView");

        if(taskListView != null){
            onAddEditTast(taskListView);
        }
    }

    public void onUpdateShared(TaskListView taskListViewResult) {
        onUpdateList();
        String myEmail = userDetails.getString(PREF_ACCOUNT_NAME, null);
        TaskListView result = null;
        List<TaskView> taskViewListShared = new ArrayList<>();
        if(taskListViewResult.getTasks().size() > 0){
            for(TaskView task : taskListView.getTasks()){
                taskViewListShared.add(task);
            }
        }
            result = new TaskListView(taskListViewResult.getId(),taskListViewResult.getTitle(),taskViewListShared,date,false,false);
            sharedView = new SharedView(myEmail,null,result);
            //onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

            //Convertimos la respuesta json a una lista de TaskListView
            String shared = gson.toJson(sharedView, SharedView.class);

            try {
                serviceController.stringRequest(getString(R.string.api_url)+"/updateShared?shared="+ URLEncoder.encode(shared,"UTF-8"), Request.Method.GET, null, this, this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //insertShared?shared="+shared


        onFinishActivity();


    }

    public void onSelectDate(){
      new AlertDialog.Builder(CreateTaskActivity.this)
                .setTitle("Fecha")
                .setMessage("Desea Seleccionar una Fecha de Caducidad?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onShowDatePicker();
                          }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onSaveTasks();
                    }
                }
        ).show();
    }

    public void onShowDatePicker(){
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
    }

    public void onShowTimePicker(){
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onBackPressed() {
        //llamar a tu metodora guardar

        onSelectDate();
       // onSaveTasks();
       /* try {

            if(!textViewTitleTask.getText().toString().isEmpty()){
                onUpdateShared();
            }else{
                onFinishActivity();
            }

            //  MainActivity.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        //llamar a tu metodora guardar
    /*    try {
            if(!textViewTitleTask.getText().toString().isEmpty()){*/
                //onUpdateShared();
            onSelectDate();
            //onSaveTasks();

          //  MainActivity.
       /* } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/


        return super.onSupportNavigateUp();
    }


    public void onSaveTasks()  {
        //Se Crean las variables a usar en este metodo
        TaskListView taskListView;
        tag = "idTask";


       // for(int i = 0; i < listTaskView.size(); i++){
            onUpdateList();
       // }


        taskListView = new TaskListView(idList,textViewTitleTask.getText().toString(),listTaskView,date,false,false);

        String accountName = userDetails.getString(PREF_ACCOUNT_NAME, null);
        //String accountName = null;
        if (accountName != null) {
            mCredential.setSelectedAccountName(accountName);
            new HelperInsertOrUpdateTask(mCredential, this, taskListView).execute();

        }



    }

    public void onFinishActivity(){
        finish();
    }
    public void onAddTask(){
        onUpdateList();

        //Agregamos un item nuevo a listTaskView
        listTaskView.add(new TaskView(null,null,"needsAction",true));
        onUpdateList();
        //adapter.notifyDataSetChanged();




        // listView.setAdapter(adapter);



    }

    public void onUpdateList(){
        //View view = null;

            if(listTaskView.size() > 0){
                for(int i = 0; i < listView.getChildCount(); i++){
                    View view = listView.getChildAt(i);

                    EditText editTextUpdate = (EditText) view.findViewById(R.id.edit_task);
                    CheckBox checkBoxUpdate = (CheckBox) view.findViewById(R.id.checkbox_edit);

                    if(checkBoxUpdate.isChecked()){
                        listTaskView.get(i).setStatus("completed");
                    }else{
                        checkBoxUpdate.setChecked(false);
                        listTaskView.get(i).setStatus("needsAction");
                    }
                    listTaskView.get(i).setTitle(editTextUpdate.getText().toString());

                }
            }

        adapter.notifyDataSetChanged();
       // listView.setAdapter(adapter);
    }

    public void onDeleteTask(int position){
        if(!listTaskView.get(position).getNew()){
            String accountName = userDetails.getString(PREF_ACCOUNT_NAME, null);
            //String accountName = null;
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                new HelperDeleteTask(mCredential, this, idList, listTaskView.get(position).getId()).execute();
            }

            String myEmail = userDetails.getString(PREF_ACCOUNT_NAME, null);
            TaskListView result = null;
            List<TaskView> taskViewListShared = new ArrayList<>();
            taskViewListShared.add(listTaskView.get(position));
            if(taskListView != null){
                result = new TaskListView(taskListView.getId(),taskListView.getTitle(),taskViewListShared,date,false,false);
                sharedView = new SharedView(myEmail,null,result);
                //onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

                //Convertimos la respuesta json a una lista de TaskListView
                String shared = gson.toJson(sharedView, SharedView.class);

                try {
                    serviceController.stringRequest(getString(R.string.api_url)+"/updateDeleteTask?shared="+ URLEncoder.encode(shared,"UTF-8"), Request.Method.GET, null, this, this);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        listTaskView.remove(position);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    public void onAddEditTast(TaskListView taskListViews){

            //Seteamos algunas valores
            idList = taskListViews.getId();
            textViewTitleTask.setText(taskListViews.getTitle());

        for (int i = 0; i < taskListViews.getTasks().size(); i++){
            listTaskView.add(new TaskView(taskListViews.getTasks().get(i).getId(),taskListViews.getTasks().get(i).getTitle(),taskListViews.getTasks().get(i).getStatus(),false));
            adapter.notifyDataSetChanged();
        }

        listView.setAdapter(adapter);
       // listView.setAdapter(adapter);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
      //  try {
          onFinishActivity();
       /* } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        date = dateFormat.format(calendar.getTime());
        onShowTimePicker();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
         date += " " + timeFormat.format(calendar.getTime());
        onSaveTasks();
    }
}
