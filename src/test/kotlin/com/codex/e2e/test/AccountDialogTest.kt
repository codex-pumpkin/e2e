package com.codex.e2e.test

import com.codex.e2e.E2EEnvironment.ADMIN_EMAIL
import com.codex.e2e.E2EEnvironment.ADMIN_USERNAME
import com.codex.e2e.E2EEnvironment.BASE_URL
import com.codex.e2e.PlaywrightTest
import com.codex.e2e.wait
import com.microsoft.playwright.Locator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AccountDialogTest : PlaywrightTest(true) {

    private fun openDialog() {
        page.locator(".open-account-dialog").click()
    }

    @Test
    fun `Shows correct username and email`() {
        openDialog()

        val username = page.locator("#username-container").getByText(ADMIN_USERNAME, Locator.GetByTextOptions().setExact(true))
        val email = page.locator("#email").getByText(ADMIN_EMAIL, Locator.GetByTextOptions().setExact(true))

        assertEquals(1, username.count())
        assertEquals(1, email.count())
    }

    @Test
    fun `Changing the username works`() {
        openDialog()

        page.locator("#change-username-button").click()

        page.locator("mat-form-field").last().locator("input").fill("username-123")
        page.locator("button").getByText("Confirm").click()

        // Time for username change
        page.waitForTimeout(200.0)

        val username = page.locator("#username-container").getByText("username-123", Locator.GetByTextOptions().setExact(true))
        assertEquals(1, username.count())

        // Reset username for future tests

        page.locator("#change-username-button").click()

        page.locator("mat-form-field").last().locator("input").fill(ADMIN_USERNAME)
        page.locator("button").getByText("Confirm").click()

        // Time for username change
        page.waitForTimeout(200.0)

        val usernameRenewed = page.locator("#username-container").getByText(ADMIN_USERNAME, Locator.GetByTextOptions().setExact(true))
        assertEquals(1, usernameRenewed.count())
    }

    @Test
    fun `Requesting password reset email works`() {
        openDialog()

        page.getByText("Reset password").click()
        page.getByText("Confirm").click()
    }

    @Test
    fun `Changing email works`() {
        openDialog()

        page.locator("#change-email-button").click()
        page.locator("#new-email-input").locator("input").fill("new@email.com")
        page.locator("button").last().click()

        page.wait("login")
    }

    @Test
    fun `Logging out works`() {
        openDialog()

        page.locator("button").getByText("Log out").click()

        page.wait("login")

        page.navigate("$BASE_URL/home/conversations")
        page.wait("login")
    }

}