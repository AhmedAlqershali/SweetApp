package com.example.sweetapp.model;

public class ChaletListIteamModel {

    public String chaletId, name_Chalet, image, address, num_Of_Hours, phone, chaletOwnerId;
    private Float evaluation_Chalet;
    private int price;



    public ChaletListIteamModel() {
    }

//    public ChaletListIteamModel(String chaletId) {
//        this.chaletId = chaletId;
//    }


    public ChaletListIteamModel(String chaletId, String name_Chalet, String image, int price, String address, String num_Of_Hours, String phone, String chaletOwnerId, Float evaluation_Chalet) {
        this.chaletId = chaletId;
        this.name_Chalet = name_Chalet;
        this.image = image;
        this.price = price;
        this.address = address;
        this.num_Of_Hours = num_Of_Hours;
        this.phone = phone;
        this.chaletOwnerId = chaletOwnerId;
        this.evaluation_Chalet = evaluation_Chalet;
    }

    public ChaletListIteamModel(String chaletId, String name_Chalet, String image, String address, String num_Of_Hours, String phone, String chaletOwnerId) {
        this.chaletId = chaletId;
        this.name_Chalet = name_Chalet;
        this.image = image;
        this.address = address;
        this.num_Of_Hours = num_Of_Hours;
        this.phone = phone;
        this.chaletOwnerId = chaletOwnerId;
    }

    public String getChaletId() {
        return chaletId;
    }

    public void setChaletId(String chaletId) {
        this.chaletId = chaletId;
    }

    public String getChaletOwnerId() {
        return chaletOwnerId;
    }

    public void setChaletOwnerId(String chaletOwnerId) {
        this.chaletOwnerId = chaletOwnerId;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_Chalet() {
        return name_Chalet;
    }

    public void setName_Chalet(String name_Chalet) {
        this.name_Chalet = name_Chalet;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getEvaluation_Chalet() {
        return evaluation_Chalet;
    }

    public void setEvaluation_Chalet(Float evaluation_Chalet) {
        this.evaluation_Chalet = evaluation_Chalet;
    }

    public String getNum_Of_Hours() {
        return num_Of_Hours;
    }

    public void setNum_Of_Hours(String num_Of_Hours) {
        this.num_Of_Hours = num_Of_Hours;
    }

}
