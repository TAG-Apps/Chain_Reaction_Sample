package com.chainreaction.sample.model.repository

import com.chainreaction.sample.model.database.InventoryDao
import com.chainreaction.sample.model.model.InventoryModel
import com.chainreaction.sample.model.network_utils.APIServices
import com.chainreaction.sample.model.utils.AppUtils
import com.chainreaction.sample.view.di.DispatcherProvider
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val apiServices: APIServices,
    private val appUtils: AppUtils,
    private val defaultDispatcher: DispatcherProvider,
    private val inventoryDao: InventoryDao
)

{
    suspend fun getInventories() = apiServices.getInventories()

    suspend fun addInventories(inventoriesList: List<InventoryModel>) = inventoryDao.addInventories(inventoriesList)

}