package com.lemon.fluttervideoshows

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.videodetaillayout.*

class VideoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videodetaillayout)
//        intent.putExtra("title", title)
//        intent.putExtra("bigTitleImage", bigTitleImage)
//        intent.putExtra("subjectCode", subjectCode)
//        intent.putExtra("titleImage", titleImage)
//        intent.putExtra("dataId", dataId)
//        intent.putExtra("jsonUrl", jsonUrl)
//        intent.putExtra("description", description)
        title1.text= intent.getStringExtra("title")
        title2.text= intent.getStringExtra("dataId")
        title3.text= intent.getStringExtra("description")
    }
}