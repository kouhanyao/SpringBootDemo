package com.example.demo.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
public class TeacherBean implements Serializable{
    @Transient
    private static final long serialVersionUID = -5939599230753662529L;
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private Integer age;
    private String address;

    public TeacherBean() {
        super();
    }

    public TeacherBean(String id, String name, Integer age, String address) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public TeacherBean(String name, Integer age) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
