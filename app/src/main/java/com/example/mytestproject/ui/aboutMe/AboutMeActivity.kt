package com.example.mytestproject.ui.aboutMe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mytestproject.databinding.ActivityAboutMeBinding
import com.example.mytestproject.helpers.loadImage
import com.example.mytestproject.ui.aboutMe.viewModel.AboutMeViewModel
import com.example.mytestproject.ui.images.ImagesActivity

class AboutMeActivity : AppCompatActivity() {
private val aboutMeViewModel:AboutMeViewModel by viewModels()
    lateinit var binding: ActivityAboutMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
updateUi()
    nextActivity()
    }


  private  fun updateUi(){
        aboutMeViewModel.jsonMutableLiveData.observe(this) {
            binding.userImage.loadImage(it[0].photo)
            binding.userName.text="Имя : ${ it[0].firstName }  "
            binding.userSurName.text= "Фамилия : ${ it[0].secondName }  "
            binding.userEducation.text="Образование : ${ it[0].education }  "
            binding.userCompanyName.text="Название компании : ${ it[0].company.name }  "
            binding.userCompanyPosition.text="Позиция в компании : ${ it[0].company.position}  "
        }


    }
    private fun nextActivity() {

        binding.nextActivity.setOnClickListener {
            val intent= Intent(this,ImagesActivity::class.java)
            startActivity(intent)

        }

    }


}