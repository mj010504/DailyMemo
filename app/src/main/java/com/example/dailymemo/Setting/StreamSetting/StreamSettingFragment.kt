package com.example.dailymemo.Setting.StreamSetting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentStreamSettingBinding
import java.io.File


class StreamSettingFragment : Fragment() {

    lateinit var binding : FragmentStreamSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStreamSettingBinding.inflate(inflater,container,false)
        initRecyclerView()
        binding.apply {

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(StreamSettingFragment())?.commit();
                fragmentManager?.popBackStack();

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initRecyclerView() {
        val settingStreamSettingRVAdapter = SettingStreamSettingRVAdapter(requireActivity())
        binding.streamRv.adapter = settingStreamSettingRVAdapter
        binding.streamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        settingStreamSettingRVAdapter.setMyItemClickListener(object: SettingStreamSettingRVAdapter.MyItemClickListener{
            override fun onSettingClick(streamName: String,) {
                val bundle = Bundle()
                bundle.putString("streamName", streamName)
                moveToStreamDetailFragment(bundle)
            }

        })
    }

    private fun moveToStreamDetailFragment(bundle: Bundle) {
        findNavController().navigate(R.id.streamSettingDetailFragment, bundle)
    }

}