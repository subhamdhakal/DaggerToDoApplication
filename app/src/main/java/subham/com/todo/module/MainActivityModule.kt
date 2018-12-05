package subham.com.todo.module

import dagger.Module
import dagger.Provides
import subham.com.todo.repository.NoteRepository
import subham.com.todo.viewmodel.NoteViewModel

@Module
class MainActivityModule{
    @Provides
    fun providesMainActivityViewModel(noteRepository: NoteRepository):NoteViewModel{
       return NoteViewModel(noteRepository)
    }
}