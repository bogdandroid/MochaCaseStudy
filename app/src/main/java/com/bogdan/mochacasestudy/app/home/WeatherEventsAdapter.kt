package com.bogdan.mochacasestudy.app.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.data.model.Feature
import com.bogdan.mochacasestudy.databinding.WeatherEventItemBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class WeatherEventsAdapter(
    private val onClickAction: (data: Feature) -> Unit
) :
    ListAdapter<Feature, WeatherEventsAdapter.WeatherEventViewHolder>(DiffCallback) {


    class WeatherEventViewHolder(
        private val binding: WeatherEventItemBinding,
        private val onClickAction: (data: Feature) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Feature) {
            binding.eventName.text = data.properties.event
            binding.dateRange.text = "From: ${data.properties.onset}\nTo: ${data.properties.ends}"
            binding.source.text = data.properties.senderName
            data.imageUrl?.let {
                Glide.with(binding.root).load(data.imageUrl)
                    .into(binding.eventImage)
            } ?: kotlin.run {

            }
            binding.root.setOnClickListener {
                onClickAction(data)
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Feature>() {
        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherEventsAdapter.WeatherEventViewHolder {
        val binding =
            WeatherEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherEventViewHolder(binding, onClickAction)
    }

    override fun onBindViewHolder(holder: WeatherEventViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        imageHack(data, position)
    }

    private fun imageHack(
        data: Feature,
        position: Int
    ) {
        if (data.imageUrl == null) {
            CoroutineScope(Dispatchers.IO).launch {
                val url = getNewUrl()
                data.imageUrl = url
                withContext(Dispatchers.Main) {
                    notifyItemChanged(position)
                }
            }
        }
    }

    private fun getNewUrl(): String? {
        try {
            val urlTemp = URL(Constants.BASE_URL_IMAGES)
            val connection: HttpURLConnection = urlTemp.openConnection() as HttpURLConnection
            val code = connection.responseCode
            val newUrl = connection.url.toString()
            connection.disconnect()
            return newUrl
        } catch (e: Exception) {
            //handle
            Log.e("TAG", "getNewUrl error: $e")
        }
        return null
    }
}