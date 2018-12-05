package subham.com.todo

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import subham.com.todo.database.Note
import subham.com.todo.databinding.ItemNoteBinding
import subham.com.todo.ui.NoteVM

class NoteAdapter(var context: Context) : RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder>() {
    var notes: List<Note> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapterViewHolder {
        var binding: ItemNoteBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_note, parent, false)
        return NoteAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteAdapterViewHolder, position: Int) {
        return holder.bind(notes[position])
    }

    fun setData(notes: List<Note>?) {
        this.notes = notes!!
        notifyDataSetChanged()
    }

    inner class NoteAdapterViewHolder(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            var noteVM = NoteVM(item)
            binding.viewModel = noteVM
        }
    }
}