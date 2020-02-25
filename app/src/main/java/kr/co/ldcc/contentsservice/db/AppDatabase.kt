package kr.co.ldcc.contentsservice.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.ldcc.contentsservice.model.dao.ReplyDao
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

@Database(entities = [ReplyVo::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun replyDao(): ReplyDao
}