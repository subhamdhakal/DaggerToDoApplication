package subham.com.todo

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import subham.com.todo.base.BaseActivity
import subham.com.todo.database.Note
import subham.com.todo.viewmodel.AddNoteActivityViewModel
import javax.inject.Inject

class AddNoteActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_add_note
    }
    @Inject
    lateinit var viewModel: AddNoteActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        title = "Add note"
//        var notedao = getDatabase(this)
//        var repository = NoteRepository(notedao.noteDao)
//        viewModel = ViewModelProviders.of(this, AddNoteActivityViewModel.FACTORY(repository))
//            .get(AddNoteActivityViewModel::class.java)
        button_store.setOnClickListener {
            saveNotes()
        }
    }

    private fun saveNotes() {
        var title = edit_text_title.text.toString()
        var description = edit_text_description.text.toString()
        var priority = number_picker_priority.value
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }
        var note = Note(id = null, title = title, description = description, priority = priority)
        viewModel.insertNote(note)
        finish()
    }
}
