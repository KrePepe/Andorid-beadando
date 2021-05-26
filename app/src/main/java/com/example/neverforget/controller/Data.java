package com.example.neverforget.controller;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.neverforget.MainActivity;
import com.example.neverforget.model.Password;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import static android.content.ContentValues.TAG;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Data {

    //TODO: Make key random for each user
    public static final String MyKey = "gdsf255ohp235erh235ozpkdf";

    public static ArrayList<Password> ITEMS = new ArrayList<Password>();

    //Test data
    static{
        AddItem(new Password("Facebook", "biztonsagosJelszo321", "Név"));
        AddItem(new Password("Twitter", "megBiztonsagosabbJelszo123", "Név1"));
        AddItem(new Password("YouTube", "legbiztonsagosabbJelszo123", "Név2"));
        for (int i = 1; i <= 12; i++) {
            AddItem(new Password("Név "+i,"jelszó"+i,"felhasznaló_"+i));
        }
    }

    public static Password GetPasswordById(int id){
        return ITEMS.get(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void AddItem(final Password password) {

        ITEMS.removeIf(new Predicate<Password>() {
            @Override
            public boolean test(Password s) {
                return s.getName().equals(password.getName());
            }
        });
        Log.d(TAG, "additem: "+password.getPassword());
        password.setPassword(Encryption.encrypt(password.getPassword(),MyKey));
        Log.d(TAG, "AddItem: "+password.getPassword());
        Log.d(TAG, "AddItem: decrypt: "+Encryption.decrypt(password.getPassword(), MyKey ));
        ITEMS.add(password);

        ITEMS.sort(new Comparator<Password>() {
            @Override
            public int compare(Password o1, Password o2) {
                if(o1.getId() > o2.getId())
                    return 1;
                if(o1.getId() < o2.getId())
                    return -1;
                return 0;
            }
        });
        SaveData();
    }

    public static void RemoveItem(Password pass){
        ITEMS.remove(pass);
    }

    public static void SaveData() {
        Context context = MainActivity.getAppContext();

        File file = new File(context.getFilesDir(), "data");
        try(FileOutputStream fos = context.openFileOutput("data", Context.MODE_PRIVATE)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ITEMS);
            oos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void LoadData() {
        Context context = MainActivity.getAppContext();
        Log.d(TAG, "LoadData: ");
        try(FileInputStream fis = context.openFileInput("data")){
            Log.d(TAG, "LoadData: "+fis.toString());
            ObjectInputStream ois = new ObjectInputStream(fis);
            ITEMS = (ArrayList<Password>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
