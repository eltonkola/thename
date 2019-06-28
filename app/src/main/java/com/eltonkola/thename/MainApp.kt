package com.eltonkola.thename

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.data.PreManager
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.ui.details.DetailsViewModel
import com.eltonkola.thename.ui.explore.ExploreViewModel
import com.eltonkola.thename.ui.list.ListViewModel
import com.eltonkola.thename.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.FileOutputStream
import java.io.IOException


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(DebugTree())
        }

    }

}


private val appModule = module {

    single {
        val dbName = "emra.sqlite"
        copyDbFromAssetsIfDoesNotExist(androidApplication(), dbName)
        Room.databaseBuilder(get(), EmriDatabase::class.java, dbName).build()
    }

    single { DataManager(get(), get(), get()) }
    single { PreManager(get()) }

    viewModel { ExploreViewModel(get(), get()) }
    viewModel { ListViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }

    viewModel { (withEmri: Emri) -> DetailsViewModel(get(), withEmri, get(), get()) }

}


private fun copyDbFromAssetsIfDoesNotExist(context: Context, databaseName: String) {
    Timber.d("copyDbFromAssetsIfDoesnotExist $databaseName ")
    val dbPath = context.getDatabasePath(databaseName)

    // If the database already exists, return
    if (dbPath.exists()) {
        return
    }

    // Make sure we have a path to the file
    dbPath.parentFile.mkdirs()

    // Try to copy database file
    try {
        val inputStream = context.assets.open(databaseName)
        val output = FileOutputStream(dbPath)

        val buffer = ByteArray(8192)
        var length = inputStream.read(buffer)

        while (length > 0) {
            output.write(buffer, 0, length)
            length = inputStream.read(buffer)
        }

        output.flush()
        output.close()
        inputStream.close()


    } catch (e: IOException) {
        Timber.d(e)
        e.printStackTrace()
    }

}



