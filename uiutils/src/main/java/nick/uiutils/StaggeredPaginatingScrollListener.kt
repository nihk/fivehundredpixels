package nick.uiutils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredPaginatingScrollListener(
    private val pageSize: Int,
    private val paginate: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as? StaggeredGridLayoutManager ?: return
        val childCount = layoutManager.childCount
        val itemCount = layoutManager.itemCount
        val firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null)

        if (firstVisibleItems.isEmpty()) {
            return
        }

        val firstVisibleItem = firstVisibleItems.first()

        if ((childCount + firstVisibleItem >= itemCount)
            && firstVisibleItem >= 0
            && itemCount >= pageSize
        ) {
            paginate()
        }
    }
}