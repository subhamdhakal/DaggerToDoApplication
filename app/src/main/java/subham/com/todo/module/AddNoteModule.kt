package subham.com.todo.module

import dagger.Module
import dagger.Provides
import subham.com.todo.repository.NoteRepository
import subham.com.todo.viewmodel.AddNoteActivityViewModel

@Module
class AddNoteModule{
    @Provides
    fun providesAddNoteViewModel(noteRepository: NoteRepository):AddNoteActivityViewModel{
       return AddNoteActivityViewModel(noteRepository)
    }

}