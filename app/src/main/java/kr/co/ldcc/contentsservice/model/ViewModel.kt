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

    var subjects: ArrayList<String>

    var videoVos: MutableLiveData<ArrayList<VideoVo>>

    init {
        videoVos = MutableLiveData()
        subjects = arrayListOf("비디오","이미지","비디오/이미지")
    }


    fun getAll() {
        Log.d("test","getAll")
        Log.d("test",videoVos.value.toString())
        var call: Call<VideoResponse> = RetrofitClient.getInstance()
            .service.getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", "zico")
        if(call==null){
            Log.d("test", "call이 null이다")
        }else{
            Log.d("test", "call이 null이아니다")
        }
//      var newData: MutableLiveData<VideoResponse> = MutableLiveData()
        call!!.enqueue(object : Callback<VideoResponse> {

            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                Log.d("test","onResponse")

                if (response.isSuccessful()) {
                    viewModelScope.launch(Dispatchers.IO) {
//                        newData.postValue(response.body())
                        Log.d("test",response.body()!!.documents.toString())
                        videoVos.postValue(response.body()!!.documents)

                    }
                }
            }

            override fun onFailure(call: Call<VideoResponse?>, t: Throwable) {
                Log.d("test",t.message)
            }
        })
    }
}