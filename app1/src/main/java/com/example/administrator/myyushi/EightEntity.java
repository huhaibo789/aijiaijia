package com.example.administrator.myyushi;

/**
 * Created by 胡海波 on 2016/10/8.
 */
public class EightEntity  {
    private boolean checked = true;
    private String content;
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public EightEntity(boolean checked) {
        super();
        this.checked = checked;

    }
    public EightEntity() {
        super();
    }

}
