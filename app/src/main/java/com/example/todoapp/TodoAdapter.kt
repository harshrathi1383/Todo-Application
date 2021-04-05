package com.example.todoapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(private val list: List<TodoItem>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(todoItem: TodoItem) {
            with(itemView){
                viewColorTag.setBackgroundColor(Color.BLACK)
                txtShowTitle.text = todoItem.title
                txtShowTask.text = todoItem.description
                txtShowCategory.text = todoItem.category
                updateTime(todoItem.time)
                updateDate(todoItem.date)

            }
        }

        private fun updateDate(date: Long) {
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            if(date != 0L)
            itemView.txtShowDate.text = sdf.format(Date(date))
            else itemView.txtShowDate.text="-"
        }

        private fun updateTime(time: Long) {
            val myformat = "h:mm a"
            val sdf = SimpleDateFormat(myformat)
            if(time != 0L)
            itemView.txtShowTime.text = sdf.format(Date(time))
            else itemView.txtShowTime.text="-"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

}