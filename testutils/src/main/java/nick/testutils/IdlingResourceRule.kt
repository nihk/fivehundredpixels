package nick.testutils

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IdlingResourceRule(
    private val idlingResource: IdlingResource
) : TestWatcher() {

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}