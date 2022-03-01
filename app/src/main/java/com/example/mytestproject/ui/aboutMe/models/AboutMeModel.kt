package com.example.mytestproject.ui.aboutMe.models

data class AboutMeModel(val firstName:String,val photo:String,val secondName:String,val education:String,val company:Company)


data class Company(val name:String,val position:String)