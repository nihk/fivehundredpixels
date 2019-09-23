package nick.testutils

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class InMemoryDatabaseRule<T>(
    private val clazz: Class<T>
) : TestWatcher()
        where T : RoomDatabase {

    private var _database: T? = null
    val database: T get() = _database!!

    override fun starting(description: Description) {
        _database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            clazz
        ).allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description?) {
        _database?.close()
    }
}