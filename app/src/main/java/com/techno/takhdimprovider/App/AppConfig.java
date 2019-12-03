package com.techno.takhdimprovider.App;



import com.techno.takhdimprovider.Interfaces.LoadInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.techno.takhdimprovider.App.Config.BASE_URL;


/**
 * Created by user1 on 11/16/2017.
 */

public class AppConfig {
    private static Retrofit retrofit = null;
    private static LoadInterface loadInterface = null;


    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static LoadInterface loadInterface() {
        if (loadInterface == null) {
            loadInterface = AppConfig.getClient().create(LoadInterface.class);
        }
        return loadInterface;
    }


}
