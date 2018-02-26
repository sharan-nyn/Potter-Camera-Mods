package potter.project.cameramods;

/**
 * Created by Sharan on 2/26/2018.
 */

public class Mods {
    private String name,desc;
    public Mods(String name, String desc)
    {
        this.setName(name);
        this.setDesc(desc);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
