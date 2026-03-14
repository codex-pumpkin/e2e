package com.codex.e2e.test

import com.codex.e2e.E2EEnvironment.BASE_URL
import com.codex.e2e.PlaywrightTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LlmConfigTest : PlaywrightTest(true) {

    private fun goToLlmConfigs() {
        page.navigate("$BASE_URL/home/settings")
    }

    @Test
    fun `Adding and removing Models works`() {
        goToLlmConfigs()

        page.getByText("Add model").click()

        page.locator("mat-form-field").nth(4).locator("input").fill("Model 1")
        page.locator("mat-form-field").nth(5).locator("input").fill("model-1.0")
        page.locator("mat-form-field").nth(6).locator("input").fill("http://example.com")

        page.getByText("Save changes").click()

        page.reload()
        page.waitForTimeout(200.0)

        val configCount = page.locator(".draft-item")
        assertEquals(2, configCount.count())

        // Delete added model again
        page.locator(".draft-item").locator("button").last().click()
        page.getByText("Save changes").click()
        page.waitForTimeout(200.0)
        assertEquals(1, configCount.count())
    }

}