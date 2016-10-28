package com.pzl.dreamer.net.download;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class FileDownloader {
    private static final String TAG = "FileDownloader";
    private Context context;
    private FileService fileService;
    private int downloadSize = 0;

    private int fileSize = 0;
    private DownloadThread[] threads;
    private File saveFile;
    private Map<Integer, Integer> data = new ConcurrentHashMap();
    private int block;
    private String downloadUrl;
    private String _fileName = "";

    private long prev_time = 0L;
    private long this_time = 0L;
    private int prev_downSize = 0;

    private int down_state = 0;
    public static final int UN_START = 0;
    public static final int DOWNLOAD_ING = 1;
    public static final int STOP = 2;
    public static final int SUSPEND = 3;
    public static final int FINISH = 4;
    public static final int ZIP = 5;
    public static final int ERROR = 6;
    public static final int UN_KONW = -1;

    public int getThreadSize() {
        return this.threads.length;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    protected synchronized void append(int size) {
        this.downloadSize += size;
    }

    public String getFileName() {
        return this._fileName;
    }

    public String getfileFath() {
        return this.saveFile.getPath();
    }

    public void stopDownLoad() {
        for (int i = 0; i < this.threads.length; i++) {
            if ((this.threads[i] != null) && (!this.threads[i].isStop())) {
                this.threads[i].stopThread();
            }
        }

        this.down_state = 2;
    }

    public void suspendDownLoad() {
        for (int i = 0; i < this.threads.length; i++) {
            if ((this.threads[i] != null) && (!this.threads[i].isStop())) {
                this.threads[i].stopThread();
            }
        }

        this.down_state = 3;
    }

    public int getDownLoadState() {
        return this.down_state;
    }

    protected void update(int threadId, int pos) {
        this.data.put(Integer.valueOf(threadId), Integer.valueOf(pos));
    }

    protected synchronized void saveLogFile() {
        this.fileService.update(this.downloadUrl, this.data);
    }

    public FileDownloader(Context context, String downloadUrl, File fileSaveDir, int threadNum) {
        try {
            this.context = context;
            this.downloadUrl = downloadUrl;
            this.fileService = new FileService(this.context);
            URL url = new URL(this.downloadUrl);
            if (!fileSaveDir.exists())
                fileSaveDir.mkdirs();
            this.threads = new DownloadThread[threadNum];
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");

            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Referer", downloadUrl);
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            printResponseHeader(conn);
            if (conn.getResponseCode() == 200) {
                this.fileSize = conn.getContentLength();
                if (this.fileSize <= 0) {
                    throw new RuntimeException("Unkown file size ");
                }
                String filename = getFileName(conn);
                this._fileName = filename;
                this.saveFile = new File(fileSaveDir, filename);
                Map<Integer, Integer> logdata = this.fileService.getData(downloadUrl);

                if (logdata.size() > 0) {
                    for (Map.Entry entry : logdata.entrySet()) {
                        this.data.put((Integer) entry.getKey(), (Integer) entry.getValue());
                    }
                }
                this.block = (this.fileSize % this.threads.length == 0 ? this.fileSize / this.threads.length : this.fileSize / this.threads.length + 1);

                if (this.data.size() == this.threads.length) {
                    for (int i = 0; i < this.threads.length; i++) {
                        this.downloadSize += ((Integer) this.data.get(Integer.valueOf(i + 1))).intValue();
                    }
                    print("下载的总大小" + this.downloadSize);
                }
                this.down_state = 0;
            } else {
                this.down_state = -1;
                throw new RuntimeException("server no response ");
            }
        } catch (IOException e) {
            this.down_state = -1;
            print(e.toString());
        } catch (Exception e) {
            this.down_state = -1;
            print(e.toString());
            throw new RuntimeException("don't connection this url");
        }
    }

    private String getFileName(HttpURLConnection conn) {
        String filename = "";
        for (int i = 0; ; i++) {
            String mine = conn.getHeaderField(i);
            if (mine == null)
                break;
            if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
                Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine);
                if (!m.find()) continue;
                try {
                    filename = m.group(1).replace("\"", "");
                    String[] split = filename.split("%");
                    if (split.length > 2) {
                        filename = URLDecoder.decode(filename, "utf-8");
                    } else filename = new String(filename.getBytes("ISO8859_1"), "GB2312");

                    return filename;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf(47) + 1);
        if ((filename == null) || ("".equals(filename.trim()))) {
            filename = UUID.randomUUID() + ".tmp";
        }

        return filename;
    }

    public int download(DownloadProgressListener listener)
            throws Exception {
        try {
            RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
            if (this.fileSize > 0)
                randOut.setLength(this.fileSize);
            randOut.close();
            URL url = new URL(this.downloadUrl);
            if (this.data.size() != this.threads.length) {
                this.data.clear();
                for (int i = 0; i < this.threads.length; i++) {
                    this.data.put(Integer.valueOf(i + 1), Integer.valueOf(0));
                }
            }
            for (int i = 0; i < this.threads.length; i++) {
                int downLength = ((Integer) this.data.get(Integer.valueOf(i + 1))).intValue();
                if ((downLength < this.block) && (this.downloadSize < this.fileSize)) {
                    this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, ((Integer) this.data.get(Integer.valueOf(i + 1))).intValue(), i + 1);

                    this.threads[i].setPriority(7);
                    this.threads[i].start();
                } else {
                    this.threads[i] = null;
                }
            }

            this.down_state = 1;
            this.fileService.save(this.downloadUrl, this.data);
            boolean notFinish = true;
            int speed = 0;
            while (notFinish) {
                Thread.sleep(900L);
                notFinish = false;
                for (int i = 0; i < this.threads.length; i++) {
                    if ((this.threads[i] != null) && (!this.threads[i].isStop())) {
                        notFinish = true;
                        if (this.threads[i].getDownLength() == -1L) {
                            this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, ((Integer) this.data.get(Integer.valueOf(i + 1))).intValue(), i + 1);

                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                        }
                    }
                }
                if (listener != null) {
                    this.this_time = new Date().getTime();
                    if (this.prev_time == 0L) {
                        listener.onDownloadMsg(this.downloadSize, 0);
                        this.prev_downSize = this.downloadSize;
                        this.prev_time = this.this_time;
                        continue;
                    }
                    if ((this.prev_time == this.this_time) || (this.downloadSize == this.fileSize))
                        speed = 0;
                    else {
                        speed = (int) ((this.downloadSize - this.prev_downSize) / (this.this_time - this.prev_time));
                    }
                    listener.onDownloadMsg(this.downloadSize, speed * 1000);
                    this.prev_downSize = this.downloadSize;
                    this.prev_time = this.this_time;
                }

            }

            if (this.downloadSize >= this.fileSize) {
                this.down_state = 4;
                this.fileService.delete(this.downloadUrl);
            }
        } catch (Exception e) {
            print(e.toString());
            throw new Exception("file download fail");
        }
        return this.downloadSize;
    }

    private static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map header = new LinkedHashMap();
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }

    private static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry entry : header.entrySet()) {
            String key = entry.getKey() != null ? (String) entry.getKey() + ":" : "";
            print(key + (String) entry.getValue());
        }
    }

    private static void print(String msg) {
        Log.i("FileDownloader", msg);
    }
}