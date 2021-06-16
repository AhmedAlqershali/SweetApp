package com.example.sweetapp.model;

public class Common {
    public static ChaletListIteamModel chaletListIteamModel;
    public static String convertCodeToStatus(String code){
        if (code.equals("0"))
            return "قبول الطلب";
        else  if (code.equals("1"))
            return "رفض الطلب";

        return "الشالية غير متاح حاليا";
    }
}
