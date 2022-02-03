package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.dolatkia.animatedThemeManager.ThemeManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*
import kotlin.collections.ArrayList

class MainFragment(private val taskArrayList: ArrayList<TaskModel>) : Fragment() {

    private lateinit var mAdapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var tvLight: TextView
    private lateinit var tvDark: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        val themeSwitch = view.findViewById<SwitchMaterial>(R.id.theme_switch)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        addButton = view.findViewById(R.id.add_btn)
        tvLight = view.findViewById(R.id.tvLight)
        tvDark = view.findViewById(R.id.tvDark)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            themeSwitch.isChecked = false

        // Implement listener
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->

            when (isChecked) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }

        addButton.setOnClickListener {
            taskArrayList.add(0, TaskModel(UUID.randomUUID().toString(), "", false))
            mAdapter.notifyItemInserted(0)


            recyclerView.postDelayed({
                val viewHolder: TodoAdapter.MyViewHolder? = recyclerView.findViewHolderForAdapterPosition(0) as TodoAdapter.MyViewHolder?
                viewHolder?.taskItem?.requestFocus()
            }, 100)
        }

        mAdapter = TodoAdapter(taskArrayList)
        recyclerView.adapter = mAdapter
        enableSwipeToDelete()
    }

//    override fun syncTheme(appTheme: AppTheme) {
//
//        val myAppTheme = appTheme as MyAppTheme
//
//        for (i in 0 until taskArrayList.size) {
//            (recyclerView.findViewHolderForAdapterPosition(i) as TodoAdapter.MyViewHolder).taskItem.setTextColor(myAppTheme.firstActivityTextColor(requireActivity()))
//        }
//
//        tvLight.setTextColor(myAppTheme.firstActivityTextColor(requireActivity()))
//        tvDark.setTextColor(myAppTheme.firstActivityTextColor(requireActivity()))
//    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback = object: SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                mAdapter.removeItem(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}