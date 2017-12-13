package com.aplex.serialselect;

/**
 * Created by aplex on 2017/12/13.
 */
public class SerialMode {
    public int fd = 0;
    native public int open(String path, int flags);
    native public void close(int fd);
    native public long ioctl(int fd, int cmd, int value);

    static {
        System.loadLibrary("serialmode");
    }
}
