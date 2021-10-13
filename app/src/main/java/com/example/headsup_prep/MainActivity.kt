package com.example.headsup_prep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    private lateinit var btAdd: Button
    private lateinit var etCelebrity: EditText
    private lateinit var submit: Button

    private lateinit var celebrities: ArrayList<Celebrity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        celebrities = arrayListOf()

        rv = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(celebrities)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        btAdd = findViewById(R.id.btAdd)
        etCelebrity = findViewById(R.id.etCelebrity)
        submit = findViewById(R.id.btDetails)



        getCelebrities()

        submit.setOnClickListener {
            if(etCelebrity.text.isNotEmpty()){
                updateCelebrity()
            }else{
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
            }
        }

        btAdd.setOnClickListener {
            intent = Intent(applicationContext, MainActivity2::class.java)
            val celebrityNames = arrayListOf<String>()
            for(c in celebrities){
                celebrityNames.add(c.name.lowercase())
            }
            intent.putExtra("celebrityNames", celebrityNames)
            startActivity(intent)
        }


    }

    private fun getCelebrities(){
        apiInterface.getCelebrities().enqueue(object: Callback<ArrayList<Celebrity>> {
            override fun onResponse(
                call: Call<ArrayList<Celebrity>>,
                response: Response<ArrayList<Celebrity>>
            ) {

                celebrities = response.body()!!
                rvAdapter.update(celebrities)
                rv.scrollToPosition(celebrities.size-1)

            }

            override fun onFailure(call: Call<ArrayList<Celebrity>>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateCelebrity(){
        var ID = 0
        for(celebrity in celebrities){
            if(etCelebrity.text.toString().capitalize() == celebrity.name){
                ID = celebrity.pk
            }
        }
        if (ID !== 0){

            intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("celebrityID", ID)
            startActivity(intent)
        }else{
            Toast.makeText(this, "${etCelebrity.text.toString().capitalize()} not found", Toast.LENGTH_LONG).show()
        }

    }
}