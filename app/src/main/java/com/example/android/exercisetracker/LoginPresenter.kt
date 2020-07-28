package com.example.android.exercisetracker

class LoginPresenter {
    private var view: View? = null
    private val DEFAULT_USERNAME = "test@wgu.edu"
    private val DEFAULT_PASSWORD = "password"

    fun attachView(view: View) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun login(username: String, password: String) {
        if (username.trim().isBlank() || password.trim().isBlank()) {
            view?.showCredentialsRequiredMessage()
        } else {
            if (username == DEFAULT_USERNAME && password == DEFAULT_PASSWORD) {
                view?.moveIntoNextActivity()
            } else {
                view?.showIncorrectCredentialsMessage()
            }
        }
    }

    // 6
    interface View {
        fun showCredentialsRequiredMessage()
        fun showIncorrectCredentialsMessage()
        fun moveIntoNextActivity()
    }
}