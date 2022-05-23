package `in`.aatechnologies.understandingintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
    }
}