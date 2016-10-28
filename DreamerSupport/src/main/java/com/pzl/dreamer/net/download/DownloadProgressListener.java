package com.pzl.dreamer.net.download;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public interface DownloadProgressListener {
    void onDownloadMsg(int downloadSize, int speed);
}
