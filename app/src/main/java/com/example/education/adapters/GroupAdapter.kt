package com.example.education.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.education.R
import com.example.education.databinding.ItemGroupBinding
import com.example.education.model.GroupData
/**
 * Creator: Javohir Oromov
 * Date: 23/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class GroupAdapter : ListAdapter<GroupData, GroupAdapter.GroupViewHolder>(GroupDiffCallback()) {

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

    inner class GroupViewHolder(private val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        private var group: GroupData? = null

        fun bind(group: GroupData) {
            this.group = group
            binding.GroupName.text = group.name
            binding.MaxCount.text = group.size.toString()
            binding.CurrCount.text = group.studentCount.toString()
        }
        init {
            binding.moreGroup.setOnClickListener {
                group?.let { group ->
                    val popupMenu = PopupMenu(it.context,it).apply {
                        inflate(R.menu.menu)
                        setOnMenuItemClickListener { item ->
                            when(item.itemId){
                                R.id.update -> updateClickListener?.invoke(group.id)
                                R.id.delete -> deleteClickListener?.invoke(group.id)
                            }
                            true
                        }
                    }
                    popupMenu.show()
                }
            }
            binding.itemGroup.setOnClickListener {
                group?.let {
                    itemClickListener?.invoke(it.id)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class GroupDiffCallback : DiffUtil.ItemCallback<GroupData>() {
    override fun areItemsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
        return oldItem == newItem
    }
}
