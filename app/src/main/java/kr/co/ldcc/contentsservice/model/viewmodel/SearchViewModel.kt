package kr.co.ldcc.contentsservice.model.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.ldcc.contentsservice.api.RetrofitClient
import kr.co.ldcc.contentsservice.model.vo.ImageResponse
import kr.co.ldcc.contentsservice.model.vo.ImageVo
import kr.co.ldcc.contentsservice.model.vo.VideoResponse
import kr.co.ldcc.contentsservice.model.vo.VideoVo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class SearchViewModel(applcation: Application) : AndroidViewModel(applcation) {
    var videoVos: MutableLiveData<ArrayList<VideoVo>>
    var imageVos: MutableLiveData<ArrayList<ImageVo>>

    init {
        videoVos = MutableLiveData()
        imageVos = MutableLiveData()
    }

    fun getAllVideoVo(searchText: String): MutableLiveData<ArrayList<VideoVo>> {
        var Call: Call<VideoResponse> = RetrofitClient.getInstance()
            .service.getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", searchText)

        Call!!.enqueue(object : Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {

                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        videoVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<VideoResponse?>, t: Throwable) {
                Log.d("retrofit onFailure", t.message)
            }
        })
        return videoVos
    }

    fun getAllImageVo(searchText: String): MutableLiveData<ArrayList<ImageVo>> {
        var Call: Call<ImageResponse> = RetrofitClient.getInstance()
            .service.getImage("KakaoAK f73ede515a6f7edcb9697b7af164db1d", searchText)

        Call!!.enqueue(object : Callback<ImageResponse> {

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {

                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        imageVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
                Log.d("retrofit onFailure", t.message)
            }
        })
        return imageVos
    }
}