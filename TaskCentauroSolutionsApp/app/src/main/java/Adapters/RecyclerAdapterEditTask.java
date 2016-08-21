package Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import app.taskcentaurosolutionsapp.CreateTaskActivity;
import app.taskcentaurosolutionsapp.R;
import views.TaskView;

/**
 * Created by Tapa on 23/06/2016.
 */
public class RecyclerAdapterEditTask extends  RecyclerView.Adapter<RecyclerAdapterEditTask.ViewHolder>{

    private List<TaskView> listTask;
    private CreateTaskActivity context;

    public RecyclerAdapterEditTask(CreateTaskActivity activity, List<TaskView> task){
        this.context = activity;
        this.listTask = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_task,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onUpdateList();
            }
        });

        holder.task.setText(listTask.get(position).getTitle());

        if(listTask.get(position).getNew()){
            holder.task.setHint("New Task");
        }

        if(listTask.get(position).getStatus().equals("completed")){
            holder.checkBox.setChecked(true);
            holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.checkBox.setChecked(false);
            holder.task.setPaintFlags(0);
        }

        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Eliminar Tarea");
                dialog.setMessage("¿Seguro deseas eliminar esta Tarea?");
                dialog.setNegativeButton("Cancelar", null);
                dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.onDeleteTask(position);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public TextView task;
        public Button deleteTask;
        public ViewHolder(View itemView){
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_edit);
            task = (TextView) itemView.findViewById(R.id.edit_task);
            deleteTask = (Button) itemView.findViewById(R.id.delete_task);

        }
    }
}
