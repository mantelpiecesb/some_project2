package com.deitel.redtesttask1_dollarcoursechecker.api

import com.deitel.redtesttask1_dollarcoursechecker.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API-query to get dollar-rouble course for last 30 days
 */
interface BankService {

    @GET("/scripts/XML_dynamic.asp")
    suspend fun searchDollarCourse(
        @Query("date_req1") data_req1: String = "02/03/2001",
        @Query("date_req2") data_req2: String = "14/03/2001",
        @Query("VAL_NM_RQ") val_nm_rq: String = "R01235",
    ): BankResponse

    @GET("/scripts/XML_dynamic.asp")
    fun searchDollarCourseCall(
        @Query("date_req1") data_req1: String = "02/03/2001",
        @Query("date_req2") data_req2: String = "14/03/2001",
        @Query("VAL_NM_RQ") val_nm_rq: String = "R01235",
    ): Call<BankResponse>

    companion object {

        fun create(): BankService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(BankService::class.java)
        }
    }

}