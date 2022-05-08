package com.example.basicandroidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.basicandroidproject.databinding.ActivityMainBinding
import com.example.basicandroidproject.databinding.ActivitySubBinding
import com.example.basicandroidproject.databinding.ActivitySubBindingImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubActivity : AppCompatActivity() {
    private val mViewModel : BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySubBinding>(this, R.layout.activity_sub)

        Log.d("base", "onCreate: ${mViewModel.getRepository().hashCode()}");

    }
}