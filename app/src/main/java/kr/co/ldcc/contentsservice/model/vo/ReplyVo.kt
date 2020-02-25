package kr.co.ldcc.contentsservice.model.vo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reply")
class ReplyVo(
    var userId: String,
    var profile: String,
    var comment: String,
    var contentId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}