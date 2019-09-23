package nick.fivehundredpixels.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nick.core.di.ApplicationContext
import nick.core.di.ApplicationScope
import nick.fivehundredpixels.data.FiveHundredPixelsDatabase

@Module
object DataModule {

    @ApplicationScope
    @Provides
    @JvmStatic
    fun fiveHundredPixelsDatabase(
        @ApplicationContext applicationContext: Context
    ): FiveHundredPixelsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            FiveHundredPixelsDatabase::class.java,
            FiveHundredPixelsDatabase.databaseName
        ).build()
    }

    @Reusable
    @Provides
    @JvmStatic
    fun photosDao(database: FiveHundredPixelsDatabase) = database.photosDao()
}