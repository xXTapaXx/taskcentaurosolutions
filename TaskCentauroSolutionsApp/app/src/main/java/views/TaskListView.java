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

    public String date;

    public Boolean isShared;

    public Boolean isCalendar;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public Boolean getCalendar() {
        return isCalendar;
    }

    public void setCalendar(Boolean calendar) {
        isCalendar = calendar;
    }

    public TaskListView(String id, String title, List<TaskView> tasks, String date, Boolean isShared, Boolean isCalendar) {
        this.id = id;
        this.title = title;
        this.tasks = tasks;
        this.date = date;
        this.isShared = isShared;
        this.isCalendar = isCalendar;
    }
}
