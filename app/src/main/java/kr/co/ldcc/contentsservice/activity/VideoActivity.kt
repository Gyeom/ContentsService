package kr.co.ldcc.contentsservice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.activity_image.imageView
import kotlinx.android.synthetic.main.activity_video.*
import kr.co.ldcc.contentsservice.R

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val intent : Intent = getIntent()
        var thumbnail : String = intent.getStringExtra("thumbnail")
        var displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics;
        var width: Int = displayMetrics.widthPixels;
        var height: Int = displayMetrics.heightPixels;

        Glide.with(applicationContext)
            .load(thumbnail)
            .apply(RequestOptions().override(width, height / 3))
            .into(imageView)
    }
}
