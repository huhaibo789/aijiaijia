package bean;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class Hotbean {
    private  String categorieName;
    private  String categoriePic;

    public String getCategorieName() {
        return categorieName;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public String getCategoriePic() {
        return categoriePic;
    }

    public void setCategoriePic(String categoriePic) {
        this.categoriePic = categoriePic;
    }

    public Hotbean(String categorieName, String categoriePic) {
        this.categorieName = categorieName;
        this.categoriePic = categoriePic;
    }
}
