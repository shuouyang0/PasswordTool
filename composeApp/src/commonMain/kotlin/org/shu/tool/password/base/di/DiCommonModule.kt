package org.shu.tool.password.base.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.shu.tool.password.base.db.AppDatabase
import org.shu.tool.password.base.network.createHttpClient
import org.shu.tool.password.base.repo.PasswordRecordLocalSource
import org.shu.tool.password.base.repo.PasswordRecordRemoteSource
import org.shu.tool.password.base.repo.PasswordRecordRepository
import org.shu.tool.password.getPlatform
import org.shu.tool.password.ui.page.home.HomeViewModel
import org.shu.tool.password.ui.page.detail.DetailViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.shu.tool.password.getDatabase

private val appModule = module {
    factory { getPlatform(this) }
}
private val databaseModule
    get() = module {
        single{ getDatabase(this) }
        singleOf(AppDatabase::passwordRecordDao)
    }

private val networkModule
    get() = module {
        single { createHttpClient() }
    }

private val sourceModule
    get() = module {
        singleOf(::PasswordRecordLocalSource)
        singleOf(::PasswordRecordRemoteSource)
    }

private val repoModule
    get() = module {
        singleOf(::PasswordRecordRepository)
    }

private val viewModule
    get() = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailViewModel)
    }





fun initKoin(config : KoinAppDeclaration? = null){
    startKoin {
        printLogger()
        includes(config)
        modules(
            appModule,
            databaseModule,
            networkModule,
            sourceModule,
            repoModule,
            viewModule
        )
    }
}