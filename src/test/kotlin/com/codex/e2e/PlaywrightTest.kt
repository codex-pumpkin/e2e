package com.codex.e2e

import com.codex.e2e.E2EEnvironment.ADMIN_EMAIL
import com.codex.e2e.E2EEnvironment.ADMIN_PASSWORD
import com.codex.e2e.E2EEnvironment.BASE_URL
import com.microsoft.playwright.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

/**
 * Navigates to the root URL.
 */
fun Page.navRoot() = navigate(BASE_URL)

fun Page.wait(url: String) = waitForURL("$BASE_URL/$url")

private val DO_HEADLESS = System.getenv()["CODEX_E2E_HEADLESS"].toBoolean()

open class PlaywrightTest(val loginAsAdmin: Boolean = false) {

    companion object {

        private lateinit var playwright: Playwright
        private lateinit var browser: Browser

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            val options = BrowserType.LaunchOptions()
                .setHeadless(DO_HEADLESS)

            playwright = Playwright
                .create()

            browser = playwright.chromium().launch(options)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            browser.close()
        }

    }

    protected lateinit var context: BrowserContext
    protected lateinit var page: Page

    @BeforeEach
    fun beforeEach() {
        context = browser.newContext()
        page = context.newPage()

        page.navRoot()

        if (loginAsAdmin) {
            page.wait("login")
            page.locator("mat-form-field").nth(0).locator("input")
                .fill(ADMIN_EMAIL)
            page.locator("mat-form-field").nth(1).locator("input")
                .fill(ADMIN_PASSWORD)

            page.locator("button").getByText("Login").click()
            page.wait("home/conversations")
        }
    }

    @AfterEach
    fun afterEach() {
        context.close()
    }

    protected fun createConvo() {
        page.locator(".footer")
            .locator("prompt-input")
            .locator("input")
            .fill("Create a simple voltage divider.")

        page.locator("mat-form-field").nth(1).click()
        page.locator("mat-option").first().click()

        page.locator("#prompt-input-send").click()

        page.getByText("Prompt:").waitFor()
    }

}