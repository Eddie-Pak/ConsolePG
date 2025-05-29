package di

import data.datasource.*
import data.repository.MenuRepositoryImpl
import data.repository.SalesRepositoryImpl
import data.repository.StoreRepositoryImpl
import domain.repository.MenuRepository
import domain.repository.SalesRepository
import domain.repository.StoreRepository
import view.model.MenuViewModel
import view.model.SalesViewModel
import view.model.StoreViewModel

class DIProviderImpl : DiProvider {
    private val menuDataSource: MenuDataSource by lazy { MenuDataSourceImpl() }
    private val storeDataSource: StoreDataSource by lazy { StoreDataSourceImpl() }
    private val salesDataSource: SalesDataSource by lazy { SalesDataSourceImpl() }

    private val menuRepository: MenuRepository by lazy { MenuRepositoryImpl(menuDataSource) }
    private val storeRepository: StoreRepository by lazy { StoreRepositoryImpl(menuDataSource, storeDataSource) }
    private val salesRepository: SalesRepository by lazy { SalesRepositoryImpl(menuDataSource, storeDataSource, salesDataSource) }

    override val menuViewModel: MenuViewModel by lazy { MenuViewModel(menuRepository) }
    override val storeViewModel: StoreViewModel by lazy { StoreViewModel(storeRepository) }
    override val salesViewModel: SalesViewModel by lazy { SalesViewModel(salesRepository) }
}