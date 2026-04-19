package com.example.ser210finalproject.data

import android.content.Context


// AppContainer for dependency injection
// provides single instance of the repository to entire app
interface AppContainer {
    val itemsRepository: ItemsRepository
}

// ensures database is only created when first needed
class AppDataContainer(private val context: Context) : AppContainer {

    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(
            AppDatabase.getDatabase(context).studentDao(),
            AppDatabase.getDatabase(context).listingDao(),
            AppDatabase.getDatabase(context).purchaseDao()
        )
    }
}