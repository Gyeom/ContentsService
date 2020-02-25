package kr.co.ldcc.contentsservice.fragment


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_reply_dialog.view.*
import kotlinx.android.synthetic.main.reply_dialog.*
import kotlinx.android.synthetic.main.reply_dialog.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.model.viewmodel.ReplyViewModel
import kr.co.ldcc.contentsservice.model.vo.ReplyVo


class ReplyDialogFragment(activity: Activity, contentId: String, replyViewModel: ReplyViewModel) :
    DialogFragment() {
    var replyViewModel: ReplyViewModel
    lateinit var sharedPreferences: SharedPreferences
    var contentId: String

    var userId: String = "사용자 아이디"
    var profile: String = "사용자 프로필"
    val activity: Activity

    init {
        this.activity = activity
        this.contentId = contentId
        this.replyViewModel = replyViewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // SharedPreferences 불러오기
        initSharedPreferences()

        // 다이얼로그를 통해 보여줄 뷰를 생성한다.
        val view: View = activity.layoutInflater.inflate(R.layout.reply_dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("댓글작성")
            .setView(view)
            .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                replyViewModel.insert(
                    ReplyVo(
                        userId,
                        profile,
                        view.editTextDialog.text.toString(),
                        contentId
                    )
                )
            }
            .setNegativeButton("취소", null)
        return builder.create()
    }

    private fun initSharedPreferences() {
        sharedPreferences = activity.getSharedPreferences("sFile", AppCompatActivity.MODE_PRIVATE)

        sharedPreferences.getString("userId", "")?.let {
            userId = it
        }
        sharedPreferences.getString("profile", "")?.let {
            profile = it
        }
    }
}
