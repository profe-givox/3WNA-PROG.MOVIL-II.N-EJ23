package net.ivanvega.miappconfeisbukej2023

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays


class MainActivity : AppCompatActivity() {

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private val EMAIL = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val info = packageManager.getPackageInfo(
                "net.ivanvega.miappconfeisbukej2023",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }

        //loginButton = (LoginButton)  findViewById(R.id.login_button);
        loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));

        // Callback registration
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                // App code
                Toast.makeText(this@MainActivity,"Exito", Toast.LENGTH_SHORT).show()

                val request = GraphRequest.newMeRequest(
                    loginResult.accessToken,
                    object : GraphRequest.GraphJSONObjectCallback {

                        override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
                            //TODO("Not yet implemented")
                            Toast.makeText(this@MainActivity,obj.toString(),
                                Toast.LENGTH_SHORT).show()
                            Log.d("KeyHash:", obj
                                .toString())
                            findViewById<TextView>(R.id.textView).text = obj?.getString("email")
                        }


                    })
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")
                request.parameters = parameters
                request.executeAsync()

            }

            override fun onCancel() {
                // App code
                Toast.makeText(this@MainActivity,"Cancel", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                // App code
                Toast.makeText(this@MainActivity,"Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }
}