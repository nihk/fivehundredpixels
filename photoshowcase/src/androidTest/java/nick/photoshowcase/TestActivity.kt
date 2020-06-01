package nick.photoshowcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nick.photoshowcase.ui.OnPhotoClickedListener

class TestActivity :
    AppCompatActivity(),
    OnPhotoClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar)

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, (application as TestApplication).photoShowcaseFragment, null)
            .commit()
    }

    override fun onPhotoClicked(id: Long) {}
}