package com.deitel.redtesttask1_dollarcoursechecker.di

import com.deitel.redtesttask1_dollarcoursechecker.api.BankService
import com.deitel.redtesttask1_dollarcoursechecker.data.BankRepository
import dagger.Module
import dagger.Provides

/**
 * Module for Dependency Injection
 */
@Module
class MainModule {

    @Provides
    fun provideBankService(): BankService {
        return BankService.create()
    }

    @Provides
    fun provideBankRepository(service: BankService): BankRepository {
        return BankRepository(service)
    }

}