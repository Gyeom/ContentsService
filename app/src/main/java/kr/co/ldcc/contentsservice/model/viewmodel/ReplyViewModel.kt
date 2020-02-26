package kr.co.ldcc.contentsservice.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.ldcc.contentsservice.db.AppDatabase
import kr.co.ldcc.contentsservice.model.vo.ReplyVo
import java.lang.Exception

class ReplyViewModel(application: Application) : AndroidViewModel(application) {

    val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "ec3"
    ).build()


    fun insert(replyVo: ReplyVo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.replyDao().insert(replyVo)
        }
    }

    fun getAll(contentId: String): LiveData<List<ReplyVo>> {
        return db.replyDao().getAll(contentId)
    }
}