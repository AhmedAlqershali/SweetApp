package com.example.sweetapp.ui.listOfChaletsTenant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListOfChaletsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListOfChaletsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is List Of Chalets ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}