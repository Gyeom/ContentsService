package kr.co.ldcc.contentsservice.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.ldcc.contentsservice.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class ViewModel(applcation: Application) : AndroidViewModel(applcation) {
    var videoVos: MutableLiveData<ArrayList<VideoVo>>
    var imageVos: MutableLiveData<ArrayList<ImageVo>>

    init {
        videoVos = MutableLiveData()
        imageVos = MutableLiveData()
    }

    fun getAllVideoVo(): MutableLiveData<ArrayList<VideoVo>> {
        var Call: Call<VideoResponse> = RetrofitClient.getInstance()
            .service.getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")
        Log.d("test", "vCall")
        Call!!.enqueue(object : Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                Log.d("test", "onResponse")

                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        videoVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<VideoResponse?>, t: Throwable) {
                Log.d("test", "fail")
                Log.d("test", t.message)

            }
        })
        return videoVos
    }

    fun getAllImageVo(): MutableLiveData<ArrayList<ImageVo>> {
        var Call: Call<ImageResponse> = RetrofitClient.getInstance()
            .service.getImage("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")

        Call!!.enqueue(object : Callback<ImageResponse> {

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                Log.d("test", "onResponse")

                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        Log.d("test", response.body()!!.documents.toString())
                        imageVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
                Log.d("test", "fail")
                Log.d("test", t.message)
            }
        })
        return imageVos
    }
//
//    fun getAll(): MutableLiveData<ArrayList<Any>> {
//        var vCall: Call<VideoResponse> = RetrofitClient.getInstance()
//            .service.getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")
//        Log.d("test","vCall")
//        vCall!!.enqueue(object : Callback<VideoResponse> {
//            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
//                Log.d("test","onResponse")
//
//                if (response.isSuccessful()) {
//                    viewModelScope.launch(Dispatchers.IO) {
//                        Log.d("test", response.body()!!.documents.toString())
//                        videoVos.postValue(response.body()!!.documents)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<VideoResponse?>, t: Throwable) {
//                Log.d("test","fail")
//                Log.d("test",t.message)
//
//            }
//        })
//
//        var iCall: Call<ImageResponse> = RetrofitClient.getInstance()
//            .service.getImage("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")
//
//        iCall!!.enqueue(object : Callback<ImageResponse> {
//
//            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
//                Log.d("test","onResponse")
//
//                if (response.isSuccessful()) {
//                    viewModelScope.launch(Dispatchers.IO) {
//                        Log.d("test", response.body()!!.documents.toString())
//                        imageVos.postValue(response.body()!!.documents)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
//                Log.d("test","fail")
//                Log.d("test",t.message)
//
//            }
//        })
//        Thread.sleep(10000)
//        layoutVos.postValue(arrayListOf(videoVos, imageVos))
//        return layoutVos
//    }
}