package com.example.demo.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 寇含尧 on 2017/10/23.
 */
@Entity(name = "person")
@NamedQuery(name = "PersonBean.withNameAndAddressNamedQuery",
query = "select p from person p where p.name = ?1 and p.address=?2")
public class PersonBean implements Serializable {
    @Transient
    private static final long serialVersionUID = -5939599230753662529L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;
    private String address;

    public PersonBean() {
        super();
    }

    public PersonBean(Long id, String name, Integer age, String address) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public PersonBean(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
