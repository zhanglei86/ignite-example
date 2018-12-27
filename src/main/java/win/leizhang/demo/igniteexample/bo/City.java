package win.leizhang.demo.igniteexample.bo;

import java.io.Serializable;

/**
 * Created by zealous on 2018/12/27.
 */
public class City implements Serializable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
