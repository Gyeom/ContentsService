package kr.co.ldcc.contentsservice.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kr.co.ldcc.contentsservice.db.AppDatabase
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo
import kr.co.ldcc.contentsservice.model.vo.ReplyVo
import java.lang.Exception

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "ec3"
    ).build()


    fun insert(bookmarkVo: BookmarkVo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.bookmarkDao().insert(bookmarkVo)
        }
    }

    fun getAll(userId: String): LiveData<List<BookmarkVo>> {
        return db.bookmarkDao().getAll(userId)
    }

    fun getOne(userId: String, contentId: String): LiveData<BookmarkVo> {
        return db.bookmarkDao().getOne(userId, contentId)
    }

    fun delete(userId: String, contentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.bookmarkDao().delete(userId, contentId)
        }
    }
}