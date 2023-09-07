package com.sanskar.jmdwebstudio

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.sanskar.jmdwebstudio.VerifyOTP.VerifyOTPResponse
import com.sanskar.jmdwebstudio.VerifyOTP.VerifyOTPService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.GenericArrayType
import java.util.*

class otp_verification : AppCompatActivity() {
    private lateinit var otp_1: EditText
    private lateinit var otp_2: EditText
    private lateinit var otp_3: EditText
    private lateinit var otp_4: EditText
    private lateinit var next2: ImageView
    private lateinit var otp: String
    private lateinit var email: String
    private lateinit var hash: String
    private lateinit var otp_msg: TextView
    private lateinit var text2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        email = intent.getStringExtra("email").toString()
        hash = intent.getStringExtra("hash").toString()
        next2 = findViewById(R.id.next2)
        otp_1 = findViewById(R.id.otp_1)
        otp_2 = findViewById(R.id.otp_2)
        otp_3 = findViewById(R.id.otp_3)
        otp_4 = findViewById(R.id.otp_4)
        otp_msg = findViewById(R.id.otp_msg)
        text2 = findViewById(R.id.text2)

        otp_1.addTextChangedListener(GenericTextWatcher(otp_1, otp_2))
        otp_2.addTextChangedListener(GenericTextWatcher(otp_2, otp_3))
        otp_3.addTextChangedListener(GenericTextWatcher(otp_3, otp_4))
        otp_4.addTextChangedListener(GenericTextWatcher(otp_4, next2))

        otp_1.setOnKeyListener(GenericKeyEvent(otp_1, null))
        otp_2.setOnKeyListener(GenericKeyEvent(otp_2, otp_1))
        otp_3.setOnKeyListener(GenericKeyEvent(otp_3, otp_2))
        otp_4.setOnKeyListener(GenericKeyEvent(otp_4, otp_3))

        next2.setOnClickListener {
            otp = otp_1.text.toString() + otp_2.text.toString() + otp_3.text.toString() + otp_4.text.toString()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service: VerifyOTPService = retrofit.create<VerifyOTPService>(VerifyOTPService::class.java)
            val listCall: Call<VerifyOTPResponse> = service.verifyOTP(email, hash, otp)

            listCall.enqueue(object: Callback<VerifyOTPResponse> {
                override fun onResponse(call: Call<VerifyOTPResponse>, response: Response<VerifyOTPResponse>) {
                    if(response.isSuccessful){
                        val verifyOtp: VerifyOTPResponse = response.body()!!
                        Log.i("Verify Otp", "$verifyOtp")
                        if(verifyOtp.success){
                            otp_msg.text = "OTP matched"
                            otp_msg.setTextColor(Color.parseColor("#37C298"))
                            otp_msg.alpha = 1.0f
                            text2.text = "Keep patience for a few seconds!"
                            Toast.makeText(this@otp_verification, verifyOtp.message, Toast.LENGTH_SHORT).show()
//                            if(verifyOtp.user.isActivate){
//                                val intent = Intent(this@otp_verification, AirCargo::class.java)
//                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@otp_verification).toBundle())
//                            }
//                            else{
                                val intent = Intent(this@otp_verification, successful_registration::class.java)
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@otp_verification).toBundle())
//                            }
                        }
                        else{
                            otp_msg.text = "OTP not matched"
                            otp_msg.setTextColor(Color.parseColor("#EE9090"))
                            otp_msg.alpha = 1.0f
                            text2.text = "Check the OTP again!"
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

                override fun onFailure(call: Call<VerifyOTPResponse>, t: Throwable) {
                    Log.e("Error!!!", t.message.toString())
                }

            })
//            if(otp == "5555") {
//                val intent = Intent(this@otp_verification, successful_registration::class.java)
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
//            }
        }
    }
    class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.otp_1 && currentView.text.isEmpty()) {
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }
    class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.otp_1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otp_4 -> if (text.length == 1) nextView!!.requestFocus()
            }
        }
        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }
        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }
    }
}
