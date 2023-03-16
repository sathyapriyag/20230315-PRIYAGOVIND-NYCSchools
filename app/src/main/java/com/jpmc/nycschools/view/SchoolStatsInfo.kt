package com.jpmc.nycschools.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.jpmc.nycschools.R
import com.jpmc.nycschools.databinding.ActivityNycschoolListBinding
import com.jpmc.nycschools.databinding.ActivitySchoolStatsInfoBinding
import com.jpmc.nycschools.model.data.SchoolInfo
import com.jpmc.nycschools.viewmodel.ListViewModel
import com.jpmc.nycschools.viewmodel.StatsViewModel

class SchoolStatsInfo : AppCompatActivity() {

    lateinit var binding: ActivitySchoolStatsInfoBinding
    lateinit var viewModel: StatsViewModel
    lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolStatsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar=binding.progressBar
        val schoolInfo = intent.getSerializableExtra("data") as SchoolInfo

        // Displays School Information passed from School List screen

        schoolInfo.apply {
            binding.txtSchoolName.text = school_name
            binding.txtWebsite.text = getString(R.string.website, website)
            binding.txtPhone.text = getString(R.string.phone, phone_number)
            binding.txtEmail.text = getString(R.string.email, school_email)
            binding.txtOverview.text = overview_paragraph
            binding.txtOpportunities.text = academicopportunities1

            initViewModel(dbn)
        }

    }

    private fun initViewModel(dbn: String) {


            // Initialise View Model
            viewModel = ViewModelProvider(this)[StatsViewModel::class.java]

            // regsietering for LiveData Stats Records
            viewModel.registerForStats().observe(this) { stats ->
                progressBar.visibility = View.GONE
                stats?.let {
                    // stats Received for specific dbn/school
                    binding.txtStats.text = getString(
                        R.string.test_stats,
                        it.num_of_sat_test_takers,
                        it.sat_critical_reading_avg_score,
                        it.sat_math_avg_score,
                        it.sat_writing_avg_score
                    )
                } ?: kotlin.run { binding.txtStats.text = getString(R.string.no_stats_available) }
            }
           progressBar.visibility = View.VISIBLE

            // fetching Stats
            viewModel.fetchStats(dbn)
        }

}