package subham.com.todo.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import subham.com.todo.database.DayDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(application: Application): DayDatabase {
        val database: DayDatabase = Room
            .databaseBuilder(
                application.applicationContext,
                DayDatabase::class.java, "day_database"
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        return database
    }

    @Provides
    @Singleton
    fun provideDayDao(database: DayDatabase) = database.toDoDao
}