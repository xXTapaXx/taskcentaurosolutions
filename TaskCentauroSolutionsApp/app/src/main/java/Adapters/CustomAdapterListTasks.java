package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import app.jason.com.taskcentaurosolutionsapp.R;
import utils.HelperSizeLista;
import views.TaskListView;
import views.TaskView;

/**
 * Created by Tapa on 14/06/2016.
 */
public class CustomAdapterListTasks extends BaseAdapter {

    private List<TaskListView> task;
    private Activity context;
    private LayoutInflater inflater;


    public CustomAdapterListTasks(Activity activity, List<TaskListView> task){

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
            convertView = inflater.inflate(R.layout.grid_item,null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.listView = (ListView) convertView.findViewById(R.id.list_view_item);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.title.setText(task.get(position).getTitle());

        holder.listView.setAdapter( new CustomAdapterTask(context,task.get(position).getTasks()));

        HelperSizeLista.getListViewSize(holder.listView);

        return convertView;
    }

    public class Holder{
        TextView title;
        ListView listView;
    }
}
