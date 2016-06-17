package app.jason.com.taskcentaurosolutionsapp;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import controllers.ServiceController;
import views.TaskListView;
import views.TaskView;

public class CreateTaskActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    List<EditText> listEditText;
    List<TaskView> listTaskView;
    String idTask;
    String tag;
    ServiceController serviceController;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = (Button) findViewById(R.id.add_task);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTask();
            }
        });

        //Inicializamos las listas
        listEditText = new ArrayList<EditText>();
        listTaskView = new ArrayList<TaskView>();

        //Instancia del Gson
        gson = new Gson();
        //Inicializamos el ServiceController
        serviceController = new ServiceController();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //llamar a tu metodora guardar
        try {
            onSaveTasks();


          //  MainActivity.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finish();

        return super.onSupportNavigateUp();
    }


    public void onSaveTasks() throws UnsupportedEncodingException {
        //Se Crean las variables a usar en este metodo
        TextView textViewTitleTask = (TextView) findViewById(R.id.title_list);
        TaskListView taskListView;
        TaskView taskView;
        tag = "idTask";

        for (int i = 0; i < listEditText.size(); i++){

            taskView = new TaskView(null,listEditText.get(i).getText().toString(),null);

            listTaskView.add(taskView);

        }

        taskListView = new TaskListView(null,textViewTitleTask.getText().toString(),listTaskView);

        //Convertimos la respuesta json a una lista de TaskListView
        String tasks = gson.toJson(taskListView, TaskListView.class);
        if(textViewTitleTask.getText().toString() != null || !textViewTitleTask.getText().toString().isEmpty()){
            serviceController.stringRequest(getString(R.string.api_url)+"/insertTasksList?title="+URLEncoder.encode(textViewTitleTask.getText().toString(),"UTF-8")+"&tasks="+URLEncoder.encode(tasks,"UTF-8"), Request.Method.GET, null, this, this, tag);

        }


    }
    public void onAddTask(){
        //Creamos el texto para crear mas tareas
        EditText editText = (EditText) new EditText(this);

        //Le agregamos algunos valores a esas tareas
        editText.setHint("Task");

        //Inicializamos el layout
        LinearLayout createTask = (LinearLayout)findViewById(R.id.task_list);

        //Le agregamos el campo de texto al layout
        createTask.addView(editText);

        //Agregamos el campo edit text a la lista
        listEditText.add(editText);




    }

    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyError prueba = error;
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(getApplicationContext(),"Se agrego Exitosamente",Toast.LENGTH_SHORT).show();


    }
}
