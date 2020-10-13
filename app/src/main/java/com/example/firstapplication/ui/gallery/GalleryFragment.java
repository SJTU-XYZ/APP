package com.example.firstapplication.ui.gallery;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapplication.R;

import java.util.ArrayList;
import java.util.Set;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private Button On, Off, Visible, list;
    private BluetoothAdapter bAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        On = (Button) view.findViewById(R.id.button1);
        Off = (Button) view.findViewById(R.id.button2);
        Visible = (Button) view.findViewById(R.id.button3);
        list = (Button) view.findViewById(R.id.button4);

        lv = (ListView) view.findViewById(R.id.listView1);

        bAdapter = BluetoothAdapter.getDefaultAdapter();
        On.setOnClickListener(new View.OnClickListener() {
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
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bAdapter.disable();
                Toast.makeText(getContext(), "Bluetooth Turned OFF", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
/*
    public void on(View view) {
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getActivity(), "Turned on"
                    , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Already on",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void list(View view) {
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : pairedDevices)
            list.add(bt.getName());

        Toast.makeText(getActivity(), "Showing Paired Devices",
                Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.fragment_gallery);
        lv.setAdapter(adapter);

    }

    public void off(View view) {
        BA.disable();
        Toast.makeText(getActivity(), "Turned off",
                Toast.LENGTH_LONG).show();
    }

    public void visible(View view) {
        Intent getVisible = new Intent(BluetoothAdapter.
                ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);

    }

 */
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/


}
