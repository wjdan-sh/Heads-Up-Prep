package com.example.headsup_prep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etTaboo1: EditText
    private lateinit var etTaboo2: EditText
    private lateinit var etTaboo3: EditText
    private lateinit var btAdd: Button
    private lateinit var btBack: Button
    private lateinit var celebrities: ArrayList<Celebrity>

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)


    private lateinit var existingCelebrities: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        celebrities = arrayListOf()

        existingCelebrities = intent.extras!!.getStringArrayList("celebrityNames")!!

        etName = findViewById(R.id.etNewName)
        etTaboo1 = findViewById(R.id.etNewTaboo1)
        etTaboo2 = findViewById(R.id.etNewTaboo2)
        etTaboo3 = findViewById(R.id.etNewTaboo3)
        btAdd = findViewById(R.id.btNewAdd)
        btBack = findViewById(R.id.btNewBack)

        btAdd.setOnClickListener {

            if(etName.text.isNotEmpty() && etTaboo1.text.isNotEmpty() &&
                etTaboo2.text.isNotEmpty() && etTaboo3.text.isNotEmpty()){

                    addCelebrity()

            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }

        btBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun addCelebrity() {

                apiInterface.addCelebrity(
                    Celebrity(
                        etName.text.toString().capitalize(),
                        etTaboo1.text.toString(),
                        etTaboo2.text.toString(),
                        etTaboo3.text.toString(),
                        0
                    )
                ).enqueue(object : Callback<Celebrity> {
                    override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                        if (!existingCelebrities.contains(etName.text.toString().lowercase())) {

                            Toast.makeText(this@MainActivity2, "Celebrity added", Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(this@MainActivity2, "Celebrity Already Exists", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Celebrity>, t: Throwable) {

                        Toast.makeText(this@MainActivity2, "Unable to get data", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
