package com.example.quizapp
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    // setting up things
    private var falseButton: Button? = null
    private var trueButton: Button? = null
    private var nextButton: ImageButton? = null
    private var prevButton: ImageButton? = null
    private var image: ImageView? = null
    private var questionTextView: TextView? = null
    private var correct = 0

    // to keep current question track
    private var currentQuestionIndex = 0
    private val questionBank: Array<Question> =
        arrayOf( // array of objects of class Question
            // providing questions from string
            // resource and the correct ans

            Question(R.string.a, true),
            Question(R.string.b, false),
            Question(R.string.c, false),
            Question(R.string.d, true),
            Question(R.string.e, false),
            Question(R.string.f, true)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setting up the buttons
        // associated with id
        falseButton = findViewById(R.id.false_button)
        trueButton = findViewById(R.id.true_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        // register our buttons to listen to
        // click events
        questionTextView = findViewById(R.id.answer_text_view)
        image = findViewById(R.id.ic_amumu_background)
        //image!!.setImageResource(R.drawable.ic_amumu_foreground)
        falseButton!!.setOnClickListener(this)
        trueButton!!.setOnClickListener(this)
        nextButton!!.setOnClickListener(this)
        prevButton!!.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View) {
        // checking which button is
        // clicked by user
        // in this case user choose false
        when (v.id) {
            R.id.false_button -> checkAnswer(false)
            R.id.true_button -> checkAnswer(true)
            R.id.next_button ->            // go to next question
                // limiting question bank range
                if (currentQuestionIndex < 7) {
                    currentQuestionIndex += 1
                    // we are safe now!
                    // last question reached
                    // making buttons
                    // invisible
                    if (currentQuestionIndex == 6) {
                        questionTextView!!.text = getString(
                            R.string.correct, correct
                        )
                        nextButton!!.visibility = View.INVISIBLE
                        prevButton!!.visibility = View.INVISIBLE
                        trueButton!!.visibility = View.INVISIBLE
                        falseButton!!.visibility = View.INVISIBLE
                        if (correct > 3) questionTextView!!.text = ("CORRECTNESS IS " + correct
                                + " "
                                + "OUT OF 6") else image!!.setImageResource(
                            R.drawable.ic_crying_background
                        )
                        // if correctness<3 showing sad emoji
                    } else {
                        updateQuestion()
                    }
                }
            R.id.prev_button -> if (currentQuestionIndex > 0) {
                currentQuestionIndex = ((currentQuestionIndex - 1)
                        % questionBank.size)
                updateQuestion()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun updateQuestion() {
        Log.d(
            "Current",
            "onClick: $currentQuestionIndex"
        )
        questionTextView!!.text = getString(questionBank[currentQuestionIndex].answerResId)

        when (currentQuestionIndex) {
            1 ->            // setting up image for each
                // question
                image!!.setImageResource(R.drawable.ic_amumu_background)
            2 -> image!!.setImageResource(R.drawable.ic_arrow_left_background)
            3 -> image!!.setImageResource(R.drawable.ic_arrow_right_background)
            4 -> image!!.setImageResource(R.drawable.ic_crying_background)
            5 -> image!!.setImageResource(R.drawable.ic_flower_background)
            6 -> image!!.setImageResource(R.drawable.ic_launcher_background)
            7 -> image!!.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    private fun checkAnswer(userChooseCorrect: Boolean) {
        val answerIsTrue: Boolean = questionBank[currentQuestionIndex].isAnswerTrue
        // getting correct ans of current question
        val toastMessageId: Int
        // if ans matches with the
        // button clicked
        if (userChooseCorrect == answerIsTrue) {
            toastMessageId = R.string.correct_answer
            correct++
        } else {
            // showing toast
            // message correct
            toastMessageId = R.string.wrong_answer
        }
        Toast
            .makeText(
                this@MainActivity, toastMessageId,
                Toast.LENGTH_SHORT
            )
            .show()
    }
}


