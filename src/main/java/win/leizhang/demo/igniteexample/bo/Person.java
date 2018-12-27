package win.leizhang.demo.igniteexample.bo;

import java.io.Serializable;

/**
 * Created by zealous on 2018/12/27.
 */
public class Person implements Serializable {
    private Long id;
    private String name;
    private Long cityId;

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
