package com.deitel.redtesttask1_dollarcoursechecker.view

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.deitel.redtesttask1_dollarcoursechecker.data.BankRepository
import javax.inject.Inject

/**
 * Factory for providing Android LifeCycle ViewModels
 */
class ViewModelFactory @Inject constructor (
    val context: Context,
    owner: SavedStateRegistryOwner,
    val repository: BankRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DollarCourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DollarCourseViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
