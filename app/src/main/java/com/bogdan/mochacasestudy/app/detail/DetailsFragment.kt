package com.bogdan.mochacasestudy.app.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.text.bold
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.mochacasestudy.app.home.HomeViewModel
import com.bogdan.mochacasestudy.app.home.WeatherEventsAdapter
import com.bogdan.mochacasestudy.databinding.FragmentDetailBinding
import com.bogdan.mochacasestudy.databinding.FragmentHomeBinding
import com.bogdan.mochacasestudy.q2q3.decodeGitMessage
import com.bogdan.mochacasestudy.q2q3.testGitText
import com.bogdan.wevideotest.base.BaseFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment :
    BaseFragment<FragmentDetailBinding, DetailsViewModel>(FragmentDetailBinding::inflate) {

    override val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(args.feature)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    renderUi(uiState)
                }
            }
        }
    }

    private fun renderUi(uiState: DetailsScreenState) {
        if (uiState.isLoading) {
            binding.contentGroup.visibility = View.GONE
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.contentGroup.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.GONE
        }
        uiState.feature?.let {
            Glide.with(binding.root).load(it.imageUrl).into(binding.topImage)
            binding.dateRange.text = SpannableStringBuilder()
                .bold { append("Date Range: ") }
                .append("${it.properties.onset} - ${it.properties.ends}")
            binding.severity.text = SpannableStringBuilder()
                .bold { append("Severity: ") }
                .append(it.properties.severity ?: "")
            binding.certainty.text = SpannableStringBuilder()
                .bold { append("Certainty: ") }
                .append(it.properties.certainty ?: "")
            binding.urgency.text = SpannableStringBuilder()
                .bold { append("Urgency: ") }
                .append(it.properties.urgency ?: "")

            binding.source.text = SpannableStringBuilder()
                .bold { append("Source: ") }
                .append(it.properties.senderName ?: "")
            binding.description.text = SpannableStringBuilder()
                .bold { append("Description") }
                .append(it.properties.description ?: "")
            binding.instructions.text = SpannableStringBuilder()
                .bold { append("Instruction: ") }
                .append(it.properties.instruction ?: "")
        }
        val str = uiState.affectedZones.map {
            "Name: ${it.properties.name}, isRadar:${!it.properties.radarStation.isNullOrEmpty()}"
        }.joinToString { myStr ->
            "$myStr\n"
        }
        binding.affectedZones.text = SpannableStringBuilder()
            .bold { append("Affected zones: ") }
            .append(str)

        binding.description.setOnClickListener {
            toggleEllipsize(binding.description)
        }
        binding.instructions.setOnClickListener {
            toggleEllipsize(binding.instructions)
        }
    }


    private fun toggleEllipsize(textView: TextView) {
        if (textView.maxLines == 2) {
            textView.maxLines = Integer.MAX_VALUE
            textView.ellipsize = null
        } else {
            textView.maxLines = 2
            textView.ellipsize = TextUtils.TruncateAt.END
        }
    }


}