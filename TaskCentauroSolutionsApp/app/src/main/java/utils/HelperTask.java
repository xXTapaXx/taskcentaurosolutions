package utils;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.taskcentaurosolutionsapp.LoginActivity;
import app.taskcentaurosolutionsapp.MainActivity;
import app.taskcentaurosolutionsapp.R;
import views.SharedModelView;
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
    public List<SharedModelView> sharedModelView;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    public HelperTask(GoogleAccountCredential credential, MainActivity context, List<SharedModelView> sharedModelView) {
        this.context = context;
        this.sharedModelView = sharedModelView;
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
        String url = context.getString(R.string.api_url)+"/haveCalendar?listId=";
        List<TaskListView> response = new ArrayList<TaskListView>();
        List<TaskView> arraylistResult = null ;
        TaskListView taskListView;
        List<String> prueba = new ArrayList<String>();

        try {
            TaskLists result = mService.tasklists().list()
                    .execute();

            List<TaskList> tasklists = result.getItems();
            for (TaskList tasklist : tasklists) {
                Boolean isShared = false;
                Boolean isCalendar = false;
                String listId = tasklist.getId();
                Tasks tasks = mService.tasks().list(tasklist.getId()).execute();
                arraylistResult = new ArrayList<TaskView>();




                if(sharedModelView.size() > 0){
                    for(SharedModelView shared : sharedModelView){
                        if(shared.list_id.equals(listId)){
                            isShared = true;
                            break;
                        }
                    }
                }

                String haveCalendar = GET(url+listId);

                if(Boolean.parseBoolean(haveCalendar)){
                    isCalendar = true;
                }

                if(tasks.getItems() != null)
                {


                    for (Task task : tasks.getItems()) {
                        prueba.add(String.format("%s (%s)\n",
                                task.getTitle(),
                                task.getId()));
                        arraylistResult.add(new TaskView(task.getId(),task.getTitle(),task.getStatus(),false));
                    }
                }


                taskListView = new TaskListView(tasklist.getId(),tasklist.getTitle(),arraylistResult,null,isShared,isCalendar);

                response.add(taskListView);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

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
        context.mProgress.show();
    }

    @Override
    protected void onPostExecute(List<TaskListView> output) {

        if (output == null || output.size() == 0) {
            Toast.makeText(context,"No results returned.", Toast.LENGTH_LONG).show();

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
                        + mLastError.getMessage(), Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(context,"Request cancelled.", Toast.LENGTH_LONG).show();


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
