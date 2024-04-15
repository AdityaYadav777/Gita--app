package com.aditya.gita.api

import com.aditya.gita.Models.ChaptersItem
import com.aditya.gita.Models.VersesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface interfaceApi {


@GET("/v2/chapters/")
fun getAllChapters():Call<List<ChaptersItem>>


@GET("/v2/chapters/{chapterNumber}/verses/")
fun getVerses(@Path("chapterNumber") chapterNumber: Int) : Call<List<VersesItem>>


@GET("v2/chapters/{chapterNumber}/verses/{verseNumber}/")
fun getAperticualarVerse(
    @Path("chapterNumber") chapterNumber: Int,
    @Path("verseNumber") verseNumber: Int,
):Call<VersesItem>
}