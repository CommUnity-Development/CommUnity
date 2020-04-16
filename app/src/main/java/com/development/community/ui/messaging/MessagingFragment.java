package com.development.community.ui.messaging;

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

public class MessagingFragment extends Fragment {

    private MessagingViewModel messagingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagingViewModel =
                ViewModelProviders.of(this).get(MessagingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);
        final TextView textView = root.findViewById(R.id.text_messaging);
        messagingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
