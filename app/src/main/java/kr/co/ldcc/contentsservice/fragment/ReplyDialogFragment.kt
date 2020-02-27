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

    val activity: Activity
    val replyViewModel: ReplyViewModel
    val contentId: String

    lateinit var sharedPreferences: SharedPreferences
    lateinit var userId: String
    lateinit var profile: String

    init {
        this.activity = activity
        this.contentId = contentId
        this.replyViewModel = replyViewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // init SharedPreferences
        initSharedPreferences()

        // inflate R.layout.reply_dialog
        val view: View = getRelplyDialogView()

        // get AlertDialog.Builder
        val builder = getAlertDialogBuilder(view)

        return builder.create()
    }

    private fun getAlertDialogBuilder(view: View): AlertDialog.Builder {
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
        return builder
    }

    private fun getRelplyDialogView(): View {
        val view: View = activity.layoutInflater.inflate(R.layout.reply_dialog, null)
        return view
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
