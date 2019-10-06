package nick.photoshowcase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import nick.core.Logger
import nick.networking.services.FiveHundredPixelsService
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.PhotoShowcaseDependencies
import nick.photoshowcase.di.PhotoShowcaseDependenciesProvider
import nick.photoshowcase.fakes.*
import nick.photoshowcase.repositories.PhotoShowcaseRepository
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.testutils.*
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PhotoShowcaseFragmentTest {

    @get:Rule
    val photosDatabaseRule = InMemoryDatabaseRule(TestDatabase::class.java)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val logger: Logger = mock()

    @Test
    fun errorAppearsWhenResultsAreEmpty() {
        launchPhotoShowcaseFragmentWithFakeService(EmptyFiveHundredPixelsService)

        onView(withId(R.id.error))
            .check(matches(isDisplayed()))
    }

    @Test
    fun errorAppearsWhenErrorHappensAndResultsAreEmpty() {
        launchPhotoShowcaseFragmentWithFakeService(ErrorFiveHundredPixelsService)

        onView(withId(R.id.error))
            .check(matches(isDisplayed()))
    }

    @Test
    fun itemsAppearWhenSuccess() {
        launchPhotoShowcaseFragmentWithFakeService(SuccessFiveHundredPixelsService)

        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(photos.size)))
        onView(withId(R.id.error))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun errorWithResultsShowsResults() = runBlockingTest {
        prepopulatePhotosDb()
        launchPhotoShowcaseFragmentWithFakeService(ErrorFiveHundredPixelsService)

        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(morePhotos.size)))
        onView(withId(R.id.error))
            .check(matches(not(isDisplayed())))
    }

    private fun prepopulatePhotosDb() = runBlockingTest {
        photosDatabaseRule.database.photosDao().insert(morePhotos)
    }

    private fun launchPhotoShowcaseFragmentWithFakeService(fiveHundredPixelsService: FiveHundredPixelsService) {
        val repository = PhotoShowcaseRepository(
            fiveHundredPixelsService,
            photosDatabaseRule.database.photosDao(),
            logger
        )

        val viewModel = PhotoShowcaseViewModel(repository, PhotosRequest())

        val photoShowcaseDependencies = object : PhotoShowcaseDependencies {
            override val photoShowcaseViewModel: PhotoShowcaseViewModel
                get() = viewModel
            override val logger: Logger
                get() = this@PhotoShowcaseFragmentTest.logger
        }

        val photoShowcaseDependenciesProvider = object : PhotoShowcaseDependenciesProvider {
            override val photoShowcaseDependencies: PhotoShowcaseDependencies
                get() = photoShowcaseDependencies
        }

        val application: TestApplication = ApplicationProvider.getApplicationContext()
        application.photoShowcaseDependenciesProvider = photoShowcaseDependenciesProvider

        launchActivity<TestActivity>()
    }
}