package org.shu.tool.password.base.di

import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.shu.tool.password.DiPlatformFactory
import org.shu.tool.password.base.db.AppDatabase
import org.shu.tool.password.base.network.createHttpClient
import org.shu.tool.password.base.repo.PasswordRecordLocalSource
import org.shu.tool.password.base.repo.PasswordRecordRemoteSource
import org.shu.tool.password.base.repo.PasswordRecordRepository
import org.shu.tool.password.ui.page.home.HomeViewModel
import org.shu.tool.password.ui.page.detail.DetailViewModel
private val databaseModule by lazy {
    module {
        single { (factory: DiPlatformFactory) -> factory.createDatabase() }
        single { (database: AppDatabase) -> database.passwordRecordDao() }
    }
}

private val networkModule by lazy {
    module {
        single { createHttpClient() }
    }
}

private val sourceModule by lazy {
    module {
        singleOf(::PasswordRecordLocalSource)
        singleOf(::PasswordRecordRemoteSource)
    }
}
private val repoModule by lazy {
    module {
        singleOf(::PasswordRecordRepository)
    }
}

private val viewModule by lazy {
    module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailViewModel)
    }
}


//用于注入通用的依赖模块
fun KoinApplication.diCommonModule() {
    modules(
        databaseModule,
        networkModule,
        repoModule,
        viewModule
    )

}