<p align="center">
  <img src="Logo.svg" alt="Project Logo" width="500"/>
</p>

# CodeX End-to-End Tests

This repository contains the code for the CodeX End-to-End tests.

## Project Structure

- `src/test/kotlin/com/codex/e2e`: General Utility and configuration
    - `test`: Test files

## Running

To run, execute

```bash
./gradlew test
```

in the project root.

## Configuration

**All** following environment variables are required to be set.

| Environment Variable     | Explanation                                    | Format                 |
|--------------------------|------------------------------------------------|------------------------|
| CODEX_E2E_BASE           | Base URL of the client that should be tested   | URL, no trailing slash |
| CODEX_E2E_ADMIN_EMAIL    | Email of the admin account                     | Email                  |
| CODEX_E2E_ADMIN_USERNAME | Username of the admin account                  | String                 |
| CODEX_E2E_ADMIN_PASSWORD | Password of the admin account                  | String                 |
| CODEX_E2E_HEADLESS       | Whether the browser should be hidden           | Boolean                |
| CODEX_E2E_OAI_MODEL_NAME | Model name of the LLM that should be used      | String                 |
| CODEX_E2E_OAI_API_URL    | OpenAI API url that should be used for the LLM | URL                    |
| CODEX_E2E_OAI_API_TOKEN  | OpenAI API token for authentication            | String                 |