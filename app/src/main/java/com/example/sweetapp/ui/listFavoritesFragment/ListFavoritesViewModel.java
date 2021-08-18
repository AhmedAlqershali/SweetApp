package com.example.sweetapp.ui.listFavoritesFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListFavoritesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListFavoritesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is List Favorites ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}