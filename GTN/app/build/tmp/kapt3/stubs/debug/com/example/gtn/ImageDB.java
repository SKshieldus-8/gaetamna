package com.example.gtn;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\b\u00a8\u0006\u000f"}, d2 = {"Lcom/example/gtn/ImageDB;", "Lio/realm/RealmObject;", "()V", "imageName", "", "getImageName", "()Ljava/lang/String;", "setImageName", "(Ljava/lang/String;)V", "imageType", "getImageType", "setImageType", "personalData", "getPersonalData", "setPersonalData", "app_debug"})
public class ImageDB extends io.realm.RealmObject {
    @org.jetbrains.annotations.NotNull()
    @io.realm.annotations.PrimaryKey()
    private java.lang.String imageName = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String imageType = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String personalData = "";
    
    public ImageDB() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImageName() {
        return null;
    }
    
    public final void setImageName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImageType() {
        return null;
    }
    
    public final void setImageType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPersonalData() {
        return null;
    }
    
    public final void setPersonalData(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
}