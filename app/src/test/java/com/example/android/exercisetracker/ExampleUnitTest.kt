package com.example.android.exercisetracker

import org.junit.Test
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginTests {
    @Test
    fun loginWithEmptyCredentials_callsShowCredentialsRequiredMessage() {
        val presenter = LoginPresenter()

        val view: LoginPresenter.View = mock()
        presenter.attachView(view)

        presenter.login("", "")

        verify(view).showCredentialsRequiredMessage()
    }
}
