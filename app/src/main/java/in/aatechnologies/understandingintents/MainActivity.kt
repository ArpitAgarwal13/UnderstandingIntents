package `in`.aatechnologies.understandingintents

import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar



const val PERMISSION_REQUEST_PHONE_CALL = 0

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // IMPLICIT INTENT
        // find View By id function is used to find and map our views from UI to code
        val launchUrlButton = findViewById<Button>(R.id.LaunchUrl)

        // setOnClickListener is event listener, whenever an user clicks on this button, code block
        // will run
        launchUrlButton.setOnClickListener {

            // Defining an intent
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://www.google.com")
            }


            // Checking if any app in our system is designed to handle this intent
            // Basically all apps in their manifest file defines the task which they perform
            // and using package manager we check if any app task match with our requirement
            if(intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "No Activity Found to handle this intent", Toast.LENGTH_SHORT).show()
            }
        }


        // EXPLICIT INTENT
        val launchActivity = findViewById<Button>(R.id.launch_hello_activity)

        launchActivity.setOnClickListener {
            val intent = Intent(this, HelloActivity::class.java).apply {
                putExtra("NAME", "JOHN")
            }
            startActivity(intent)
        }


        // Runtime Permissions
        // For runtime permissions we need to add in manifest file uses_permission
        val callSupportButton = findViewById<Button>(R.id.call_support_button)
        callSupportButton.setOnClickListener {
            makePhoneCallAfterPermission(it)
        }
    }


    /**
     * When Using Runtime Permission, We need a way to know whether user granted the permission or not
     * For this, we implement ActivityCompat.OnRequestPermissionsResultCallback interface and override
     * this function. Whenever user accepts or denies permission we get a call back here
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_REQUEST_PHONE_CALL) {
            if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            }

            else {
                Toast.makeText(this, "Permission Denied To make Phone Call", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * Checking IF we already have the permission or not
     */
    private fun makePhoneCallAfterPermission(view : View) {

        // Here We are Checking if we already have the permission or not
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall()
        }
        else {
            requestCallPermission(view)
        }

    }

    /**
     * For Making Phone Call
     */
    private fun makePhoneCall() {
        val intent = Intent().apply {
            action = ACTION_CALL
            data = Uri.parse("tel: 7665303481")
        }

        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
        else {
            Toast.makeText(this, "No Activity Found to Handle Calls Intent", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Asking Permission From the user
     */
    private fun requestCallPermission(view : View) {

        // If the user have denied the permission previously than this rationale is shown
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {

            val snack = Snackbar.make(
                view, "We need your permission to make a call." +
                        " \n When asked please give the permission", Snackbar.LENGTH_INDEFINITE
            )
            snack.setAction("OK", View.OnClickListener {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    PERMISSION_REQUEST_PHONE_CALL
                )
            }).show()
        }

        else {

            // This is how we request the permission From the user
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_PHONE_CALL)
        }
    }
}