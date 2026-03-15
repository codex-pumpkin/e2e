package com.codex.e2e.test

import com.codex.e2e.E2EEnvironment.ADMIN_EMAIL
import com.codex.e2e.E2EEnvironment.ADMIN_PASSWORD
import com.codex.e2e.E2EEnvironment.ADMIN_USERNAME
import com.codex.e2e.E2EEnvironment.BASE_URL
import com.codex.e2e.PlaywrightTest
import com.codex.e2e.wait
import org.junit.jupiter.api.Test
import kotlin.test.expect

class AuthFlowTest : PlaywrightTest() {

    @Test
    fun `Initial position is login`() {
        page.wait("login")
        expect("$BASE_URL/login", page::url)
    }

    private fun loginToSignup() {
        page.locator("a").getByText("Register")
            .click()
        page.wait("signup")

        expect("$BASE_URL/signup", page::url)
    }

    @Test
    fun `Signing up works`() {
        page.wait("login")
        loginToSignup()

        page.locator("mat-form-field").nth(0).locator("input")
            .fill("email@example.com")
        page.locator("mat-form-field").nth(1).locator("input")
            .fill("user1234")
        page.locator("mat-form-field").nth(2).locator("input")
            .fill("abcd1234")

        page.locator("button").getByText("Register").click()

        page.wait("email-not-verified")

        expect("$BASE_URL/email-not-verified", page::url)
    }

    private fun loginAsAdmin(identifier: String) {
        page.locator("mat-form-field").nth(0).locator("input")
            .fill(identifier)
        page.locator("mat-form-field").nth(1).locator("input")
            .fill(ADMIN_PASSWORD)

        page.locator("button").getByText("Login").click()
    }

    @Test
    fun `Logging in with Admin Credentials works, email`() {
        page.wait("login")
        loginAsAdmin(ADMIN_EMAIL)

        page.wait("home/conversations")
        expect("$BASE_URL/home/conversations", page::url)
    }

    @Test
    fun `Reset password works`() {
        page.wait("login")

        page.locator("a").getByText("Reset").click()
        page.wait("request-password-reset")

        page.locator("mat-form-field").nth(0).locator("input")
            .fill(ADMIN_EMAIL)

        page.locator("button").getByText("Reset password").click()

        page.getByText("A link to reset your password has been sent to your email address.").waitFor()

        page.locator("button").getByText("Continue").click()

        page.wait("login")
    }

}