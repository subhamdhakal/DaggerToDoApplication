package subham.com.todo.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import subham.com.todo.R
import subham.com.todo.database.ToDo
import subham.com.todo.databinding.ItemNoteBinding
import subham.com.todo.ui.DayVM

class DayAdapter(var context: Context) : RecyclerView.Adapter<DayAdapter.DayAdapterViewHolder>() {
    private var todos: List<ToDo> = arrayListOf()
    val TAG=DayAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapterViewHolder {
        var binding: ItemNoteBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_note, parent, false)
        return DayAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
    fun removeAt(position: Int){

        notifyItemRemoved(position)

    }

    override fun onBindViewHolder(holder: DayAdapterViewHolder, position: Int) {
         holder.bind(todos[position])
    }

    fun setData(todos:List<ToDo>?) {
        this.todos = todos?: arrayListOf()
        notifyDataSetChanged()
    }

    inner class DayAdapterViewHolder(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        var dayVM = DayVM()

        init {
            binding.viewModel = dayVM
        }
        fun bind(item: ToDo) {
            Log.e(TAG,"item:"+item.title)
            dayVM.setToDo(item)
        }
    }
}