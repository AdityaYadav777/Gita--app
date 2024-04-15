package com.aditya.gita.reprositry


import com.aditya.gita.Models.ChaptersItem
import com.aditya.gita.Models.VersesItem
import com.aditya.gita.api.ApiUtilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppRepositry {

    fun getAllChapters() : Flow<List<ChaptersItem>> = callbackFlow {

        val callbacks = object : Callback<List<ChaptersItem>> {
            override fun onResponse(
                call: Call<List<ChaptersItem>>,
                response: Response<List<ChaptersItem>>
            ) {
              if (response.isSuccessful && response.body() != null){
                  trySend(response.body()!!)
                  close()
              }
            }

            override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
            close(t)
            }

        }

        ApiUtilities.api.getAllChapters().enqueue(callbacks)

        awaitClose{}

    }

    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> = callbackFlow {
        val callback= object : Callback<List<VersesItem>> {
            override fun onResponse(
                call: Call<List<VersesItem>>,
                response: Response<List<VersesItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                close(t)
            }


        }
        ApiUtilities.api.getVerses(chapterNumber).enqueue(callback)
        awaitClose{}

    }

    fun getAperticualarVerse(chapterNumber: Int,verseNumber:Int) :Flow<VersesItem> =  callbackFlow {
        val callback= object : Callback<VersesItem> {
            override fun onResponse(call: Call<VersesItem>, response: Response<VersesItem>) {
                if (response.isSuccessful && response.body() !=null){
                    trySend(response.body()!!)
                    close()
                }

            }

            override fun onFailure(call: Call<VersesItem>, t: Throwable) {
               close(t)

            }

        }
        ApiUtilities.api.getAperticualarVerse(chapterNumber,verseNumber).enqueue(callback)
        awaitClose()
    }


}