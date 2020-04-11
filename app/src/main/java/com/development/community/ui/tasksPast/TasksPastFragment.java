package com.development.community.ui.tasksPast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.development.community.R;

public class TasksPastFragment extends Fragment {

    private TasksPastViewModel tasksPastViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksPastViewModel =
                ViewModelProviders.of(this).get(TasksPastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks_up, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        tasksPastViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
