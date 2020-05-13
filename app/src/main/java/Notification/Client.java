package Notification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * was originally created to get data structures other than JSONs, not in use very much
 */
public class Client {

    private static Retrofit retrofit = null;

    /**
     * gets the client when necessary
     */
    public static Retrofit getClient(String url){
        if(retrofit == null)
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }
}
