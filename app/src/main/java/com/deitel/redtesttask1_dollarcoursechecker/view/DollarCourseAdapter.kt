package com.deitel.redtesttask1_dollarcoursechecker.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.deitel.redtesttask1_dollarcoursechecker.model.Record

/**
 * Adapter for DollarCourse RecyclerView
 */
class DollarCourseAdapter(diffCallback: DiffUtil.ItemCallback<Record>, private val context: Context) :
    PagingDataAdapter<Record, DollarCourseViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DollarCourseViewHolder {
        return DollarCourseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DollarCourseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }

    object StringComparator : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }
}