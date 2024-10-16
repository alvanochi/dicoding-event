package com.dicoding.submissionfundamental1.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dicoding.submissionfundamental1.data.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.FragmentFinishedBinding
import com.dicoding.submissionfundamental1.ui.ListFinishedAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val finishedViewModel by viewModels<FinishedViewModel>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishedViewModel.finishedEvent.observe(viewLifecycleOwner){
            setEventData(it)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            setLoading(it)
        }

        finishedViewModel.isEmpty.observe(viewLifecycleOwner){
            setListEmpty(it)
        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }

        })


    }

    private fun filter(query: String?){
        if(query != null){
            finishedViewModel.showFilter(query)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setEventData(listEvents: List<ListEventsItem>) {
        val adapter = ListFinishedAdapter()
        adapter.submitList(listEvents)
        binding.rvFinished.adapter = adapter
        binding.rvFinished.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFinished.setHasFixedSize(true)

    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListEmpty(isEmpty: Boolean) {
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE

    }
}