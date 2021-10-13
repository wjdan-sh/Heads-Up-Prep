package com.example.headsup_prep

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity3 : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etTaboo1: EditText
    private lateinit var etTaboo2: EditText
    private lateinit var etTaboo3: EditText
    private lateinit var btDelete: Button
    private lateinit var btUpdate: Button
    private lateinit var btBack: Button
    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    private var ID = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        ID = intent.extras!!.getInt("celebrityID", 0)
        etName = findViewById(R.id.etUpdateName)
        etTaboo1 = findViewById(R.id.etUpdateTaboo1)
        etTaboo2 = findViewById(R.id.etUpdateTaboo2)
        etTaboo3 = findViewById(R.id.etUpdateTaboo3)
        btDelete = findViewById(R.id.btDelete)
        btUpdate = findViewById(R.id.btUpdate)
        btBack = findViewById(R.id.btUpdateBack)

        btUpdate.setOnClickListener {
            if(etName.text.isNotEmpty() && etTaboo1.text.isNotEmpty() &&
                etTaboo2.text.isNotEmpty() && etTaboo3.text.isNotEmpty()){
                updateCelebrity()
            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }
        btBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        btDelete.setOnClickListener {
             customAlert()
        }


        getCelebrity()




    }
    private fun getCelebrity(){


        apiInterface.getCelebrity(ID).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                val celebrity = response.body()!!
                etName.setText(celebrity.name)
                etTaboo1.setText(celebrity.taboo1)
                etTaboo2.setText(celebrity.taboo2)
                etTaboo3.setText(celebrity.taboo3)
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {

                Toast.makeText(this@MainActivity3, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun updateCelebrity(){


        apiInterface.updateCelebrity(
            ID,
            Celebrity(
                etName.text.toString(),
                etTaboo1.text.toString(),
                etTaboo2.text.toString(),
                etTaboo3.text.toString(),
                ID
            )).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {

                Toast.makeText(this@MainActivity3, "Celebrity Updated", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {

                Toast.makeText(this@MainActivity3, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun deleteCelebrity(){


        apiInterface.deleteCelebrity(ID).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                Toast.makeText(this@MainActivity3, "Celebrity Deleted", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@MainActivity3, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun customAlert(){

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Are you sure?! want to delete? ")

            .setPositiveButton("yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
                deleteCelebrity()
            })

            .setNegativeButton("no", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()

        alert.setTitle(" Delete Celebrity ")


        alert.show()
    }
}