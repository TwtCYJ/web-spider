package com.twst.spider.model;

import java.io.Serializable;

/**
 * @description: ${description}
 * @author: chenyingjie
 * @create: 2018-12-27 17:28
 **/
public class MJBInfo implements Serializable {
    private static final long serialVersionUID = 25199995718491531L;

    private int id;
    private String title;
    private String content;
    private String auth;
    private String the_data;
    private int type;
    private int create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getThe_data() {
        return the_data;
    }

    public void setThe_data(String the_data) {
        this.the_data = the_data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
