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

import java.io.IOException;

import app.taskcentaurosolutionsapp.CreateActivity;
import app.taskcentaurosolutionsapp.CreateTaskActivity;
import app.taskcentaurosolutionsapp.LoginActivity;

/**
 * Created by Tapa on 24/06/2016.
 */

/**
 * An asynchronous task that handles the Google Tasks API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class HelperDeleteTask extends AsyncTask<Void, Void, Boolean> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    public CreateActivity context;
    public String listId;
    public String taskId;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    public static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    public HelperDeleteTask(GoogleAccountCredential credential, CreateActivity context, String listId, String taskId) {
        this.context = context;
        this.listId = listId;
        this.taskId = taskId;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();

        // String tasks = gson.toJson(mService, com.google.api.services.tasks.Tasks.class);
        // serviceController.jsonArrayRequest(getString(R.string.api_url)+"/taskslists?tasks="+tasks, Request.Method.GET, null, this, this);

    }

    public boolean deleteTasksList() {

        try {
            mService.tasks().delete(listId, taskId).execute();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    /**
     * Background task to call Google Tasks API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return deleteTasksList();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return false;
        }
    }

    @Override
    protected void onPreExecute() {
        context.mProgress.show();
    }

    @Override
    protected void onPostExecute(Boolean output) {
        context.mProgress.hide();
        if (!output) {
            Toast.makeText(context,"No se pudo eliminar la lista de Tareas", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,"La Tarea se eliminó correctamente", Toast.LENGTH_LONG).show();
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
