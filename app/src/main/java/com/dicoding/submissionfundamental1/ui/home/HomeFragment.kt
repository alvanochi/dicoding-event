package com.dicoding.submissionfundamental1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionfundamental1.data.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.FragmentHomeBinding
import com.dicoding.submissionfundamental1.ui.ListFinishHomeAdapter
import com.dicoding.submissionfundamental1.ui.ViewPagerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.finishedEvent.observe(viewLifecycleOwner){
            setEventData(it)
            binding.progressBar.visibility = View.INVISIBLE
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val viewPagerAdapter = ViewPagerAdapter()
        binding.viewPagerUpcoming.adapter = viewPagerAdapter

        binding.viewPagerUpcoming.layoutDirection = View.LAYOUT_DIRECTION_LTR



        homeViewModel.upcomingEvent.observe(viewLifecycleOwner) { events ->
            viewPagerAdapter.submitList(events)
        }

        binding.rvHomeFinished.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeFinished.adapter = ListFinishHomeAdapter()
        binding.rvHomeFinished.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEventData(listEvents: List<ListEventsItem>) {
        val adapter = ListFinishHomeAdapter()
        adapter.submitList(listEvents)
        binding.rvHomeFinished.adapter = adapter
    }
}