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

import java.io.IOException;
import java.util.List;

import app.jason.com.taskcentaurosolutionsapp.CreateTaskActivity;
import app.jason.com.taskcentaurosolutionsapp.LoginActivity;
import app.jason.com.taskcentaurosolutionsapp.MainActivity;
import views.SharedModelView;
import views.TaskListView;
import views.TaskModelView;
import views.TaskView;

/**
 * Created by Tapa on 24/06/2016.
 */

/**
 * An asynchronous task that handles the Google Tasks API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class HelperCreateOrUpdateSharedTask extends AsyncTask<Void, Void, String> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    public MainActivity context;
    public List<SharedModelView> sharedModelView;


    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;

    public HelperCreateOrUpdateSharedTask(GoogleAccountCredential credential, MainActivity context, List<SharedModelView> sharedModelView) {
        this.context = context;
        this.sharedModelView = sharedModelView;
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
        String listId = null;
        String listName = null;
        try {


            for(SharedModelView shared : sharedModelView){
                if(!shared.getShared_list_id().getList().isEmpty()) {
                    if (shared.getList_id() != null && !shared.getList_id().isEmpty() && !shared.getList_id().toString().equals("null")) {

                        if(shared.getShared_list_id().getSync() == 0){
                            try {
                                mService.tasklists().delete(shared.getList_id()).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            listTasks = mService.tasklists().get(shared.getList_id()).execute();
                            listTasks.setTitle(shared.getShared_list_id().getList());

                            mService.tasklists().update(shared.getList_id(), listTasks).execute();
                            listId = listTasks.getId();
                            listName = listTasks.getTitle();
                        }

                    } else if(listId != null && listName.equals(shared.getShared_list_id().getList())){

                        listTasks = mService.tasklists().get(listId).execute();
                        listTasks.setTitle(shared.getShared_list_id().getList());

                        mService.tasklists().update(listId, listTasks).execute();

                    }else{
                        TaskList taskList = new TaskList();
                        taskList.setTitle(shared.getShared_list_id().getList());
                        listTasks = mService.tasklists().insert(taskList).execute();
                        listId = listTasks.getId();
                        listName = listTasks.getTitle();
                    }

                        if(shared.getTask_id() != null  && !shared.getTask_id().toString().equals("null") && shared.getShared_task_id().getSync() == 0){

                            try {
                                mService.tasks().delete(shared.getList_id(), shared.getTask_id()).execute();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            if (shared.getTask_id() != null && !shared.getShared_task_id().getTask().isEmpty() && !shared.getTask_id().toString().equals("null") && shared.getShared_task_id().getSync() > 0) {

                                resultTask = mService.tasks().get(shared.getList_id(), shared.getTask_id()).execute();
                                resultTask.setTitle(shared.getShared_task_id().getTask());
                                resultTask.setStatus(shared.getShared_task_id().getStatus());
                                mService.tasks().update(shared.getList_id(), shared.getTask_id(), resultTask).execute();

                            } else if(!shared.getShared_task_id().getTask().isEmpty() && shared.getShared_task_id().getSync() > 0){
                                Task insertTask = new Task();
                                insertTask.setTitle(shared.getShared_task_id().getTask());
                                resultTask = mService.tasks().insert(listTasks.getId(), insertTask).execute();

                                if(shared.getShared_task_id().getStatus().equals("completed")){
                                    resultTask = mService.tasks().get(listTasks.getId(), resultTask.getId()).execute();
                                    resultTask.setStatus(shared.getShared_task_id().getStatus());
                                    mService.tasks().update(listTasks.getId(), resultTask.getId(), resultTask).execute();
                                }
                            }
                        }

                    }

                shared.setList_id(listTasks.getId());
                shared.setTask_id(resultTask.getId());
                response = "Se Agrego y/o actualizó la tarea";
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
            Toast.makeText(context,"Hubo un error al compartir la tarea",Toast.LENGTH_LONG).show();

        } else {
            context.onSharedFinish(sharedModelView);
           // Toast.makeText(context,"Se Agrego y/o actualizó la tarea",Toast.LENGTH_LONG).show();

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
