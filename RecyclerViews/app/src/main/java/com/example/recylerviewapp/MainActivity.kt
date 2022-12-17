package com.example.recylerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recylerviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // create adapter by passing the required data
        val adapter = MainAdapter(TaskList.taskList)
        // set the adapter for the recycler view
        binding?.taskRv?.adapter = adapter
        binding?.taskRv?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding != null) {
            binding = null
        }
    }
}