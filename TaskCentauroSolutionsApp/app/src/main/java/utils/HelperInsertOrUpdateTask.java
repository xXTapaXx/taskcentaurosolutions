package utils;

import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.jason.com.taskcentaurosolutionsapp.CreateTaskActivity;
import app.jason.com.taskcentaurosolutionsapp.LoginActivity;
import app.jason.com.taskcentaurosolutionsapp.MainActivity;
import views.TaskListView;
import views.TaskView;

/**
 * Created by Tapa on 24/06/2016.
 */

/**
 * An asynchronous task that handles the Google Tasks API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class HelperInsertOrUpdateTask extends AsyncTask<Void, Void, String> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    public CreateTaskActivity context;
    public TaskListView listTask;


    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;

    public HelperInsertOrUpdateTask(GoogleAccountCredential credential, CreateTaskActivity context,TaskListView listTask) {
        this.context = context;
        this.listTask = listTask;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();

    }

    public String insertOrUpdateTask(){

        TaskList listTasks = null;
        Task resultTask = null;
        String response = "";
        try {


            if(!listTask.getTitle().isEmpty()) {
                    if (listTask.getId() != null) {
                        listTasks = mService.tasklists().get(listTask.getId()).execute();
                        listTasks.setTitle(listTask.getTitle());

                        mService.tasklists().update(listTask.getId(), listTasks).execute();
                    } else {

                        TaskList taskList = new TaskList();
                        taskList.setTitle(listTask.getTitle());
                        listTasks = mService.tasklists().insert(taskList).execute();

                        listTask.setId(listTasks.getId());
                    }

                    for (TaskView task : listTask.getTasks()) {

                        if (task.getId() != null && !task.getTitle().isEmpty()) {


                            resultTask = mService.tasks().get(listTask.getId(), task.getId()).execute();
                            resultTask.setTitle(task.getTitle());
                            resultTask.setStatus(task.getStatus());
                            mService.tasks().update(listTask.getId(), task.getId(), resultTask).execute();

                        } else if(!task.getTitle().isEmpty()){
                            Task insertTask = new Task();
                            insertTask.setTitle(task.getTitle());

                            resultTask = mService.tasks().insert(listTasks.getId(), insertTask).execute();

                            if(task.getStatus().equals("completed")){
                                resultTask = mService.tasks().get(listTasks.getId(), resultTask.getId()).execute();
                                resultTask.setStatus(task.getStatus());
                                mService.tasks().update(listTasks.getId(), resultTask.getId(), resultTask).execute();
                            }
                        }
                        task.setId(resultTask.getId());
                    }

                    response = listTasks.getId();

              }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return response;
    }

    /**
     * Background task to call Google Tasks API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected String doInBackground(Void... params) {
        try {
            return insertOrUpdateTask();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
       context.mProgress.show();
    }

    @Override
    protected void onPostExecute(String output) {
        context.mProgress.hide();
        if (output == null || output.isEmpty()) {
            Toast.makeText(context,"No se Agrego la Tarea",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context,"Se Agrego y/o actualizó la tarea",Toast.LENGTH_LONG).show();
            context.onUpdateShared(listTask);
        }
    }

    @Override
    protected void onCancelled() {
       context.mProgress.hide();
        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                showGooglePlayServicesAvailabilityErrorDialog(
                        ((GooglePlayServicesAvailabilityIOException) mLastError)
                                .getConnectionStatusCode());
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                context.startActivityForResult(
                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
                        LoginActivity.REQUEST_AUTHORIZATION);
            } else {
                Toast.makeText(context,"The following error occurred:\n"
                        + mLastError.getMessage(),Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(context,"Request cancelled.",Toast.LENGTH_LONG).show();


        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                context,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


}
