package kr.co.ldcc.contentsservice.model

import androidx.lifecycle.MutableLiveData


data class LayoutVo(
   var videoVos: MutableLiveData<ArrayList<VideoVo>>,
   var imageVos: MutableLiveData<ArrayList<ImageVo>>
)