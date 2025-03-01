package com.example.education.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.education.R
import com.example.education.databinding.ItemCourseBinding
import com.example.education.model.CourseData
/**
 * Creator: Javohir Oromov
 * Date: 23/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class CourseAdapter : ListAdapter<CourseData, CourseAdapter.CourseViewHolder>(CourseDiffUtil()) {

    private var updateClickListener: ((Int) -> Unit)? = null
    private var deleteClickListener: ((Int) -> Unit)? = null
    private var itemClickListener: ((Int) -> Unit)? = null

    fun setUpdateClickListener(listener: (Int) -> Unit) {
        updateClickListener = listener
    }

    fun setDeleteClickListener(listener: (Int) -> Unit) {
        deleteClickListener = listener
    }

    fun setItemClickListener(listener: (Int) -> Unit){
        itemClickListener = listener
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        private var course: CourseData? = null

        fun bind(course: CourseData) {
            this.course = course
            binding.CourseName.text = course.name
            binding.StCount.text = course.studentCount.toString()
            binding.GpCount.text = course.groupCount.toString()
        }
        init {
            binding.moreCourse.setOnClickListener {
                course?.let { c ->
                    val popupMenu = PopupMenu(it.context, it).apply {
                        inflate(R.menu.menu)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.update -> updateClickListener?.invoke(c.id)
                                R.id.delete -> deleteClickListener?.invoke(c.id)
                            }
                            true
                        }
                    }
                    popupMenu.show()
                }
            }
            binding.container.setOnClickListener {
                course?.let { c ->
                    itemClickListener?.invoke(c.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CourseDiffUtil : DiffUtil.ItemCallback<CourseData>() {
    override fun areItemsTheSame(oldItem: CourseData, newItem: CourseData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CourseData, newItem: CourseData): Boolean {
        return oldItem == newItem
    }
}