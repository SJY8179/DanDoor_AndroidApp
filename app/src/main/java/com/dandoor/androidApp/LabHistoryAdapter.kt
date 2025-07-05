package com.dandoor.androidApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dandoor.library.data.Lab // library 모듈의 Lab 클래스를 import 합니다.
import java.text.SimpleDateFormat
import java.util.*

class LabHistoryAdapter(
    private val labs: List<Lab>,
    private val onLabClick: (Lab) -> Unit
) : RecyclerView.Adapter<LabHistoryAdapter.LabViewHolder>() {

    class LabViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val aliasTextView: TextView = view.findViewById(R.id.tv_lab_alias)
        private val dateTextView: TextView = view.findViewById(R.id.tv_lab_date)

        fun bind(lab: Lab) {
            aliasTextView.text = lab.alias
            dateTextView.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(lab.createdAt))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lab_history, parent, false)
        return LabViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        val lab = labs[position]
        holder.bind(lab)
        holder.itemView.setOnClickListener { onLabClick(lab) }
    }

    override fun getItemCount() = labs.size
}