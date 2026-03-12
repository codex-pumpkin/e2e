package com.codex.e2e

private fun env(key: String) = System.getenv(key) ?: ""

object E2EEnvironment {
    val BASE_URL
        get() = env("CODEX_E2E_BASE")

    val ADMIN_EMAIL
        get() = env("CODEX_E2E_ADMIN_EMAIL")
    val ADMIN_USERNAME
        get() = env("CODEX_E2E_ADMIN_USERNAME")
    val ADMIN_PASSWORD
        get() = env("CODEX_E2E_ADMIN_PASSWORD")

    val DO_HEADLESS
        get() = env("CODEX_E2E_HEADLESS").toBooleanStrict()

    val OAI_MODEL_NAME
        get() = env("CODEX_E2E_OAI_MODEL_NAME")
    val OAI_API_URL
        get() = env("CODEX_E2E_OAI_API_URL")
    val OAI_API_TOKEN
        get() = env("CODEX_E2E_OAI_API_TOKEN")
}