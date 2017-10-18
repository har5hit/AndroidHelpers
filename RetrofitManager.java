package helpers;

import com.bigrattle.colgatepds.BuildConfig;

import java.io.IOException;

import interfaces.RetrofitService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harshit on 20/1/17.
 */

public class RetrofitManager {


    private static RetrofitManager instance;
    private Retrofit retrofit;
    private RetrofitService service;
    private OkHttpClient client;



    public RetrofitManager() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(loggingInterceptor);

        Retrofit.Builder builer= new Retrofit.Builder()
                .baseUrl(Constants.stagUrl)
                .addConverterFactory(GsonConverterFactory.create());




        final String token=SharedPrefs.getPrefs().getString("token");
        if(!token.isEmpty())
        {
            Utils.printreq("token found");
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer "+token)
                            .addHeader("Accept", "application/x.colgateapi.v1+json"); // <-- this is the important line


                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }else {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/x.colgateapi.v1+json"); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        client=httpClient.build();
        builer.client(client);

        retrofit=builer.build();

        service=retrofit.create(RetrofitService.class);
    }


    public static RetrofitManager getInstance() {

        if(instance==null)
        {
            instance=new RetrofitManager();
        }
        return instance;
    }


    public static Retrofit getRetrofit() {
        if(instance==null)
        {
            instance=new RetrofitManager();
        }
        return instance.retrofit;
    }

    public static RetrofitService getService() {

        if(instance==null)
        {
            instance=new RetrofitManager();
        }
        return instance.service;
    }

    public static void reinit() {
        Utils.printreq("retrofit nulling");
        instance=new RetrofitManager();
    }

    public static OkHttpClient getClient() {

        if(instance==null)
        {
            instance=new RetrofitManager();
        }
        return instance.client;
    }



}
