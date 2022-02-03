package com.example.todoapp

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView

class TodoAdapter(private val data: ArrayList<TaskModel>): RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View, private val data: ArrayList<TaskModel>): RecyclerView.ViewHolder(itemView) {
        val checkBox: LottieAnimationView = itemView.findViewById(R.id.checkbox)
        val taskItem: EditText = itemView.findViewById(R.id.task_item)
        lateinit var task: TaskModel

        init {
            checkBox.setOnClickListener {
                if (task.done) {
                    checkBox.progress = 0f
                    task.done = false
                    val animator = ObjectAnimator.ofObject(taskItem, "textColor", ArgbEvaluator(), Color.parseColor("#A3A3A3"), Color.parseColor("#2A2B2F"))
                    animator.duration = 400
                    animator.interpolator = AccelerateDecelerateInterpolator()
                    animator.start()
                    taskItem.paintFlags = taskItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                } else {
                    checkBox.speed = 1f
                    checkBox.playAnimation()
                    task.done = true
                    val animator1 = ObjectAnimator.ofFloat(taskItem, "translationX", 0f, 15f)
                    animator1.duration = 200
                    animator1.interpolator = AccelerateDecelerateInterpolator()
                    val animator2 = ObjectAnimator.ofFloat(taskItem, "translationX", 15f, 0f)
                    animator2.duration = 200
                    animator2.interpolator = AccelerateDecelerateInterpolator()
                    val animator3 = ObjectAnimator.ofObject(taskItem, "textColor", ArgbEvaluator(), taskItem.currentTextColor, Color.parseColor("#A3A3A3"))
                    animator3.duration = 400
                    animator3.interpolator = AccelerateDecelerateInterpolator()
                    val set = AnimatorSet()
                    set.playSequentially(animator1, animator2, animator3)
                    set.doOnEnd {
                        taskItem.paintFlags = taskItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    set.start()
                }

                taskItem.doAfterTextChanged {
                    task.subject = it.toString()
                }

                for (item in data) {
                    if (item.id == task.id) {
                        item.done = task.done
                        break
                    }
                }
            }

            taskItem.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    taskItem.clearFocus()

                    for (item in data) {
                        if (item.id == task.id) {
                            item.subject = task.subject
                            break
                        }
                    }
                }

                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)

        return MyViewHolder(itemView, data)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.taskItem.setText(data[position].subject)
        if (data[position].done) {
            holder.checkBox.progress = 1f
            holder.taskItem.setTextColor(Color.parseColor("#A3A3A3"))
            holder.taskItem.paintFlags = holder.taskItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.task = data[position]
    }

    override fun getItemCount() = data.size

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}