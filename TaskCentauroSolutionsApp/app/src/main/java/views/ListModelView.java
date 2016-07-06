package views;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tapa on 14/06/2016.
 */
public class ListModelView implements Serializable {

    public Integer id;

    public String list;

    public Integer sync;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public ListModelView(Integer id, String list, Integer sync) {
        this.id = id;
        this.list = list;
        this.sync = sync;
    }
}
