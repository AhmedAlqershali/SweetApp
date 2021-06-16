package com.example.sweetapp.ui.accountManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Account Management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}