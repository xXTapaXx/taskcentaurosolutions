package views;

import java.util.List;

/**
 * Created by Tapa on 29/06/2016.
 */
public class SharedView {

    String myEmail;

    List<String> emails;

    TaskListView taskListView;

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public TaskListView getTaskListView() {
        return taskListView;
    }

    public void setTaskListView(TaskListView taskListView) {
        this.taskListView = taskListView;
    }

    public SharedView(String myEmail, List<String> emails, TaskListView taskListView){

        this.myEmail = myEmail;
        this.emails = emails;
        this.taskListView = taskListView;

    }
}
