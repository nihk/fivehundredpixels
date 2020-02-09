package nick.photoshowcase.ui

interface PhotoShowcaseCallbacks : OnPhotoClickedListener {
    fun paginate()
    fun paginationThreshold(): Int
}