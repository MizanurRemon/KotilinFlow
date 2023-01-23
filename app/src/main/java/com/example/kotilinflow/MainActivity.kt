package com.example.kotilinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Flow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //val data = flowOf(1, 23, 5, 4, 8).flowOn(Dispatchers.IO)
        val data = listOf(1, 23, 5, 4, 8).asFlow().flowOn(Dispatchers.IO)

        runBlocking {

            data.filter { value ->
                value % 2 == 0
            }.map { value ->
                value * 2
            }.collect {
                Log.d("dataxx", "MAP:: $it")
            }
        }

        runBlocking {
            getData().catch { e ->
                Log.d("dataxx", "ERROR:: ${e.message}")
            }.collect { data ->
                Log.d("dataxx", "DATA:: $data")

            }
        }
    }


    fun getData(): kotlinx.coroutines.flow.Flow<Int> = flow {
        for (i in 1..5) {
            kotlinx.coroutines.delay(1000)

            emit(i)

        }
    }.flowOn(Dispatchers.IO)
}