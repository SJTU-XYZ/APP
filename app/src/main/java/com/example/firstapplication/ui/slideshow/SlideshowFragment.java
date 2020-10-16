package com.example.firstapplication.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        addBtn = (Button)view.findViewById(R.id.btn_add);
        controlApp = new ControlAppliance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_save:
                                String name = dialog.text_name.getText().toString().trim();
                                String power = dialog.text_power.getText().toString().trim();
                                String type = dialog.text_type.getText().toString().trim();
                                Appliance app = new Appliance(name, Float.parseFloat(power), ApplianceType.Necessary, Mode_e.AUTO);
                                controlApp.Add(app);
                                dialog.cancel();
                                break;
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