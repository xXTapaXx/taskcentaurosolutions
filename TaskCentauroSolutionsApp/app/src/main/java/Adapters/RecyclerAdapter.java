package Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.jason.com.taskcentaurosolutionsapp.MainActivity;
import app.jason.com.taskcentaurosolutionsapp.R;
import utils.HelperSizeLista;
import views.TaskListView;

/**
 * Created by Tapa on 23/06/2016.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<TaskListView> task;
    private MainActivity context;

    public RecyclerAdapter(MainActivity activity, List<TaskListView> task){
        this.context = activity;
        this.task = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.title.setText(task.get(position).title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.onClickShow(position);
            }
        });
        holder.listView.setAdapter(new CustomAdapterTask(context,task.get(position).getTasks()));
        HelperSizeLista.getListViewSize(holder.listView);
        holder.btn_delete_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Eliminar Lista");
                dialog.setMessage("¿Seguro deseas eliminar esta lista?");
                dialog.setNegativeButton("Cancelar", null);
                dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.onDeleteList(position);
                    }
                });

                dialog.show();
            }
        });

        holder.btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onShared(position);
            }
        });

       /* holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // context.onDeleteList(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Eliminar Lista");
                dialog.setMessage("¿Seguro deseas eliminar esta lista?");
                dialog.setNegativeButton("Cancelar", null);
                dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.onDeleteList(position);
                    }
                });

                dialog.show();
                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ListView listView;
        public ImageButton btn_shared;
        public ImageButton btn_delete_list;
        public ViewHolder(View itemView){
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            listView = (ListView) itemView.findViewById(R.id.list_view_item);
            btn_shared = (ImageButton) itemView.findViewById(R.id.btn_shared);
            btn_delete_list = (ImageButton) itemView.findViewById(R.id.btn_delete_list);

        }
    }
}