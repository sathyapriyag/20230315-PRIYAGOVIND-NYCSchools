package com.jpmc.nycschools.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpmc.nycschools.R
import com.jpmc.nycschools.databinding.ActivityNycschoolListBinding
import com.jpmc.nycschools.view.adapter.NycSchoolAdapter
import com.jpmc.nycschools.viewmodel.ListViewModel

class NYCSchoolList : AppCompatActivity() {

    private lateinit var binding: ActivityNycschoolListBinding
    lateinit var viewModel: ListViewModel
    lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNycschoolListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // use a linear layout manager
        list =binding.recyclerView
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(DividerItemDecoration(list.context,DividerItemDecoration.VERTICAL))


        initViewModel()

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        //registering for LiveData School list
        viewModel.registerForSchoolsInfo().observe(this) { schoolsList ->
            // callback received with list of school information
            showMessage(schoolsList.isNullOrEmpty())
            if (schoolsList.isNullOrEmpty()) {
                showMessage(true)
            } else {
                showMessage(false)
                list.adapter = NycSchoolAdapter(schoolsList)
            }
        }
        // Observing for network connection
        viewModel.registerForInternalConnectivity().observe(this) { isNetworkConnected ->
            if (!isNetworkConnected) {
                binding.progressBar.visibility = View.GONE
                binding.txtMessage.visibility = View.VISIBLE
                binding.txtMessage.text = getString(R.string.no_network)
            }
        }
        binding.progressBar.visibility = View.VISIBLE
        // fetch schools
        viewModel.fetchSchools()
    }

    private fun showMessage(show: Boolean) {
        binding.progressBar.visibility = View.GONE
        list.visibility = if (show) View.GONE else View.VISIBLE
        binding.txtMessage.visibility = if (show) View.VISIBLE else View.GONE
    }
}