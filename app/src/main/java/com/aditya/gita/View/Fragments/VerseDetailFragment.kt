package com.aditya.gita.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aditya.gita.Models.Translation
import com.aditya.gita.R
import com.aditya.gita.databinding.FragmentVerseDetailBinding
import com.aditya.gita.databinding.FragmentVersesBinding
import com.aditya.gita.viewModels.MainViewModel
import kotlinx.coroutines.launch

class VerseDetailFragment : Fragment() {


lateinit var binding:FragmentVerseDetailBinding
private val viewModel:MainViewModel by viewModels()
private var chapterNumber=0
private var verseNumber=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVerseDetailBinding.inflate(layoutInflater)

        getSetData()
        getVerseDetails()

        return binding.root
    }

    private fun getVerseDetails() {
      lifecycleScope.launch {
          viewModel.getAperticualarVerse(chapterNumber,verseNumber).collect{verse->

              binding.slokeText.text=verse.text

              for(t in verse.commentaries){
                  if(t.language=="hindi"){
                      binding.commentryHindi.text=t.description
                      binding.shimmer.visibility=View.GONE
                      binding.cardView2.visibility=View.VISIBLE
                      binding.autherName.text=t.author_name
                  }
              }






          }
      }
    }

    private fun getSetData() {
     val bundle:Bundle?=arguments
        chapterNumber= bundle!!.getInt("chapterNumber")
        verseNumber= bundle.getInt("verseNumber")
        binding.verseNumber.text="|| ${bundle?.getInt("verseNumber")} ||"
        binding.wordMeaning.text=bundle?.getString("vere")

    }


}