package com.pzl.dreamer.net.download;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */

public class DownMsgEntity {
    private int id;
    private String path;
    private int downstate;
    private String name;
    private boolean isOk = false;

    public DownMsgEntity() {
    }

    public DownMsgEntity(String path, int downstate, String name) {
        this.path = path;
        this.downstate = downstate;
        this.name = name;
    }

    public boolean isOk() {
        return this.isOk;
    }

    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDownstate() {
        return this.downstate;
    }

    public void setDownstate(int downstate) {
        this.downstate = downstate;
    }

    public String toString() {
        return this.name + "：" + this.downstate;
    }
}
