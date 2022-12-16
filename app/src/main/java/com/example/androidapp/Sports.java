package com.example.androidapp;

public class Sports {

    String name,id;
    int imageId,emploi;
    String prix ;

    public Sports(String id,String name,int imageId,String prix,int emploi) {
        this.name = name;
        this.prix=prix;
        this.imageId=imageId;
        this.id =id;
        this.emploi=emploi;
    }
}
