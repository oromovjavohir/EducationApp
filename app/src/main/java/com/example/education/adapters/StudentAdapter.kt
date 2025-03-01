package com.example.education.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.education.R
import com.example.education.databinding.ItemStudentBinding
import com.example.education.model.StudentData
/**
 * Creator: Javohir Oromov
 * Date: 23/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class StudentAdapter: ListAdapter<StudentData,StudentAdapter.StudentViewHolder>(StudentDiffUtil) {

    private var updateClickListener: ((Int) -> Unit)? = null
    private var deleteClickListener: ((Int) -> Unit)? = null


    fun setUpdateClickListener(listener: (Int) -> Unit) {
        updateClickListener = listener
    }

    fun setDeleteClickListener(listener: (Int) -> Unit) {
        deleteClickListener = listener
    }

    inner class StudentViewHolder(private val binding: ItemStudentBinding): RecyclerView.ViewHolder(binding.root){
        private var student: StudentData? = null

        fun bind(student: StudentData){
            this.student = student
            binding.StName.text = student.name
            binding.StPhone.text = student.phone
        }
        init {
            binding.moreStudent.setOnClickListener {
                student?.let { student ->
                   val popupMenu = PopupMenu(it.context,it).apply {
                       inflate(R.menu.menu)
                       setOnMenuItemClickListener { item ->
                           when(item.itemId){
                               R.id.delete -> deleteClickListener?.invoke(student.id)
                               R.id.update -> updateClickListener?.invoke(student.id)
                           }
                           true
                       }
                   }
                    popupMenu.show()
                }
            }
        }
    }
    object StudentDiffUtil: DiffUtil.ItemCallback<StudentData>(){
        override fun areItemsTheSame(oldItem: StudentData, newItem: StudentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudentData, newItem: StudentData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}