package com.example.sweetapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Favorites;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "ChaletDB.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void addTOFavorites(Favorites favorites)
    {
        SQLiteDatabase dbb = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(" +
                "ChaletId,NameChalet,Image,Address,NumOfHours,Phone,ChaletOwnerId) " +
                "VALUES('%s','%s','%s','%s','%s','%s','%s');",
                favorites.getChaletId(),
                favorites.getName_Chalet(),
                favorites.getImage(),
                favorites.getAddress(),
                favorites.getNum_Of_Hours(),
                favorites.getPhone(),
                favorites.getChaletOwnerId());
        dbb.execSQL(query);
    }

    public void removeFromFavorites(String chaletId)
    {
        SQLiteDatabase dbb = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE chaletId='%s';",chaletId);
        dbb.execSQL(query);
    }
    public boolean isFavorites(String chaletId) {

        SQLiteDatabase dbb = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE chaletId='%s';",chaletId);
        Cursor cursor = dbb.rawQuery(query,null);
        if (cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }




    public ArrayList<Favorites> getFavorites() {
        ArrayList<Favorites> modelArrayList =  new ArrayList<>();
        SQLiteDatabase dbb = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites");
        Cursor cursor = dbb.rawQuery(query,null);
        if (cursor != null && cursor.moveToFirst())
        {
            do {
                String chaletId = cursor.getString(cursor.getColumnIndex("ChaletId"));
                String name_Chalet = cursor.getString(cursor.getColumnIndex("NameChalet"));
                String image =cursor.getString(cursor.getColumnIndex("Image"));
                String address = cursor.getString(cursor.getColumnIndex("Address"));
                String num_Of_Hours = cursor.getString(cursor.getColumnIndex("NumOfHours"));
                String phone = cursor.getString(cursor.getColumnIndex("Phone"));
                String chaletOwnerId = cursor.getString(cursor.getColumnIndex("ChaletOwnerId"));
//                String evaluation_Chalet = cursor.getString(cursor.getColumnIndex("Evaluation_Chalet"));
//                String price = cursor.getString(cursor.getColumnIndex("Price"));
//                cursor.close();
                Favorites model =new Favorites(chaletId, name_Chalet, image, address, num_Of_Hours, phone, chaletOwnerId);
                modelArrayList.add(model);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return modelArrayList;
    }


}