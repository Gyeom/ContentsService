package kr.co.ldcc.contentsservice.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo

@Dao
interface BookmarkDao{
    @Query("SELECT * FROM bookmark WHERE userId= :userId")
    fun getAll(userId: String): LiveData<List<BookmarkVo>>

    @Query("SELECT * FROM bookmark WHERE userId= :userId AND contentId= :contentId")
    fun getOne(userId: String, contentId: String): LiveData<BookmarkVo>

    @Insert
    fun insert(bookMarkVo: BookmarkVo)

    @Query("DELETE FROM bookmark WHERE userId= :userId AND contentId= :contentId")
    fun delete(userId: String, contentId: String)
}