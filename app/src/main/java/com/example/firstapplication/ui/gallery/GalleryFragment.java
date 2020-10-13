package com.example.firstapplication.ui.gallery;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private Button buttonON, buttonOFF, buttonDisc, buttonList;
    private Button btnConnect, btnSend, btnQuit;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ListView listView;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private InputStream inStream = null;
    private ReceiveThread rThread = null;  //数据接收线程
    String ReceiveData = "";
    MyHandler handler;
    TextView statusLabel;
    EditText etReceived, etSend;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "20:16:07:26:18:46";
    private ArrayList macAddressList = new ArrayList();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        buttonON = (Button) view.findViewById(R.id.buttonOn);
        buttonList = (Button) view.findViewById(R.id.buttonList);
        buttonDisc = (Button) view.findViewById(R.id.buttonDisc);
        btnConnect = (Button) view.findViewById(R.id.btnConnect);

        listView = (ListView) view.findViewById(R.id.listView1);

        bAdapter = BluetoothAdapter.getDefaultAdapter();
        buttonON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bAdapter == null) {
                    Toast.makeText(getContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                } else {
                    if (!bAdapter.isEnabled()) {
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
                        Toast.makeText(getContext(), "Bluetooth Turned ON", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!bAdapter.isEnabled()) {
                    bAdapter.enable();
                }
                bAdapter.startDiscovery();
                //text2.setText("正在搜索...");
            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bAdapter == null) {
                    Toast.makeText(getContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                } else {
                    Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
                    ArrayList list = new ArrayList();
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String macAddress = device.getAddress();
                            list.add("Name: " + deviceName + "MAC Address: " + macAddress);
                            macAddressList.add(macAddress);
                        }
                        aAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(aAdapter);
                    }
                }
            }
        });

        handler = new MyHandler();

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断蓝牙是否打开
                if (!bAdapter.isEnabled()) {
                    bAdapter.enable();
                }
                bAdapter.startDiscovery();

                //创建连接
                new ConnectTask().execute(address);

            }
        });

        return view;
    }

    //连接蓝牙设备的异步任务
    class ConnectTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            BluetoothDevice device = bAdapter.getRemoteDevice(params[0]);

            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                btSocket.connect();
                Log.e("error", "ON RESUME: BT connection established, data transfer link open.");
            } catch (IOException e) {
                try {
                    btSocket.close();
                    return "Socket 创建失败";
                } catch (IOException e2) {
                    Log.e("error", "ON RESUME: Unable to close socket during connection failure", e2);
                    return "Socket 关闭失败";
                }
            }
            //取消搜索
            bAdapter.cancelDiscovery();

            try {
                outStream = btSocket.getOutputStream();

            } catch (IOException e) {
                Log.e("error", "ON RESUME: Output stream creation failed.", e);
                return "Socket 流创建失败";
            }


            return "蓝牙连接正常,Socket 创建成功";
        }

        @Override    //这个方法是在主线程中运行的，所以可以更新界面
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //连接成功则启动监听
            rThread = new ReceiveThread();
            rThread.start();
            statusLabel.setText(result);
            super.onPostExecute(result);
        }
    }

    //发送数据到蓝牙设备的异步任务
    class SendInfoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            statusLabel.setText(result);

            //将发送框清空
            etSend.setText("");
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            if (btSocket == null) {
                return "还没有创建连接";
            }

            if (arg0[0].length() > 0)//不是空白串
            {
                //String target=arg0[0];

                byte[] msgBuffer = arg0[0].getBytes();

                try {
                    //  将msgBuffer中的数据写到outStream对象中
                    outStream.write(msgBuffer);

                } catch (IOException e) {
                    Log.e("error", "ON RESUME: Exception during write.", e);
                    return "发送失败";
                }

            }

            return "发送成功";
        }

    }


    //从蓝牙接收信息的线程
    class ReceiveThread extends Thread {

        String buffer = "";

        @Override
        public void run() {

            while (btSocket != null) {
                //定义一个存储空间buff
                byte[] buff = new byte[1024];
                try {
                    inStream = btSocket.getInputStream();
                    System.out.println("waitting for instream");
                    inStream.read(buff); //读取数据存储在buff数组中
//                        System.out.println("buff receive :"+buff.length);
                    processBuffer(buff, 1024);
                    //System.out.println("receive content:"+ReceiveData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processBuffer(byte[] buff, int size) {
            int length = 0;
            for (int i = 0; i < size; i++) {
                if (buff[i] > '\0') {
                    length++;
                } else {
                    break;
                }
            }

//			System.out.println("receive fragment size:"+length);

            byte[] newbuff = new byte[length];  //newbuff字节数组，用于存放真正接收到的数据

            for (int j = 0; j < length; j++) {
                newbuff[j] = buff[j];
            }

            ReceiveData = ReceiveData + new String(newbuff);
            Log.e("Data", ReceiveData);
//			System.out.println("result :"+ReceiveData);
            Message msg = Message.obtain();
            msg.what = 1;
            handler.sendMessage(msg);  //发送消息:系统会自动调用handleMessage( )方法来处理消息
        }
    }


    //更新界面的Handler类
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    etReceived.setText(ReceiveData);
                    break;
            }
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/


}
