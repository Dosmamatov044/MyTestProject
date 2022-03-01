package com.example.mytestproject.ui.images.models

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private  const val  PREFERENCES_NAME="images_preferences"



val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = PREFERENCES_NAME)


class DataStoreRepository(private val context: Application)  {

    companion object{
        val IMAGE_COUNT_KEY = intPreferencesKey("ImagesCount")
    }

    suspend fun saveImageCount(count: Int) {
        context.datastore.edit {
           it[IMAGE_COUNT_KEY] =count


        }

    }

    val readImageCount: Flow<Int> = context.datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val imageCount = it[IMAGE_COUNT_KEY] ?: 0

            imageCount
        }
}




