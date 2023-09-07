package com.sanskar.jmdwebstudio

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.sanskar.jmdwebstudio.SignUp.SignUpResponse
import com.sanskar.jmdwebstudio.SignUp.SignUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var email_input: EditText
    private lateinit var next: ImageView
    private var success: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email_input = findViewById(R.id.email_input)
        next = findViewById(R.id.next)
        next.setOnClickListener {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service: SignUpService = retrofit.create<SignUpService>(SignUpService::class.java)
            val listCall: Call<SignUpResponse> = service.getOtp(email_input.text.toString())

            listCall.enqueue(object: Callback<SignUpResponse> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    if(response.isSuccessful){
                        val signUpList: SignUpResponse = response.body()!!
                        Log.i("Sign Up Result", "$signUpList")
                        success = signUpList.success
                        if(success) {
                            Toast.makeText(this@MainActivity, signUpList.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, otp_verification::class.java)
                            intent.putExtra("email", email_input.text)
                            intent.putExtra("hash", signUpList.hash)
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
                        }
                    }
                    else{
                        val rc = response.code()
                        when(rc){
                            400 -> Log.e("Error 400", "Bad Connection!!")
                            404 -> Log.e("Error 404", "Not Found")
                            else -> Log.e("Error", "Generic Error")
                        }
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e("Error!!!", t.message.toString())
                }

            })
        }
    }
}