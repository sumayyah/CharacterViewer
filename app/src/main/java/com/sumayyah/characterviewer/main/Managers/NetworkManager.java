package com.sumayyah.characterviewer.main.Managers;

import com.sumayyah.characterviewer.main.Console;
import com.sumayyah.characterviewer.main.Model.RelatedTopic;
import com.sumayyah.characterviewer.main.Model.RelatedTopicTypeAdapterFactory;
import com.sumayyah.characterviewer.main.Network.NetworkUtils;
import com.sumayyah.characterviewer.main.Network.OkHttpClientProvider;
import com.sumayyah.characterviewer.main.Network.RetrofitAPIService;
import com.sumayyah.characterviewer.main.Network.RetrofitInstanceProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sumayyah on 8/16/16.
 */
public class NetworkManager {

    private NetworkOpsCompleteListener networkOpsCompleteListener;
    private NetworkUtils networkUtils;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit; //TODO inject all these

    public NetworkManager(NetworkUtils networkUtils, NetworkOpsCompleteListener networkOpsCompleteListener) {
        this.networkUtils = networkUtils;
        this.networkOpsCompleteListener = networkOpsCompleteListener;
    }

    public NetworkManager() {
        Console.log("Networkmanager", "Constructor");
        okHttpClient = new OkHttpClientProvider().getClient();
        retrofit = new RetrofitInstanceProvider(new RelatedTopicTypeAdapterFactory(), okHttpClient).getRetrofitInstance();
    }

    public Call<List<RelatedTopic>> createCallForAllData() {
        Console.log("Networkmanager", "Creating call for all data");

        Call<List<RelatedTopic>> call = retrofit.create(RetrofitAPIService.class).getAllData();
        return call;
    }

//    public void executeAPICall() {
//
//        Call<List<RelatedTopic>> call = networkUtils.getAPIService().getApiResponse();
//        call.enqueue(callback);
//    }
//
//    private Callback<List<RelatedTopic>> callback = new Callback<List<RelatedTopic>>() {
//        @Override
//        public void onResponse(Call<List<RelatedTopic>> call, Response<List<RelatedTopic>> response) {
//
//            Console.log("NetworkManager", "Completed call, got "+response.body().size()+ " items");
//            if(response.body().size() <= 0) {
//                networkUtils.showFailureDialog();
//                return;
//            }
//            DataManager.getInstance().populateList(response.body(), listCompleteListener);
//        }
//
//        @Override
//        public void onFailure(Call<List<RelatedTopic>> call, Throwable t) {
//            networkUtils.showFailureDialog();
//        }
//    };

    private DataManager.ListCompleteListener listCompleteListener = new DataManager.ListCompleteListener() {
        @Override
        public void onListPopulateComplete() {
            networkOpsCompleteListener.onNetworkOpsComplete();
        }
    };

    public interface NetworkOpsCompleteListener {
        void onNetworkOpsComplete();
    }
}
