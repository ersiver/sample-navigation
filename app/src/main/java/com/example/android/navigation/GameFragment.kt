/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is Android Jetpack?",
                    answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
            Question(text = "What is the base class for layouts?",
                    answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            Question(text = "What layout do you use for complex screens?",
                    answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            Question(text = "What do you use to push structured data into a layout?",
                    answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")),
            Question(text = "What method do you use to inflate layouts in fragments?",
                    answers = listOf("onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
            Question(text = "What's the build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "Which class do you use to create a vector drawable?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Which one of these is an Android navigation component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Which XML element lets you register an activity with the launcher activity?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "What do you use to mark a layout for data binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment:
        //1st way:
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)
        //2nd way:
        //val binding = FragmentGameBinding.inflate(inflater)

        //Bind this fragment class to the layout variable.
        binding.game = this

        randomizeQuestions()

        // Set the onClickListener on button
        binding.submitButton.setOnClickListener{view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // Navigate to the gameWonFragment

                        //1st way: view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        //2nd way, with safe-arg:
                        view.findNavController()
                                .navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
                    }
                } else {
                    // Navigate to the GameOverFragment
                    //1st way:view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
                    //2nd way, with safe-arg:
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }
        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
