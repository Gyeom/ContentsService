package kr.co.ldcc.contentsservice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.ldcc.contentsservice.model.dao.BookmarkDao
import kr.co.ldcc.contentsservice.model.dao.ReplyDao
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

@Database(entities = [ReplyVo::class, BookmarkVo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun replyDao(): ReplyDao
    abstract fun bookmarkDao(): BookmarkDao
}