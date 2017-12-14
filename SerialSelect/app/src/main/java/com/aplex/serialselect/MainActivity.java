package com.aplex.serialselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplex.serialselect.R;
import com.aplex.serialselect.utils.SPUtils;

import java.io.File;

public class MainActivity extends Activity  {

    final int TEST_RS232_CMD_APLEX = 66;
    final int TEST_RS485_CMD_APLEX = 67;
    final int TEST_RS422_CMD_APLEX = 68;
    final int TEST_LOOPBACK_CMD_APLEX  = 69;
    final int TEST_RS485_TERM_CMD_APLEX = 70;
    final int TEST_RS422_TERM_CMD_APLEX = 71;

    SerialMode serialMode = new SerialMode();    //本地函数对象
    Button BtRS232;
    Button BtRS485;
    Button BtRS422;
    Button BtRS485Trml;
    Button BtRS422Trml;
    Button BtLoopBack;

    int BtId = -1;
    String Prompt;
    int cmd;
    Integer serialmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtRS232 = (Button)findViewById(R.id.ButtonRS232);
        BtRS485 = (Button)findViewById(R.id.ButtonRS485);
        BtRS422 = (Button)findViewById(R.id.ButtonRS422);
        BtRS485Trml = (Button)findViewById(R.id.ButtonRS485Trml);
        BtRS422Trml = (Button)findViewById(R.id.ButtonRS422Trml);
        BtLoopBack = (Button)findViewById(R.id.ButtonLoopBack);
        //SPUtils.testSPUtils(getApplication());

        //getApplication()
        //bt.findViewById(R.id.Button1);
        //bt.setOnClickListener();

//        serialMode.fd = serialMode.open("/dev/com3mode", 0);
//        if(serialMode.fd <= 0){
//            Toast.makeText(this, "文件打开失败， fd="+serialMode.fd, Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "文件打开成功", Toast.LENGTH_SHORT).show();
//        }
        File file = new File("dev/com3mode");
        if (file.exists()) {
            serialMode.fd = serialMode.open("/dev/com3mode", 0);
            Toast.makeText(this, "文件打开成功", Toast.LENGTH_SHORT).show();

            if ( serialMode.fd > 0) {

                serialmode = (Integer)SPUtils.getValue("serialmode", Integer.valueOf(0));
                if(serialmode==0)
                    Toast.makeText(this,"没有之前的值", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(this,"之前保存的值为："+serialmode, Toast.LENGTH_SHORT).show();
                    switch (serialmode){
                        case 1:serialMode.ioctl(serialMode.fd, TEST_RS232_CMD_APLEX, 0);break;
                        case 2:serialMode.ioctl(serialMode.fd, TEST_RS485_CMD_APLEX , 0);break;
                        case 3:serialMode.ioctl(serialMode.fd, TEST_RS422_CMD_APLEX , 0);break;
                        case 4:serialMode.ioctl(serialMode.fd, TEST_RS485_TERM_CMD_APLEX, 0);break;
                        case 5:serialMode.ioctl(serialMode.fd, TEST_RS422_TERM_CMD_APLEX, 0);break;
                        case 6:serialMode.ioctl(serialMode.fd, TEST_LOOPBACK_CMD_APLEX, 0);break;
                        default:break;
                    }
                }

                ButtonOnClick buttonOnClick = new ButtonOnClick();
                BtRS232.setOnClickListener(buttonOnClick);
                BtRS485.setOnClickListener(buttonOnClick);
                BtRS422.setOnClickListener(buttonOnClick);
                BtRS485Trml.setOnClickListener(buttonOnClick);
                BtRS422Trml.setOnClickListener(buttonOnClick);
                BtLoopBack.setOnClickListener(buttonOnClick);

            } else {
                /**
                 * 可能驱动节点文件不存在，或者权限不够
                 */
                new AlertDialog.Builder(this).setTitle("Error").setMessage("Missing read/write permission, trying to chmod the file.").setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        } ).show();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (serialMode == null) {
            serialMode = new SerialMode();
            serialMode.fd = serialMode.open("/dev/com3mode", 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serialMode != null)
            serialMode.close(serialMode.fd);
        serialMode = null;
    }

    class ButtonOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ButtonRS232:
                    BtId = 1;
                    Prompt = "ButtonRS232";
                    cmd = TEST_RS232_CMD_APLEX;

                    serialMode.ioctl(serialMode.fd, TEST_RS232_CMD_APLEX, 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                case R.id.ButtonRS485:
                    BtId = 2;
                    Prompt = "ButtonRS485";
                    cmd = TEST_RS485_CMD_APLEX;
                    serialMode.ioctl(serialMode.fd, TEST_RS485_CMD_APLEX , 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                case R.id.ButtonRS422:
                    BtId = 3;
                    Prompt = "ButtonRS422";
                    cmd = TEST_RS485_CMD_APLEX;
                    serialMode.ioctl(serialMode.fd, TEST_RS422_CMD_APLEX , 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                case R.id.ButtonRS485Trml:
                    BtId = 4;
                    Prompt = "ButtonRS485Trml";
                    serialMode.ioctl(serialMode.fd, TEST_RS485_TERM_CMD_APLEX, 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                case R.id.ButtonRS422Trml:
                    BtId = 5;
                    Prompt = "ButtonRS422Trml";
                    serialMode.ioctl(serialMode.fd, TEST_RS422_TERM_CMD_APLEX, 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                case R.id.ButtonLoopBack:
                    BtId = 6;
                    Prompt = "ButtonLoopBack";
                    serialMode.ioctl(serialMode.fd, TEST_LOOPBACK_CMD_APLEX, 0);
                    SPUtils.pushInt("serialmode", BtId);
                    break;
                default:break;
            }
            Toast.makeText(MainActivity.this, Prompt, Toast.LENGTH_SHORT).show();
        }

    }

}
