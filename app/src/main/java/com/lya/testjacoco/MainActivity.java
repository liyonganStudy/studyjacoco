package com.lya.testjacoco;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            generateCoverReport();
        }
    }

    private void generateCoverReport() {
        OutputStream out = null;
        try {
            File coverageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "coverage"); //这里是写入覆盖率工具的 sd 卡上的目录
            if(!coverageDir.exists()){
                coverageDir.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String coverageFileName = BuildConfig.VERSION_NAME + "_" + sdf.format(new java.util.Date())+".ec";
            File file = new File(coverageDir,coverageFileName);
            out = new FileOutputStream(file, false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT").getMethod("getAgent").invoke(null);
            out.write((byte[])agent.getClass().getMethod("getExecutionData", boolean.class).invoke(agent, true));
            //第二个bool 参数是指，获得数据后，是否reset data，重新生成覆盖数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
