package com.bogdan.mochacasestudy.app.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.mochacasestudy.R
import com.bogdan.mochacasestudy.databinding.FragmentHomeBinding
import com.bogdan.mochacasestudy.q2q3.decodeGitMessage
import com.bogdan.mochacasestudy.q2q3.testGitText
import com.bogdan.wevideotest.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private val weatherListAdapter by lazy {
        WeatherEventsAdapter { feature ->
            val direction = HomeFragmentDirections.homeDestToDetailAction(feature)
            findNavController().navigate(direction)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.weatherRecyclerView.apply {
            this.adapter = weatherListAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 1)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    weatherListAdapter.submitList(uiState.featureList)
                    binding.progressCircular.visibility =
                        if (uiState.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

}