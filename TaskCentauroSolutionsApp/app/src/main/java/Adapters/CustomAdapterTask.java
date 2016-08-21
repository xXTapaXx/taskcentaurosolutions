package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import app.taskcentaurosolutionsapp.R;
import views.TaskView;

/**
 * Created by Tapa on 14/06/2016.
 */
public class CustomAdapterTask extends BaseAdapter {

    private List<TaskView> task;
    private Context context;
    private LayoutInflater inflater;


    public CustomAdapterTask(Activity activity, List<TaskView> task){

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
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.lists_task,null);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            holder.task = (TextView) convertView.findViewById(R.id.task);

        convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.checkBox.setClickable(false);
        holder.task.setText(task.get(position).getTitle());

       if(task.get(position).getStatus().equals("completed")){
            holder.checkBox.setChecked(true);
           holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
           holder.checkBox.setChecked(false);
           holder.task.setPaintFlags(0);
       }



        return convertView;
    }

    public class Holder{
        CheckBox checkBox;
        TextView task;
    }
}
