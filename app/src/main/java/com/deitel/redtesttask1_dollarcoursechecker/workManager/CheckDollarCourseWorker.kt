package com.deitel.redtesttask1_dollarcoursechecker.workManager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deitel.redtesttask1_dollarcoursechecker.Constants
import com.deitel.redtesttask1_dollarcoursechecker.R
import com.deitel.redtesttask1_dollarcoursechecker.api.BankResponse
import com.deitel.redtesttask1_dollarcoursechecker.api.BankService
import com.mili.workmanagerandpendingnotification.SharedPrefHelpers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * WorkManager task to show notification if dollar course becomes higher than the latest course
 */
class CheckDollarCourseWorker(context: Context, workerParams: WorkerParameters) : Worker(context,  workerParams),
    Callback<BankResponse> {

    override fun doWork(): Result {
        checkDollarCourseFromAPI()
        return Result.success()
    }

    private fun checkDollarCourseFromAPI() {
        val bankService : BankService = BankService.create()
        val call : Call<BankResponse> = bankService.searchDollarCourseCall()
        call.enqueue(this)

    }

    override fun onResponse(call: Call<BankResponse?>?, response: Response<BankResponse?>) {
        if (response.isSuccessful()) {
            val bankResponse: BankResponse? = response.body()
            if(getCorrectFloatFromString(bankResponse?.records?.get(bankResponse.records.size - 1)?.value)
                > getCorrectFloatFromString(SharedPrefHelpers.readFromSharedPreferences(applicationContext,"LAST_RECORD_VALUE",""))
            ) {
                val builder = NotificationCompat.Builder(applicationContext,
                    Constants.CHANNEL_ID_PERIOD_WORK
                )
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Курс доллара повысился!")
                    .setContentText("Запустите приложение, чтобы посмотреть текущий курс.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(applicationContext)) {
                    notify(1, builder.build())
                }
            } else {
                Log.d("WORKMANAGER ", "WORKS!")
            }

        } else {
            Log.d("MY TEST ", response.errorBody().toString())
        }


    }

    override fun onFailure(call: Call<BankResponse?>?, t: Throwable) {
        t.printStackTrace()
    }

    private fun getCorrectFloatFromString(param: String?): Float {
        return param?.replace(',','.')?.toFloat()!!
    }


}