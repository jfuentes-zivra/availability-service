package com.zivra.platform.availabilityservice;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(){
        this.content = "";
        this.id = -1;
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
