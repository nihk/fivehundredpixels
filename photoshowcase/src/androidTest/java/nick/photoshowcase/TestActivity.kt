package nick.photoshowcase

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import nick.photoshowcase.ui.OnPhotoClickedListener

class TestActivity
    : AppCompatActivity()
    , OnPhotoClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar)

        val viewId = 1234
        val layout = FrameLayout(this)
            .also { it.id = viewId }
        setContentView(layout)

        supportFragmentManager.beginTransaction()
            .add(viewId, (application as TestApplication).photoShowcaseFragment, null)
            .commit()
    }

    override fun onPhotoClicked(id: Long) {}
}