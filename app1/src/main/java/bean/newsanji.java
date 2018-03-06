package bean;

import java.io.Serializable;
import java.util.List;

public class newsanji implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<newsanji1> sonList;



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<newsanji1> getSonList() {
        return sonList;
    }

    public void setSonList(List<newsanji1> sonList) {
        this.sonList = sonList;
    }
}
