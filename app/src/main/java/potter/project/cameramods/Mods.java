package potter.project.cameramods;

/**
 * Created by Sharan on 2/26/2018.
 */

public class Mods {
    private String title;
    private String desc;
    private int id;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
