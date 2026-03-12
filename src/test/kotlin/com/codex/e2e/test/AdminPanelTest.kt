package com.codex.e2e.test

import com.codex.e2e.E2EEnvironment.BASE_URL
import com.codex.e2e.PlaywrightTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AdminPanelTest : PlaywrightTest(true) {

    private fun goToAdminPanel() {
        page.navigate("$BASE_URL/home/admin-panel")
    }

    @Test
    fun `Changing Quota works`() {
        goToAdminPanel()

        // No quota for admins
        page.getByText("Administrators").click()

        // Quota for members of 67 / month
        page.locator(".quota-container").nth(1).locator("mat-form-field").locator("input").fill("67")

        page.reload()

        val memberQuota = page.locator(".quota-container").nth(1).locator("mat-form-field").locator("input").inputValue()
        assertEquals("67", memberQuota)

        val adminQuota = page.locator(".quota-container").nth(0).locator("mat-form-field").locator("input").inputValue()
        assertEquals("", adminQuota)

        // Reset both
        page.locator(".quota-container").nth(1).locator("mat-form-field").locator("input").fill("20")
        page.getByText("Administrators").click()
    }

    @Test
    fun `Statistics are displayed`() {
        val today = page.getByText("Requests today:")
        val month = page.getByText("Requests this month:")

        assertEquals(0, today.count())
        assertEquals(0, month.count())
    }

}