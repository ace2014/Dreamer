package com.pzl.dreamer.net.download;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */

public class DownFileEntity {
    public String date;
    public String name;
    public String fileName;

    public DownFileEntity(String date, String name, String fileName) {
        this.date = date;
        this.name = name;
        this.fileName = fileName;
    }
}