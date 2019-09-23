package nick.uiutils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredItemDecoration(
    private val spacePx: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val layoutManager = parent.layoutManager as StaggeredGridLayoutManager
        val spanIndex = layoutParams.spanIndex
        val spanCount = layoutManager.spanCount

        if (spanIndex == 0) {
            outRect.left = spacePx
        }

        if (position < spanCount) {
            outRect.top = spacePx
        }

        outRect.right = spacePx
        outRect.bottom = spacePx
    }
}