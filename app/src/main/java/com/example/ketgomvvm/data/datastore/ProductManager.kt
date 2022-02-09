package com.example.ketgomvvm.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("products")

class ProductManager(val context: Context) {

    private val myDatastore: DataStore<Preferences> = context.datastore

    companion object {
        val SELLING_LOCATION = stringPreferencesKey("SELLING_LOCATION")
     }

    suspend fun storeProductSellingData(sellingLocation: String) {
        myDatastore.edit { preferences ->
            preferences[SELLING_LOCATION] = sellingLocation
        }
    }

    val productSellingLocationFlow: Flow<String> = myDatastore.data.map {
        it[SELLING_LOCATION] ?: ""
    }

}