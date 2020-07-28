package com.example.android.exercisetracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginPresenter.View {
    private val presenter: LoginPresenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.attachView(this)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            presenter.login(username, password)
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showCredentialsRequiredMessage() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Snackbar.make(
            login, getString(R.string.no_credentials), Snackbar
                .LENGTH_LONG
        ).show()
    }

    override fun showIncorrectCredentialsMessage() {
        Snackbar.make(
            login, getString(R.string.wrong_credentials), Snackbar
                .LENGTH_LONG
        ).show()    }

    override fun moveIntoNextActivity() {
        val intent = Intent(this, PrepWorkoutActivity::class.java)
        startActivity(intent)    }
}
