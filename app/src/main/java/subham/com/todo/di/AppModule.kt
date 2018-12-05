package subham.com.todo.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import subham.com.todo.database.NoteDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(application: Application): NoteDatabase {
        val database: NoteDatabase = Room
            .databaseBuilder(
                application.applicationContext,
                NoteDatabase::class.java, "note_database"
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        return database
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase) = database.noteDao
}