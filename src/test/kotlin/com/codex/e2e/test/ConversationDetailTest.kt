package com.codex.e2e.test

import com.codex.e2e.PlaywrightTest
import org.junit.jupiter.api.Test

class ConversationDetailTest : PlaywrightTest(true) {

    @Test
    fun `Creating a new Conversation works`() {
        page.locator(".footer")
            .locator("prompt-input")
            .locator("input")
            .fill("Create a simple voltage divider.")

        page.locator("mat-form-field").first().click()
        page.locator("mat-option").first().click()

        page.locator("#prompt-input-send").click()

        val progressVisible = 1 == page.getByText("Your input is being processed…").count()
        assert(progressVisible)

        page.getByText("Prompt:").waitFor()

        page.waitForDownload {
            page.locator("#circuit-download").click()
        }

        page.locator("mat-form-field").first().click()
        page.locator("mat-option").nth(2).click()

        val circuitVisible = 1 == page.locator("circuit-graph").count()
        assert(circuitVisible)
    }
}