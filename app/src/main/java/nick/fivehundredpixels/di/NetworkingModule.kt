package nick.fivehundredpixels.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nick.core.Logger
import nick.networking.adapters.PhotosJsonAdapter
import nick.networking.services.FiveHundredPixelsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkingModule {

    @Provides
    fun retrofitBuilder() = Retrofit.Builder()

    @Provides
    fun moshiBuilder() = Moshi.Builder()

    @Provides
    fun okHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun moshi(photosJsonAdapter: PhotosJsonAdapter, moshiBuilder: Moshi.Builder): Moshi {
        return moshiBuilder
            .add(photosJsonAdapter)
            .build()
    }

    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    fun httpLogger(logger: Logger): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger(logger::d)
    }

    @Provides
    fun httpLoggingInterceptor(httpLogger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(httpLogger)
            .also { it.level = HttpLoggingInterceptor.Level.BASIC }
    }

    @Provides
    fun okHttpClient(
        builder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return builder
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Reusable
    @Provides
    fun fiveHundredPixelsService(
        retrofitBuilder: Retrofit.Builder,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): FiveHundredPixelsService {
        return retrofitBuilder
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .baseUrl(FiveHundredPixelsService.baseUrl)
            .build()
            .create(FiveHundredPixelsService::class.java)
    }
}