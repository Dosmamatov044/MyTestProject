package com.example.mytestproject.helpers

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.makeToast(text:String){
  Toast.makeText(this,text,Toast.LENGTH_LONG).show()

}
fun ImageView.loadImage(url: String?) {
  Glide
    .with(this.context)
    .load(url)
    .centerCrop()
    .into(this)
}
