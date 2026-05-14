package com.cbtis226.docit.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCliente {

    // cuando los del otro equipo prendan su flask cambien esto
    // si lo corren en su comp y prueban en emulador es 10.0.2.2
    public static String url_base = "http://10.0.2.2:5000/";

    private static Retrofit retro;

    public static Retrofit get(){
        if(retro == null){
            HttpLoggingInterceptor log = new HttpLoggingInterceptor();
            log.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient cli = new OkHttpClient.Builder()
                    .addInterceptor(log)
                    .build();
            retro = new Retrofit.Builder()
                    .baseUrl(url_base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(cli)
                    .build();
        }
        return retro;
    }

    public static ApiDocit api(){
        return get().create(ApiDocit.class);
    }
}