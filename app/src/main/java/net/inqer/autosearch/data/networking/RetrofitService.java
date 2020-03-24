package net.inqer.autosearch.data.networking;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.service.AccountClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private  static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://8fde093bb098.sn.mynetname.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
            .build();

    public AccountClient accountClient = retrofit.create(AccountClient.class);

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
