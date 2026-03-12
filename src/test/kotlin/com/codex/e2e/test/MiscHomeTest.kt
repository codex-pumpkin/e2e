package com.codex.e2e.test

import com.codex.e2e.PlaywrightTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MiscHomeTest : PlaywrightTest(true) {

    @Test
    fun `Toggling the language works`() {
        val germanLabel = page.getByText("Ideen in Code verwandeln")
        val englishLabel = page.getByText("Turn ideas into code")

        assertEquals(0, germanLabel.count())
        assertEquals(1, englishLabel.count())

        page.locator("nav")
            .locator("button")
            .last().click()

        assertEquals(1, germanLabel.count())
        assertEquals(0, englishLabel.count())
    }
}