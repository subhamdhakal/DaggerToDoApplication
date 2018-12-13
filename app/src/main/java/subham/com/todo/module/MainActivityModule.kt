package subham.com.todo.module

import dagger.Module
import dagger.Provides
import subham.com.todo.repository.DayRepository
import subham.com.todo.viewmodel.DayViewModel

@Module
class MainActivityModule{
    @Provides
    fun providesMainActivityViewModel(dayRepository: DayRepository):DayViewModel{
       return DayViewModel(dayRepository)
    }
}