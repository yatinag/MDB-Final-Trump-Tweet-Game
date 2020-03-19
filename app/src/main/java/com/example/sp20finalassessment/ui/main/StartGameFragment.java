package com.example.sp20finalassessment.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sp20finalassessment.GameActivity;
import com.example.sp20finalassessment.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartGameFragment extends Fragment {

    StartGameFragment () {
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        textView.setText("Start Game on this tab");
        final Button startBtn = root.findViewById(R.id.startBtn);
        startBtn.setText("Start Game here");
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GameActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
}