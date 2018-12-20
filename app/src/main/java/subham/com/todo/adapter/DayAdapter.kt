package subham.com.todo.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import subham.com.todo.R
import subham.com.todo.database.ToDo
import subham.com.todo.databinding.ItemNoteBinding
import subham.com.todo.ui.DayVM

class DayAdapter(var context: Context,var listener:ClickListener) : RecyclerView.Adapter<DayAdapter.DayAdapterViewHolder>() {

    interface ClickListener{
        fun onCheckClickListener(todo:ToDo)
    }

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

    inner class DayAdapterViewHolder(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root),
            View.OnClickListener
    {
        var dayVM = DayVM()

        init {
            binding.imageViewTickIcon.setOnClickListener(this)
            binding.viewModel = dayVM
        }
        override fun onClick(p0: View?) {
            if(p0?.id==binding.imageViewTickIcon.id)
                return listener.onCheckClickListener(dayVM.todo)

        }

        fun bind(item: ToDo) {
            Log.e(TAG,"item:"+item.title)
            dayVM.setToDo(item)
            binding.viewModel=dayVM
            if(item.doneStatus) {
                binding.itemTodo.setBackgroundResource(R.drawable.bg_rounded_rectangle_yellow)
                binding.textViewTodoTime.visibility = View.GONE
                binding.imageViewPriorityIndicator.visibility=View.GONE
                binding.imageViewNotification.visibility=View.GONE
            }

            else {
                binding.itemTodo.setBackgroundResource(R.drawable.bg_rounded_rectangle_gray)
                binding.textViewTodoTime.visibility = View.VISIBLE
                binding.imageViewPriorityIndicator.visibility=View.VISIBLE
                binding.imageViewNotification.visibility=View.VISIBLE
            }


        }


    }
}