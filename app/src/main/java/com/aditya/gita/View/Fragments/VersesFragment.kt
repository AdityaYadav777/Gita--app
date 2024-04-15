package com.aditya.gita.View.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aditya.gita.Models.Verses
import com.aditya.gita.Models.VersesItem
import com.aditya.gita.R
import com.aditya.gita.View.adapter.AdapterVerse
import com.aditya.gita.databinding.FragmentVersesBinding
import com.aditya.gita.viewModels.MainViewModel
import kotlinx.coroutines.launch


class VersesFragment : Fragment() {

    lateinit var binding:FragmentVersesBinding
    private lateinit var adapterVerse:AdapterVerse
    private var chapterNumber=0
    private val viewModel:MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVersesBinding.inflate(layoutInflater)


        getSetAndSetCahapterDetails()
         onReadMoreClicked()
        getAllVerses()

        return binding.root
    }





    @SuppressLint("SuspiciousIndentation")
    private fun onReadMoreClicked() {
      var isExpand=false
        binding.readMore.setOnClickListener {
          if(!isExpand){
              binding.tvDesc.maxLines=50
              isExpand=true
          }
          else{
              binding.tvDesc.maxLines=4
              isExpand=false
           }


        }
    }

    private fun getSetAndSetCahapterDetails() {
        val bundle: Bundle? =arguments
        chapterNumber= bundle?.getInt("chapterNumber")!!
        binding.tvChNo.text="Chapter ${bundle?.getInt("chapterNumber")}"
        binding.tvTitle.text="${bundle?.getString("chapterTitle")}"
        binding.tvDesc.text="${bundle?.getString("chapterDesc")}"
        binding.tvVerseCount.text="${bundle?.getInt("verseCount")} Verses"

    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            viewModel.getAllVerses(chapterNumber).collect {

                adapterVerse= AdapterVerse(::onVerseItemVClicked)
                binding.rvVerse.adapter=adapterVerse
                var Verslist= arrayListOf<String>()

                for(currentVers in it){
                    for(verser in currentVers.translations){
                        if(verser.language=="hindi"){
                            Verslist.add(verser.description)
                            break
                        }
                    }

                }
                adapterVerse.differ.submitList(Verslist)
                binding.shimmer.visibility=View.GONE
                binding.rvVerse.visibility=View.VISIBLE

            }
        }
    }
    private fun onVerseItemVClicked(verse:String,verseNumber:Int){

        val bundle=Bundle()
        bundle.putInt("chapterNumber",chapterNumber)
        bundle.putInt("verseNumber",verseNumber)
        bundle.putString("vere",verse)

findNavController().navigate(R.id.action_versesFragment_to_verseDetailFragment,bundle)
    }

}