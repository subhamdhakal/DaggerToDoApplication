package subham.com.todo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import subham.com.todo.AddNoteActivity
import subham.com.todo.MainActivity
import subham.com.todo.module.AddNoteModule
import subham.com.todo.module.MainActivityModule

@Module
abstract class ActivityBuilder{
    @ContributesAndroidInjector(modules = arrayOf(AddNoteModule::class))
    abstract fun bindAddNoteActivity():AddNoteActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity():MainActivity
}