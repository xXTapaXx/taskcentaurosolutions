package views;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Tapa on 14/06/2016.
 */
public class TaskView implements Serializable {

    public String id;

    public String title;

    public String status;

    public Boolean isNew;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public TaskView(String id, String title, String status,Boolean isNew){

        this.id = id;
        this.title = title;
        this.status = status;
        this.isNew = isNew;

    }
}
