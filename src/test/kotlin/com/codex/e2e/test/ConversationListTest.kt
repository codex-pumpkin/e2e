package com.codex.e2e.test

import com.codex.e2e.PlaywrightTest
import com.codex.e2e.wait
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConversationListTest : PlaywrightTest(true) {

    @Test
    fun `Interacting with the Conversation List works`() {
        createConvo()
        openNewConvo()
        createConvo()
        openNewConvo()
        createConvo()

        val convoSelector = page.locator(".nav-item")

        val convos = List(3) { convoSelector.nth(2 + it) }

        // Renaming

        convos.forEachIndexed { index, convo ->
            convo.locator("button").last().click()
            page.getByText("Rename").click()

            page.locator("mat-form-field").locator("input").fill("Conversation ${index + 1}")

            page.getByText("Confirm").click()
        }

        // Searching

        page.locator(".conversation-header")
            .locator("button")
            .last().click()

        page.locator(".conversation-header")
            .locator("input")
            .fill("Conversation 1")

        assert(convos[0].count() == 1)
        assert(convos[1].count() == 0)
        assert(convos[2].count() == 0)

        page.locator(".conversation-header")
            .locator("button")
            .last().click()

        // Deleting
        convos[0].locator("button").last().click()
        page.locator("span").getByText("Delete").click()

        // Need to wait for the convo to be actually deleted
        page.waitForTimeout(500.0)

        val convo1 = page.getByText("Conversation 1")
        assertEquals(0, convo1.count())

        // Pinning
        val divider = page.locator("mat-divider")
        assertEquals(0, divider.count())

        convos[0].locator("button").last().click()
        page.locator("span").getByText("Pin").click()
        page.waitForTimeout(500.0)

        // Need to wait for the convo to actually be deleted
        assertEquals(1, divider.count())
    }

    private fun openNewConvo() {
        page.locator(".add-conversation-button").first().click()
        page.wait("home/conversations")
    }
}