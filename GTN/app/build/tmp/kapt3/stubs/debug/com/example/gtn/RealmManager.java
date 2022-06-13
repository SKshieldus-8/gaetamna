package com.example.gtn;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010J\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0012"}, d2 = {"Lcom/example/gtn/RealmManager;", "", "realm", "Lio/realm/Realm;", "(Lio/realm/Realm;)V", "getRealm", "()Lio/realm/Realm;", "create", "", "curdata", "Lcom/example/gtn/ImageDB;", "deleteByName", "name", "", "find", "findAll", "", "update", "app_debug"})
public final class RealmManager {
    @org.jetbrains.annotations.NotNull()
    private final io.realm.Realm realm = null;
    
    public RealmManager(@org.jetbrains.annotations.NotNull()
    io.realm.Realm realm) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.realm.Realm getRealm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.gtn.ImageDB find(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.gtn.ImageDB> findAll() {
        return null;
    }
    
    public final void create(@org.jetbrains.annotations.NotNull()
    com.example.gtn.ImageDB curdata) {
    }
    
    public final void update(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.example.gtn.ImageDB curdata) {
    }
    
    public final void deleteByName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
}