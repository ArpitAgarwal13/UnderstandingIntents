package `in`.aatechnologies.understandingintents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val guestName : String? = intent.getStringExtra("NAME")

        guestName?.let {
            val name = findViewById<TextView>(R.id.hello_text_view)
            name.text = "Hello $it"

            Toast.makeText(this, "This activity is launched By $it", Toast.LENGTH_SHORT).show()
        }
    }
}