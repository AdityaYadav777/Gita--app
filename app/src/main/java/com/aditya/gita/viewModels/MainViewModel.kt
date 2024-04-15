package com.aditya.gita.viewModels

import androidx.lifecycle.ViewModel
import com.aditya.gita.Models.ChaptersItem
import com.aditya.gita.Models.VersesItem
import com.aditya.gita.reprositry.AppRepositry
import kotlinx.coroutines.flow.Flow

class MainViewModel:ViewModel() {
    val appRepositry=AppRepositry()

    fun getAllChapters() : Flow<List<ChaptersItem>> = appRepositry.getAllChapters()
    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> = appRepositry.getAllVerses(chapterNumber)
    fun  getAperticualarVerse(chapterNumber: Int,verseNumber: Int) :Flow<VersesItem> = appRepositry.getAperticualarVerse(chapterNumber,verseNumber)
}