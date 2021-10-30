package com.deitel.redtesttask1_dollarcoursechecker.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deitel.redtesttask1_dollarcoursechecker.R
import com.deitel.redtesttask1_dollarcoursechecker.databinding.RecyclerviewItemBinding
import com.deitel.redtesttask1_dollarcoursechecker.model.Record

/**
 * ViewHolder for DollarCourse RecyclerView
 */
class DollarCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewItemBinding.bind(view)

    fun bind(item: Record?, context: Context) {
        binding.dollarCourseValueText.text = context.getString(R.string.dollar_course_pretext, item?.value)
        binding.dollarCourseDateText.text = context.getString(R.string.dollar_course_date_pretext, item?.date)
    }

    companion object {
        fun create(parent: ViewGroup): DollarCourseViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return DollarCourseViewHolder(view)
        }
    }
}