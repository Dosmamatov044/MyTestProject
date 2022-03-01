package com.example.mytestproject.ui.aboutMe.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mytestproject.json.listData
import org.json.JSONException
import org.json.JSONObject

class AboutMeRepositories() {
    var jsonMutableLiveData: MutableLiveData<List<AboutMeModel>>


    init {

        jsonMutableLiveData = MutableLiveData()
    }

    fun getJson() {
        val jsonStr = listData
        try {

            val jObj = JSONObject(jsonStr)
            val jsonArry = jObj.getJSONObject("users")

            val company = jsonArry.getJSONArray("company")
            val user = mutableListOf<AboutMeModel>()
            val photo = jsonArry.getString("photo")
            val firstName = jsonArry.getString("first_name")
            val secondName = jsonArry.getString("second_name")
            val education = jsonArry.getString("education")
            for (i in 0 until company.length()) {


                val companyArray = company.getJSONObject(i)
                val companyModel =
                    Company(companyArray.getString("name"), companyArray.getString("position"))

                val aboutMeModel =
                    AboutMeModel(firstName, photo, secondName, education, companyModel)
                user.add(aboutMeModel)
                jsonMutableLiveData.value = user
            }

        } catch (ex: JSONException) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex)
        }
    }

}


