package utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class HelperTask extends AsyncTask<Void, Void, List<TaskListView>> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    public MainActivity context;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    public HelperTask(GoogleAccountCredential credential, MainActivity context) {
        this.context = context;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();

        // String tasks = gson.toJson(mService, com.google.api.services.tasks.Tasks.class);
        // serviceController.jsonArrayRequest(getString(R.string.api_url)+"/taskslists?tasks="+tasks, Request.Method.GET, null, this, this);

    }

    public List<TaskListView> getTasks(){
        List<TaskListView> response = new ArrayList<TaskListView>();
        List<TaskView> arraylistResult = null ;
        TaskListView taskListView;
        List<String> prueba = new ArrayList<String>();
        try {
            TaskLists result = mService.tasklists().list()
                    .execute();

            List<TaskList> tasklists = result.getItems();
            for (TaskList tasklist : tasklists) {

                Tasks tasks = mService.tasks().list(tasklist.getId()).execute();
                arraylistResult = new ArrayList<TaskView>();
                if(tasks.getItems() != null)
                {


                    for (Task task : tasks.getItems()) {
                        prueba.add(String.format("%s (%s)\n",
                                task.getTitle(),
                                task.getId()));
                        arraylistResult.add(new TaskView(task.getId(),task.getTitle(),task.getStatus(),false));
                    }
                }

                taskListView = new TaskListView(tasklist.getId(),tasklist.getTitle(),arraylistResult,null);
                response.add(taskListView);
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
    protected List<TaskListView> doInBackground(Void... params) {
        try {
            return getTasks();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
      //  context.mProgress.show();
    }

    @Override
    protected void onPostExecute(List<TaskListView> output) {
        context.mProgress.hide();
        if (output == null || output.size() == 0) {
            Toast.makeText(context,"No results returned.",Toast.LENGTH_LONG).show();

        } else {
            context.onResponseAllTasks(output);
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
