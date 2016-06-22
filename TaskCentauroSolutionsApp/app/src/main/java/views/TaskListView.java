package views;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tapa on 14/06/2016.
 */
public class TaskListView implements Serializable {

    public String id;

    public String title;

    public List<TaskView> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TaskView> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskView> tasks) {
        this.tasks = tasks;
    }

    public TaskListView(String id,  String title, List<TaskView> tasks){

        this.id = id;
        this.title = title;
        this.tasks = tasks;


    }

}
