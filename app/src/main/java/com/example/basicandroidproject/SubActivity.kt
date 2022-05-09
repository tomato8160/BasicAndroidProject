package com.example.basicandroidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}