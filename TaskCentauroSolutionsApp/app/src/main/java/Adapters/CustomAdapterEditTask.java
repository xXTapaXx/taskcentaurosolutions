package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.jason.com.taskcentaurosolutionsapp.CreateTaskActivity;
import app.jason.com.taskcentaurosolutionsapp.R;
import views.TaskView;

/**
 * Created by Tapa on 14/06/2016.
 */
public class CustomAdapterEditTask extends BaseAdapter {

    private List<TaskView> task;
    private CreateTaskActivity context;
    private LayoutInflater inflater;
    private TextWatcher watcher;

    public CustomAdapterEditTask(CreateTaskActivity activity, List<TaskView> task){

        this.context = activity;
        this.task = task;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return task.size();
    }

    @Override
    public Object getItem(int position) {
        return task.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;

        /*Toast.makeText(context,"OnKey:" + holder.task.getText().toString() , Toast.LENGTH_LONG).show();
        task.get(position).setTitle(holder.task.getText().toString());*/

           if(convertView == null){
               holder = new Holder();
               convertView = inflater.inflate(R.layout.edit_task,null);
               holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_edit);
               holder.checkBox.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       context.onUpdateList(position);
                   }
               });
               holder.task = (TextView) convertView.findViewById(R.id.edit_task);

               convertView.setTag(holder);
           }else{
               holder = (Holder) convertView.getTag();
           }

        holder.task.setText(task.get(position).getTitle());

        if(task.get(position).getNew()){
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.task.setHint("New Task");
        }else{
            if(task.get(position).getStatus().equals("completed")){
                holder.checkBox.setChecked(true);
                holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                holder.checkBox.setChecked(false);
                holder.task.setPaintFlags(0);
            }
        }



        return convertView;
    }

    public class Holder{
        CheckBox checkBox;
        TextView task;
    }

}
