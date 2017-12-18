package com.aplex.serialselect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.aplex.serialselect.utils.SPUtils;

public class MyReceiver extends BroadcastReceiver {
    private final String TAG = "MyReceiver";
    final int TEST_RS232_CMD_APLEX = 66;
    final int TEST_RS485_CMD_APLEX = 67;
    final int TEST_RS422_CMD_APLEX = 68;
    final int TEST_LOOPBACK_CMD_APLEX  = 69;
    final int TEST_RS485_TERM_CMD_APLEX = 70;
    final int TEST_RS422_TERM_CMD_APLEX = 71;
    public final static String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    SerialMode serialMode = new SerialMode();    //本地函数对象
    Integer serialmode;
    // 重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)){

            serialmode = (Integer) SPUtils.getValue("serialmode", Integer.valueOf(0));
            Log.e(TAG, "onReceive start serialmode="+serialmode);

            serialMode.fd = serialMode.open("/dev/com3mode", 0);
            if ( serialMode.fd > 0) {

                serialmode = (Integer)SPUtils.getValue("serialmode", Integer.valueOf(0));
                Log.e(TAG, "onReceive start serialmode="+serialmode);
                    switch (serialmode){
                        case 1:serialMode.ioctl(serialMode.fd, TEST_RS232_CMD_APLEX, 0);break;
                        case 2:serialMode.ioctl(serialMode.fd, TEST_RS485_CMD_APLEX , 0);break;
                        case 3:serialMode.ioctl(serialMode.fd, TEST_RS422_CMD_APLEX , 0);break;
                        case 4:serialMode.ioctl(serialMode.fd, TEST_RS485_TERM_CMD_APLEX, 0);break;
                        case 5:serialMode.ioctl(serialMode.fd, TEST_RS422_TERM_CMD_APLEX, 0);break;
                        case 6:serialMode.ioctl(serialMode.fd, TEST_LOOPBACK_CMD_APLEX, 0);break;
                        default:serialMode.ioctl(serialMode.fd, TEST_LOOPBACK_CMD_APLEX, 0);break;
                    }
                }

            }
            serialMode.close(serialMode.fd);
            serialMode = null;
            Log.e(TAG, "onReceive after");
    }
}
