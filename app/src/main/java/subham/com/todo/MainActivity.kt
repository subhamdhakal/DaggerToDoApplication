package subham.com.todo

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*
import subham.com.todo.base.BaseActivity
import subham.com.todo.viewmodel.NoteViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    @Inject
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reclycler_view.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter(this)
        reclycler_view.adapter = adapter
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }


        noteViewModel.getAllNotes().observe(this, Observer {
            adapter.setData(it)
        })
    }
}
