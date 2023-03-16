package com.jpmc.nycschools.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpmc.nycschools.R
import com.jpmc.nycschools.model.data.SchoolInfo
import com.jpmc.nycschools.view.SchoolStatsInfo




class NycSchoolAdapter(var commitRecords: List<SchoolInfo>)
    : RecyclerView.Adapter<NycSchoolAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRecord(commitRecords.get(position))
    }

    override fun getItemCount(): Int {
        return commitRecords.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtSchoolName: TextView =itemView.findViewById<TextView>(R.id.txtSchoolName)
        private val txtOverview: TextView =itemView.findViewById<TextView>(R.id.txtOverview)
        private val txtWebsite: TextView =itemView.findViewById<TextView>(R.id.txtWebsite)

        fun bindRecord(record: SchoolInfo) {
            record.apply {
                txtSchoolName.text = record.school_name
                txtOverview.text = record.overview_paragraph
                txtWebsite.text = record.website
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SchoolStatsInfo::class.java).apply {
                    putExtra("data", record)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}