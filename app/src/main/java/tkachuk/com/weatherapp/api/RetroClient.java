package tkachuk.com.weatherapp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static ApiService service;
    private static final String URL_SOURCE  = "http://api.openweathermap.org/data/2.5/";

    public static ApiService getRetroClient() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_SOURCE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createDefaultOkHttpClient())
                    .build();

            service = retrofit.create(ApiService.class);
        }
        return service;
    }

    private static OkHttpClient createDefaultOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
    }
}
