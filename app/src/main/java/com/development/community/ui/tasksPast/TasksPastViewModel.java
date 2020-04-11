package com.development.community.ui.tasksPast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TasksPastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TasksPastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TasksPast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}