package com.deitel.redtesttask1_dollarcoursechecker.di

import com.deitel.redtesttask1_dollarcoursechecker.App
import com.deitel.redtesttask1_dollarcoursechecker.view.MainActivity
import dagger.Component

/**
 * Component to make Dependency Injection for Application components
 */
@Component(modules = arrayOf(MainModule::class))
interface AppComponent {
    fun inject(app: App)

    fun inject(activity: MainActivity)

}