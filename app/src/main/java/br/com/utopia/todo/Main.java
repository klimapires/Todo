package br.com.utopia.todo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kleber on 11/10/2016.
 */

public class Main extends Application {
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}