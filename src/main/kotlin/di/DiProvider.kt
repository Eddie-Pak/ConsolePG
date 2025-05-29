package di

import view.model.MenuViewModel
import view.model.SalesViewModel
import view.model.StoreViewModel

interface DiProvider {
    val menuViewModel: MenuViewModel

    val storeViewModel: StoreViewModel

    val salesViewModel: SalesViewModel
}