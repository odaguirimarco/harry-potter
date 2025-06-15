# Harry Potter Elixirs Console Application

Welcome! This is a Spring Boot console application that helps you explore ingredients and find matching elixirs (potions) based on the ingredients you have—all from your terminal.

---

## How to Run

1. **Prerequisites:**
    - Java SDK 24 or newer installed.
    - Recommended IDE: IntelliJ IDEA (Ultimate or Community), or use the command line.

2. **Start the Application:**
    - Open a terminal window in the root of the project folder.
    - Use the following command to run:
      ```bash
      ./mvnw spring-boot:run
      ```
      or
      ```bash
      mvn spring-boot:run
      ```
    - The application will launch in interactive mode in your terminal.
    - You will see a welcome banner and a menu with options.

3. **Available Menu Options:**
    - **List all ingredients:** See all available potion ingredients.
    - **Match elixirs (provide ingredients):** Enter a comma-separated list of ingredients to find which elixirs you can potentially brew.
    - **Quit:** Exit the application.

---

## How to Test

You can test the application in two ways:

### 1. Automated Unit

- Run all tests with:
  ```bash
  ./mvnw test
  ```
- This will execute the test suite to make sure the internal logic works as expected.

### 2. Manual Testing (Recommended)

- Start the application (see **How to Run** above).
- Use the interactive menu to:
    - List all ingredients by choosing option 1.
    - Test the elixir matching by choosing option 2, then entering different combinations of ingredients (e.g., `root of asphodel, powdered root of valerian`).
- You can verify its responses directly in the terminal.

---

## About the In-Memory Cache

To keep the application responsive and avoid making redundant external API calls every time you ask for ingredients or match elixirs, this project uses an **in-memory cache**.

**What you need to know:**
- When you request the list of ingredients, the data is fetched from the external API **only once** and stored in memory for a short period (10 minutes by default).
- Any repeated requests for ingredient or elixir information within that time will use the cached results, making it much faster.
- This cache is temporary and will be cleared each time you restart the application.

---

## Notes

- This is a command line (console) application and does not expose web endpoints or a REST API. All interaction happens in your terminal window.
- For troubleshooting, ensure you are running with Java 24 or newer and have internet access for the first API calls (to fetch data).

---

Happy potion making! 🧪✨