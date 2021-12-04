package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
   lateinit var adapter:TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val onLongClickListener = object : TaskItemAdapter.OnLongClick{
           override fun onItemLongClicked(position: Int) {
               //remove from list
               listOfTask.removeAt(position)
               //notify adapter
               adapter.notifyDataSetChanged()

               saveItems()
           }
       }
/*        listOfTask.add("hello muahah")
        listOfTask.add("this is test stuff")*/
        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTask, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)
        // That's all!

        // detects when user clicks on the add button
        findViewById<Button>(R.id.button).setOnClickListener{
            Log.i("manny","User clicked on button")
            //grab the text from user input
            val input =findViewById<EditText>(R.id.addTaskField).text.toString()

            // add string to list
            listOfTask.add(input)

            //notify adapter
            adapter.notifyItemChanged(listOfTask.size - 1)

            //clear text field
            findViewById<EditText>(R.id.addTaskField).setText("")

            saveItems()
        }
    }

    //save data user inputs by r/w to file

    //make a function to get file we need
    fun getDataFile(): File {

        return File(filesDir,"data.txt")
    }

    //load items to files
    fun loadItems(){
        try {
            listOfTask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // save items
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}