package app.jason.com.taskcentaurosolutionsapp;



import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.zip.Inflater;


import Adapters.CustomAdapterEditTask;
import controllers.ServiceController;
import views.TaskListView;
import views.TaskView;

public class CreateTaskActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    List<EditText> listEditText;
    List<TaskView> listTaskView;
    TaskListView taskListView;
    String idTask;
    String tag;
    ServiceController serviceController;
    Gson gson;
    TextView textViewTitleTask;
    LinearLayout addTaskView;
    ListView listView;
    LayoutInflater inflater;
    CustomAdapterEditTask adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        listEditText = new ArrayList<EditText>();
        listTaskView = new ArrayList<TaskView>();

        //Inicializamos el customAdapter
        adapter = new CustomAdapterEditTask(this,listTaskView);
        //Inicializamos las listas

        //Inicializamos el listView
        listView = (ListView) findViewById(R.id.list_view_tasks);

        //Instancia del Gson
        gson = new Gson();
        //Inicializamos el ServiceController
        serviceController = new ServiceController();

        //Inicializamos el inflate
        inflater = (LayoutInflater) getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Se inicializa el texto del titulo de la tarea
         textViewTitleTask = (TextView) findViewById(R.id.title_list);

        //Inicializamos el layout
        // addTaskView = (LinearLayout)findViewById(R.id.task_list);

        //Se activa el boton para regresar a la actividad anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView button = (TextView) findViewById(R.id.add_task);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTask();
            }
        });

        taskListView = (TaskListView) getIntent().getSerializableExtra("ListView");

        if(taskListView != null){
            onAddEditTast(taskListView);
        }
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
        TaskListView taskListView;
        tag = "idTask";

        for(int i = 0; i < listTaskView.size(); i++){
            onUpdateList(i);
        }

        taskListView = new TaskListView(idTask,textViewTitleTask.getText().toString(),listTaskView);

        //Convertimos la respuesta json a una lista de TaskListView
        String tasks = gson.toJson(taskListView, TaskListView.class);
        if(textViewTitleTask.getText().toString() != null || !textViewTitleTask.getText().toString().isEmpty()){
            serviceController.stringRequest(getString(R.string.api_url)+"/insertTasksList?title="+URLEncoder.encode(textViewTitleTask.getText().toString(),"UTF-8")+"&tasks="+URLEncoder.encode(tasks,"UTF-8"), Request.Method.GET, null, this, this, tag);

        }


    }
    public void onAddTask(){

        for(int i = 0; i < listTaskView.size(); i++){
            onUpdateList(i);
        }
        //Agregamos un item nuevo a listTaskView
        listTaskView.add(new TaskView(null,null,null,true));
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

    }

    public void onUpdateList(int position){
        View view = listView.getChildAt(position);
        EditText editTextUpdate = (EditText) view.findViewById(R.id.edit_task);
        CheckBox checkBoxUpdate = (CheckBox) view.findViewById(R.id.checkbox_edit);

        if(checkBoxUpdate.isChecked()){
            listTaskView.get(position).setStatus("completed");
        }else{
            checkBoxUpdate.setChecked(false);
            listTaskView.get(position).setStatus("needsAction");
        }
        listTaskView.get(position).setTitle(editTextUpdate.getText().toString());
        adapter.notifyDataSetChanged();
    }

    public void onAddEditTast(TaskListView taskListViews){
      View  convertView = inflater.inflate(R.layout.edit_task,null);

            //Seteamos algunas valores
            idTask = taskListViews.getId();
            textViewTitleTask.setText(taskListViews.getTitle());

        for (int i = 0; i < taskListViews.getTasks().size(); i++){
            listTaskView.add(new TaskView(taskListViews.getTasks().get(i).getId(),taskListViews.getTasks().get(i).getTitle(),taskListViews.getTasks().get(i).getStatus(),false));
            adapter.notifyDataSetChanged();
        }


        listView.setAdapter(adapter);

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
