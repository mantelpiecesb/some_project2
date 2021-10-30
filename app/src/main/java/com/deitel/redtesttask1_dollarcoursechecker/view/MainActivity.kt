package com.deitel.redtesttask1_dollarcoursechecker.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.savedstate.SavedStateRegistryOwner
import androidx.work.*
import com.deitel.redtesttask1_dollarcoursechecker.R
import com.deitel.redtesttask1_dollarcoursechecker.appComp
import com.deitel.redtesttask1_dollarcoursechecker.data.BankRepository
import com.deitel.redtesttask1_dollarcoursechecker.databinding.ActivityMainBinding
import com.deitel.redtesttask1_dollarcoursechecker.db.DollarCourseDatabase
import com.deitel.redtesttask1_dollarcoursechecker.workManager.CheckDollarCourseWorker
import com.mili.workmanagerandpendingnotification.SharedPrefHelpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * MainActivity that shows DollarCourse RecyclerView
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: DollarCourseViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: BankRepository

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        this.application.appComp.inject(this)
        mViewModel = ViewModelProvider(
            this, provideViewModelFactory(
                context = this,
                owner = this
            )
        ).get(DollarCourseViewModel::class.java)

        supportActionBar?.title = getString(R.string.default_activity_bar_title)
        val pagingAdapter = DollarCourseAdapter(DollarCourseAdapter.StringComparator, this)
        val recyclerView = binding.myRecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
            .apply {
                setDrawable(resources.getDrawable(R.drawable.divider))
            }
        )

        recyclerView.adapter = pagingAdapter

        lifecycleScope.launch { mViewModel.searchData().collectLatest { pagingData -> pagingAdapter.submitData(pagingData) }
        }

        if (WorkManager.getInstance(this).getWorkInfosByTag("periodic-pending-notification").get() == emptyList<WorkInfo>()) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val uploadWorkRequest = PeriodicWorkRequestBuilder<CheckDollarCourseWorker>(1, TimeUnit.DAYS, 1, TimeUnit.DAYS
            )
                .addTag("periodic-pending-notification")
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork("periodic-pending-notification", ExistingPeriodicWorkPolicy.KEEP, uploadWorkRequest
            )
        }

        lifecycleScope.launch(Dispatchers.Default) { saveLastRecordValue() }

    }

    fun provideViewModelFactory(context: Context, owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(this,owner, repository)
    }

    private fun saveLastRecordValue() {
        val dollarCourseDatabase = DollarCourseDatabase.getInstance(this)
        SharedPrefHelpers.writeToSharedPreferences(applicationContext,"LAST_RECORD_VALUE",
            dollarCourseDatabase.recordsDao().getLastRecord().value.toString())
        //Log.d("SAVED PREFS: ", SharedPrefHelpers.readFromSharedPreferences(applicationContext,"LAST_RECORD_VALUE","").toString())
    }


}































