package kr.co.ldcc.contentsservice.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

@Dao
interface ReplyDao {
    @Query("SELECT * FROM reply WHERE contentId = :contentId")
    fun getAll(contentId : String) : LiveData<List<ReplyVo>>

    @Insert
    fun insert(replyVo: ReplyVo)
}