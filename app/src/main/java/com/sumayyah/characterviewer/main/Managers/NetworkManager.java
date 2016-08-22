package com.sumayyah.characterviewer.main.Managers;

import com.sumayyah.characterviewer.main.Console;
import com.sumayyah.characterviewer.main.Model.APIResponse;
import com.sumayyah.characterviewer.main.Network.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sumayyah on 8/16/16.
 */
public class NetworkManager {

    private NetworkOpsCompleteListener networkOpsCompleteListener;
    private NetworkUtils networkUtils;

    public NetworkManager(NetworkUtils networkUtils, NetworkOpsCompleteListener networkOpsCompleteListener) {
        this.networkUtils = networkUtils;
        this.networkOpsCompleteListener = networkOpsCompleteListener;
    }

    public void executeAPICall() {

        Call<APIResponse> call = networkUtils.getAPIService().getApiResponse();
        call.enqueue(callback);
    }

    private Callback<APIResponse> callback = new Callback<APIResponse>() {
        @Override
        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
            APIResponse apiResponse = response.body();

            if(apiResponse == null) {
                networkUtils.showFailureDialog();
                return;
            }
            DataManager.getInstance().populateList(apiResponse, listCompleteListener);
        }

        @Override
        public void onFailure(Call<APIResponse> call, Throwable t) {
            networkUtils.showFailureDialog();
        }
    };

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