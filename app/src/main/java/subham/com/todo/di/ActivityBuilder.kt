package subham.com.todo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import subham.com.todo.activity.MainActivity
import subham.com.todo.module.MainActivityModule

@Module
abstract class ActivityBuilder{

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): MainActivity
}