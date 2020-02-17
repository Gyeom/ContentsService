package kr.co.ldcc.contentsservice.model

import android.app.Application
import android.util.Log
import android.widget.TextView
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

    var layoutVo : MutableLiveData<LayoutVo>
    var videoVos : MutableLiveData<ArrayList<VideoVo>>
    var imageVos : MutableLiveData<ArrayList<ImageVo>>

    init {
        layoutVo = MutableLiveData()
        videoVos = MutableLiveData()
        imageVos = MutableLiveData()
    }

    fun getAll() : MutableLiveData<LayoutVo> {
        var vCall: Call<VideoResponse> = RetrofitClient.getInstance()
            .service.getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")

        vCall!!.enqueue(object : Callback<VideoResponse> {

            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        Log.d("test",response.body()!!.documents.toString())
                        videoVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<VideoResponse?>, t: Throwable) {
            }
        })

        var iCall: Call<ImageResponse> = RetrofitClient.getInstance()
            .service.getImage("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")

        iCall!!.enqueue(object : Callback<ImageResponse> {

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        Log.d("test",response.body()!!.documents.toString())
                        imageVos.postValue(response.body()!!.documents)
                    }
                }
            }

            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
            }
        })
      Thread(Runnable { run(){
          layoutVo.postValue(LayoutVo(videoVos, imageVos))
      } })

        return layoutVo
    }
}