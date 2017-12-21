package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
@Document
public class StudentBean {
    @Id
    private String id;
    private String name;
    private Integer age;
    @Field("locs")
    private Collection<LocationBean> locations = new LinkedHashSet<>();

    public StudentBean(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Collection<LocationBean> getLocations() {
        return locations;
    }

    public void setLocations(Collection<LocationBean> locations) {
        this.locations = locations;
    }
}
