package com.example.dailymemo.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailymemo.Setting.Dialog.AutoIndexDialog
import com.example.dailymemo.Setting.Dialog.BuyStreamDialog
import com.example.dailymemo.Setting.Dialog.RemoveAdDialog
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentPremiumSettingBinding


class PremiumSettingFragment : Fragment() {

    lateinit var binding : FragmentPremiumSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPremiumSettingBinding.inflate(inflater,container,false)

        binding.apply {
            buyStreamLayout.setOnClickListener {
                showBuyStreamDialog()
            }

            removeAdLayout.setOnClickListener {
                showRemoveAdDialog()
            }

            autoIndexLayout.setOnClickListener {
                showAutoIndexDialog()
            }

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(PremiumSettingFragment())?.commit();
                fragmentManager?.popBackStack();

            }
        }

        return binding.root
    }

    private fun showBuyStreamDialog() {
        val dialog = BuyStreamDialog(requireContext())
        dialog.show()
    }

    private fun showRemoveAdDialog() {
        val dialog = RemoveAdDialog(requireContext())
        dialog.show()
    }

    private fun showAutoIndexDialog() {
        val dialog = AutoIndexDialog(requireContext())
        dialog.show()
    }

}