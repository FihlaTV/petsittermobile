package zekisanmobile.petsitter.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetModule {

    //region Members
    String baseUrl;
    //endregion

    //region Constructors
    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    //endregion

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return client;
    }

    @Provides
    @Singleton
    ObjectMapper provideMapper(){
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(ObjectMapper mapper, OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .baseUrl(baseUrl)
                .client(client)
                .build();
        return retrofit;
    }
}
