package com.example.numbersgameapp



import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var clRoot: ConstraintLayout
    private lateinit var guessField: EditText
    private lateinit var guessButton: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var tvPrompt: TextView

    private var answer = 0
    private var numOfGus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer = Random.nextInt(10)

        clRoot = findViewById(R.id.clRoot)
        messages = ArrayList()

        tvPrompt = findViewById(R.id.tvPrompt)

        rvMsgs.adapter = MessageAdapter(this, messages)
        rvMsgs.layoutManager = LinearLayoutManager(this)

        guessField = findViewById(R.id.etGuessField)
        guessButton = findViewById(R.id.btGuessButton)
        var i = 3
        guessButton.setOnClickListener {
            val msg = guessField.text.toString()

            if(msg.isNotEmpty()){
                if(numOfGus<4){

                    if(msg.toInt() == answer){
                        guessButton.isEnabled = false
                        guessButton.isClickable = false
                        guessField.isEnabled = false
                        guessField.isClickable = false
                        var mm = "You win! Play again?"
                        showAlert(mm)
                    }else{
                        numOfGus++
                        messages.add("You guessed $msg")
                        i--
                        messages.add("You have $i guesses left")
                    }
                    if(numOfGus==3){

                        messages.add("You lose - The correct answer was $answer")
                        messages.add("Game Over")
                        var mm ="You lose The correct answer was $answer.you want to Play again?"
                        showAlert(mm)
                        guessButton.isEnabled = false
                        guessButton.isClickable = false
                        guessField.isEnabled = false
                        guessField.isClickable = false
                    }
                }
                guessField.text.clear()
                guessField.clearFocus()
                rvMsgs.adapter?.notifyDataSetChanged()
            }else{
                Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()
            } }
    }






    private fun showAlert(title: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(title)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }
}
