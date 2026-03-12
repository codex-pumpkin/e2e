package com.codex.e2e

import com.codex.e2e.E2EEnvironment.ADMIN_EMAIL
import com.codex.e2e.E2EEnvironment.ADMIN_PASSWORD
import com.codex.e2e.E2EEnvironment.BASE_URL
import com.codex.e2e.E2EEnvironment.DO_HEADLESS
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.function.Function

class GlobalLifecycleExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        context.root
            .getStore(ExtensionContext.Namespace.GLOBAL)
            .getOrComputeIfAbsent<String?, E2EInitializer?>("lifecycle", Function { key: String? -> E2EInitializer })
    }
}

private object E2EInitializer: ExtensionContext.Store.CloseableResource {

    init {
        val (playwright, browser) = createPlaywright()
        val page = browser.newPage()

        login(page)
        createModel(page)

        page.close()
        browser.close()
        playwright.close()
    }

    override fun close() {
        val (playwright, browser) = createPlaywright()
        val page = browser.newPage()

        login(page)
        removeConvos(page)
        removeModel(page)

        page.close()
        browser.close()
        playwright.close()
    }

    private fun removeConvos(page: Page) {
        page.navigate("$BASE_URL/home/conversations")
        page.wait("home/conversations")

        val itemLocator = page.locator(".convos-container").locator(".nav-item")

        while (itemLocator.count() > 0) {
            itemLocator.first().locator("button").last().click()
            page.locator("span").getByText("Delete").click()
            page.waitForTimeout(200.0)
        }
    }

    private fun removeModel(page: Page) {
        page.navigate("$BASE_URL/home/settings")
        page.wait("home/settings")

        page.locator(".draft-item").locator("button").last().click()

        page.getByText("Save changes")
        page.waitForTimeout(200.0)
    }

    private fun createModel(page: Page) {
        page.navigate("$BASE_URL/home/settings")
        page.wait("home/settings")

        page.getByText("Add model").click()

        page.locator("mat-form-field").nth(0).locator("input").fill("E2E Model")
        page.locator("mat-form-field").nth(1).locator("input").fill(E2EEnvironment.OAI_MODEL_NAME)
        page.locator("mat-form-field").nth(2).locator("input").fill(E2EEnvironment.OAI_API_URL)
        page.locator("mat-form-field").nth(3).locator("input").fill(E2EEnvironment.OAI_API_TOKEN)

        page.getByText("Save changes").click()

        page.waitForTimeout(200.0)
    }

    private fun createPlaywright(): Pair<Playwright, Browser> {
        val options = BrowserType.LaunchOptions()
            .setHeadless(DO_HEADLESS)

        val playwright = Playwright
            .create()

        val browser = playwright.chromium().launch(options)

        return Pair(playwright, browser)
    }

    private fun login(page: Page) {
        page.navigate("$BASE_URL/login")
        page.wait("login")
        page.locator("mat-form-field").nth(0).locator("input")
            .fill(ADMIN_EMAIL)
        page.locator("mat-form-field").nth(1).locator("input")
            .fill(ADMIN_PASSWORD)

        page.locator("button").getByText("Login").click()
        page.wait("home/conversations")
    }

}