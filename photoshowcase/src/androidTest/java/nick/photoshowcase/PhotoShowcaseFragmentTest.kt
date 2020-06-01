package nick.photoshowcase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runBlockingTest
import nick.core.Logger
import nick.photos.daos.PhotosDao
import nick.photos.services.FiveHundredPixelsService
import nick.photos.services.FiveHundredPixelsServiceWrapper
import nick.photos.services.PhotosRequest
import nick.photoshowcase.fakes.*
import nick.photoshowcase.repositories.PhotoShowcaseRepository
import nick.photoshowcase.ui.PhotoShowcaseFragment
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.testutils.InMemoryDatabaseRule
import nick.testutils.mock
import nick.testutils.withItemCount
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Provider

@RunWith(AndroidJUnit4::class)
class PhotoShowcaseFragmentTest {

    @get:Rule
    val photosDatabaseRule = InMemoryDatabaseRule(TestDatabase::class.java)

    val photosDao: PhotosDao
        get() = photosDatabaseRule.database.photosDao()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val logger: Logger = mock()

    @Test
    fun emptyAppearsWhenResultsAreEmpty() {
        launchPhotoShowcaseFragmentWithFakeService(EmptyFiveHundredPixelsService)

        onView(withId(R.id.empty))
            .check(matches(isDisplayed()))
    }

    @Test
    fun emptyAppearsWhenErrorHappensAndResultsAreEmpty() {
        launchPhotoShowcaseFragmentWithFakeService(ErrorFiveHundredPixelsService)

        onView(withId(R.id.empty))
            .check(matches(isDisplayed()))
    }

    @Test
    fun itemsAppearWhenSuccess() {
        launchPhotoShowcaseFragmentWithFakeService(SuccessFiveHundredPixelsService)

        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(photos.size)))
        onView(withId(R.id.empty))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun errorWithResultsShowsResults() = runBlockingTest {
        prepopulatePhotosDb()
        launchPhotoShowcaseFragmentWithFakeService(ErrorFiveHundredPixelsService)

        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(morePhotos.size)))
        onView(withId(R.id.empty))
            .check(matches(not(isDisplayed())))
    }

    private fun prepopulatePhotosDb() = runBlockingTest {
        photosDao.insert(morePhotos)
    }

    private fun launchPhotoShowcaseFragmentWithFakeService(fiveHundredPixelsService: FiveHundredPixelsService) {
        val repository = PhotoShowcaseRepository(
            FiveHundredPixelsServiceWrapper(fiveHundredPixelsService),
            photosDao,
            logger
        )

        val vmProvider = Provider {
            PhotoShowcaseViewModel(repository,
                PhotosRequest(), 15)
        }
        val fragment = PhotoShowcaseFragment(vmProvider, logger)

        val application: TestApplication = ApplicationProvider.getApplicationContext()
        application.photoShowcaseFragment = fragment

        launchActivity<TestActivity>()
    }
}