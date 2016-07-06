package views;

import java.io.Serializable;

/**
 * Created by Tapa on 29/06/2016.
 */
public class SharedModelView implements Serializable{

    public String email;

    public TaskModelView shared_task_id;

    public ListModelView shared_list_id;

    public String list_id;

    public String task_id;

    public Integer sync;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TaskModelView getShared_task_id() {
        return shared_task_id;
    }

    public void setShared_task_id(TaskModelView shared_task_id) {
        this.shared_task_id = shared_task_id;
    }

    public ListModelView getShared_list_id() {
        return shared_list_id;
    }

    public void setShared_list_id(ListModelView shared_list_id) {
        this.shared_list_id = shared_list_id;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public SharedModelView(String email, TaskModelView shared_task_id, ListModelView shared_list_id, String list_id,
                           String task_id, Integer sync) {

        this.email = email;
        this.shared_task_id = shared_task_id;
        this.shared_list_id = shared_list_id;
        this.list_id = list_id;
        this.task_id = task_id;
        this.sync = sync;
    }

}
