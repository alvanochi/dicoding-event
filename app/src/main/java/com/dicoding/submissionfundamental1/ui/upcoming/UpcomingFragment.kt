package com.dicoding.submissionfundamental1.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionfundamental1.data.remote.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.FragmentUpcomingBinding
import com.dicoding.submissionfundamental1.ui.detail.DetailActivity
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_MEDIACOVER
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_NAME
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.ID_EVENT
import kotlinx.coroutines.launch

class UpcomingFragment : Fragment(), ListUpcomingAdapter.OnItemClickListener {

    private var _binding: FragmentUpcomingBinding? = null
    private val upcomingViewModel by viewModels<UpcomingViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingViewModel.upcomingEvent.observe(viewLifecycleOwner){
            setEventData(it)
        }
        upcomingViewModel.isLoading.observe(viewLifecycleOwner){
            setLoading(it)
        }

        upcomingViewModel.isEmpty.observe(viewLifecycleOwner){
            setListEmpty(it)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setEventData(listEvents: List<ListEventsItem>) {
        val adapter = ListUpcomingAdapter(this)
        adapter.submitList(listEvents)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcoming.adapter = adapter
        binding.rvUpcoming.setHasFixedSize(true)
    }


    private fun filter(query: String?){
        upcomingViewModel.viewModelScope.launch {
            if(!query.isNullOrEmpty()){
                upcomingViewModel.showFilter(query)
            }
        }
    }

    override fun onItemClick(item: ListEventsItem) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(ID_EVENT, item.id)
        intent.putExtra(EVENT_NAME, item.name)
        intent.putExtra(EVENT_MEDIACOVER, item.mediaCover)
        startActivity(intent)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListEmpty(isEmpty: Boolean) {
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE

    }
}