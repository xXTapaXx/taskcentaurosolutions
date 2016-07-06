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

import app.jason.com.taskcentaurosolutionsapp.CreateTaskActivity;
import app.jason.com.taskcentaurosolutionsapp.R;
import app.jason.com.taskcentaurosolutionsapp.SharedActivity;
import views.TaskView;

/**
 * Created by Tapa on 23/06/2016.
 */
public class RecyclerAdapterShared extends  RecyclerView.Adapter<RecyclerAdapterShared.ViewHolder>{

    private List<String> listEmails;
    private SharedActivity context;

    public RecyclerAdapterShared(SharedActivity activity, List<String> emails){
        this.context = activity;
        this.listEmails = emails;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_shared,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.email_shared.setText(listEmails.get(position));

    }

    @Override
    public int getItemCount() {
        return listEmails.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView email_shared;
        public ViewHolder(View itemView){
            super(itemView);

            email_shared = (TextView) itemView.findViewById(R.id.email_shared);

        }
    }
}
