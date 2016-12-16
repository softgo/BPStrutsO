package com.javabase.base.mongo.test;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * test 
 */
@Entity(value = "city", noClassnameStored = true)
public class City implements Serializable{
    @Id
    private ObjectId _id;
    private String id;
    private String code;
    private String text;
    private int type;

    @Override
    public String toString() {
        return "City{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
