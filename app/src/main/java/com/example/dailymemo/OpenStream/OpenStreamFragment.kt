package com.example.dailymemo.OpenStream

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentOpenStreamBinding


class OpenStreamFragment : Fragment() {

    lateinit var binding : FragmentOpenStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenStreamBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun initRecyclerView() {
        val openStreamRVAdapter = OpenStreamRVAdapter()
        binding.openstreamRv.adapter = openStreamRVAdapter
        binding.openstreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        openStreamRVAdapter.setMyItemClickListener(object: OpenStreamRVAdapter.MyItemClickListener {
            override fun onMenuClick(menu: ImageView) {
                showPopupMenu(menu)
            }

            override fun onStreamClick() {
               moveToStream()
            }
        })

    }

    private fun showPopupMenu(anchorView: View) {
        val inflater = LayoutInflater.from(requireContext())
        val customMenuView = inflater.inflate(R.layout.mystream_popup_menu_layout, null)

        // Customize PopupWindow
        val popupWindow = PopupWindow(
            customMenuView,
            350,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Get the location of the anchorView on the screen
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)

        // Calculate xOffset and yOffset to align top-right of popup with top-right of anchorView
        val xOffset = location[0] + anchorView.width - 350
        val yOffset = location[1]

        // Set location
        popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset)

        val publicBtn = customMenuView.findViewById<ConstraintLayout>(R.id.public_btn)
        val modifyBtn = customMenuView.findViewById<ConstraintLayout>(R.id.modify_btn)
        val deleteBtn = customMenuView.findViewById<ConstraintLayout>(R.id.delete_btn)

        publicBtn.setOnClickListener {

            popupWindow?.dismiss()
        }

        modifyBtn.setOnClickListener {

            popupWindow?.dismiss()
        }


        deleteBtn.setOnClickListener {

            popupWindow?.dismiss()
        }

    }

    private fun moveToStream() {
        findNavController().navigate(R.id.watchStreamFragment)
    }


}