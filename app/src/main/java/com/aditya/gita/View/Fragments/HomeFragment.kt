package com.aditya.gita.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aditya.gita.Models.ChaptersItem
import com.aditya.gita.NetworkManager
import com.aditya.gita.R
import com.aditya.gita.View.adapter.AdapterChapters
import com.aditya.gita.databinding.FragmentHomeBinding
import com.aditya.gita.viewModels.MainViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var adapterChapters: AdapterChapters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        checkInternetConnectivity()
        getVerseData()
        return binding.root
    }


    private fun getVerseData() {
        val chNumber = (1..18).random()
        val verseNo = (1..15).random()
        lifecycleScope.launch {
            viewModel.getAperticualarVerse(chNumber, verseNo).collect { verse ->

                for (i in verse.translations) {
                    if (i.language == "hindi") {
                        binding.verseOfDay.text = i.description
                        binding.shimmerDay.visibility = View.GONE
                        binding.verseOfDay.visibility = View.VISIBLE

                    }
                }
            }
        }
    }

    private fun checkInternetConnectivity() {
        try {
            val networkManager = NetworkManager(requireContext())
            networkManager.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.shimmer.visibility = View.VISIBLE
                    binding.rv.visibility = View.VISIBLE
                    binding.internetText.visibility = View.GONE
                    getAllChapters()
                } else {
                    binding.shimmer.visibility = View.GONE
                    binding.rv.visibility = View.GONE
                    binding.internetText.visibility = View.VISIBLE
                }
            }
            } catch (e: Exception) {


            }



        fun changeStatusBarColor() {
            val window = activity?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.titleColor)
            window?.decorView?.let {
                WindowCompat.getInsetsController(window, it).apply {
                    isAppearanceLightStatusBars = true
                }
            }
        }

    }

    private fun getAllChapters() {
        lifecycleScope.launch {
            try {
                viewModel.getAllChapters().collect { chapterList ->
                    adapterChapters = AdapterChapters(::onChapterItemView)
                    binding.rv.adapter = adapterChapters
                    adapterChapters.differ.submitList(chapterList)
                    binding.shimmer.visibility = View.GONE
                }
            } catch (E: Exception) {


            }
        }
    }

    private fun onChapterItemView(chaptersItem: ChaptersItem) {

        val bundle = Bundle()
        bundle.putInt("chapterNumber", chaptersItem.chapter_number)
        bundle.putString("chapterTitle", chaptersItem.name)
        bundle.putString("chapterDesc", chaptersItem.chapter_summary_hindi)
        bundle.putInt("verseCount", chaptersItem.verses_count)
        findNavController().navigate(R.id.action_homeFragment_to_versesFragment, bundle)

    }
}

