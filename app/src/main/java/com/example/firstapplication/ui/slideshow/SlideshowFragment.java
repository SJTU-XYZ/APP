package com.example.firstapplication.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapplication.R;
import com.example.firstapplication.control.Appliance;
import com.example.firstapplication.control.ApplianceType;
import com.example.firstapplication.control.ControlAppliance;
import com.example.firstapplication.control.Mode_e;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private EditText inputAppliance;
    private CreateAddAppDialog dialog;
    private ControlAppliance controlApp;
    private View.OnClickListener onClickListener;
    private Button addBtn;

    private ApplianceType tempType;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        addBtn = (Button)view.findViewById(R.id.btn_add);
        controlApp = new ControlAppliance();


        //spinner.setVisibility(View.VISIBLE);//设置默认显示

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_save) {
                            String name = dialog.text_name.getText().toString().trim();
                            String power = dialog.text_power.getText().toString().trim();
                            Appliance app = new Appliance(name, Float.parseFloat(power), dialog.type, Mode_e.AUTO);
                            controlApp.Add(app);
                            if(dialog.text_name != null && dialog.text_power != null) {
                                dialog.cancel();
                            }
                        }
                    }
                };

                dialog = new CreateAddAppDialog(getActivity(), R.style.AppTheme, onClickListener);
                dialog.show();
            }
        });
        return view;
    }
}