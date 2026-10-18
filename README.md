# 🛒 Noon Automation Test

An automated testing project that opens a real web browser, visits the **Noon Egypt** online store ([noon.com/egypt-en](https://www.noon.com/egypt-en/)), and behaves like a shopper — filtering products, adding items to the cart, and searching — while **checking by itself that everything works correctly**. When it finishes, it produces a clean, clickable **report** showing what passed and what failed.



---

## 📑 Table of Contents

1. [What is "automation testing"?](#-what-is-automation-testing)
2. [Demo video](#-demo-video)
3. [What does this project test? (the 4 flows)](#-what-does-this-project-test-the-4-flows)
4. [Tools used](#-tools-used)
5. [What you need to install first](#-what-you-need-to-install-first)
6. [Step 1 – Download (clone) the project](#step-1--download-clone-the-project)
7. [Step 2 – Run the tests and see the report](#step-2--run-the-tests-and-see-the-report)
8. [Understanding the report](#-understanding-the-report)
9. [Changing the test data](#-changing-the-test-data)
10. [Changing the browser](#-changing-the-browser)
11. [Project structure](#-project-structure)
12. [Troubleshooting](#-troubleshooting)
13. [Good to know](#-good-to-know)
14. [Author](#-author)

---

## 🤖 What is "automation testing"?

Normally, to check that a website works, a person sits in front of it and clicks around: *"Does the search work? Does the cart add up correctly?"* Doing that by hand again and again is slow and boring.

**Automation testing** means we write a program that does those clicks **for us**:

| Done by hand 🧑 | Done by automation 🤖 |
|---|---|
| Open the browser | The program opens the browser |
| Click on a category | The program clicks on a category |
| Check the prices are right | The program checks the prices are right |
| Write down what went wrong | The program builds a report with screenshots |

You press **one button** (one command), watch the browser move by itself, and get a report at the end.

---

## 🎥 Demo video
https://github.com/user-attachments/assets/67575425-b97a-43d6-a9cc-780228ea520c

---

## 🧪 What does this project test? (the 4 flows)

A **flow** is one complete journey a shopper might take on the website. This project has **four flows**. Each one is a "mini story" that the program performs and verifies.

### Flow 1 — Filtering products (price & rating) 🔍
**The story:** *A shopper wants Samsung electronics between 1,000 and 3,000 EGP with a good rating.*

What the program does:
1. Opens the Noon Egypt home page and makes sure it loaded.
2. Clicks the **Electronics** category and checks it landed on the right page.
3. Picks the brand **Samsung** and checks the results page is about Samsung.
4. Opens the **Price** filter, types a minimum (1000) and maximum (3000) price, and applies it.
5. ✅ **Checks** that *every* product shown is inside that price range.
6. Opens the **Rating** filter, moves the slider to **3.7 stars**.
7. ✅ **Checks** that *every* product shown has a rating of 3.7 or higher.

> This flow has **two versions**: one where the price is **typed** into the boxes, and one where the price is set by **dragging/scrolling the slider**.
> The second version is speed up in the demo video and used 100 & 300 range for time saving
>
> Test: `filtersFlow.FiltersFlowTest`

---

### Flow 2 — Adding products to the cart (shipping fee allowed) 🛍️
**The story:** *A shopper has a budget of 9,000 EGP and wants to buy 4 things: a perfume, oil, a power bank, and a vase.*

What the program does:
1. Splits the total budget between the 4 items (each item gets its own price limit).
2. For each item: searches for it → filters the price to fit that item's share of the budget → sorts the results **from highest to lowest price** → adds the first product to the cart.
3. ✅ **Checks** that the number of items added matches the shopping list, and that the little number on the cart icon is correct.
4. Opens the **cart** and ✅ **checks**:
   - every item we added is really in the cart,
   - there is only **one** of each item (no duplicates),
   - the **subtotal** is the sum of the item prices,
   - **subtotal + shipping fee = total**,
   - the **total is within the budget** (not above it, and not more than 5% below it).

> Test: `addingProducts.AddingProductsWithShippingTest`

---

### Flow 3 — Adding products to the cart (free shipping only) 🚚
**The story:** *Same idea as Flow 2, but this time the shopper only wants products with **Free Delivery**: a blender, earbuds, makeup, and a charger.*

It works like Flow 2, with two differences:
- The program only picks products that carry the **"Free Delivery"** tag.
- ✅ It adds one more check: the **shipping fee must be 0**.

> Test: `addingProducts.AddingProductsWithoutShippingTest`

---

### Flow 4 — Searching for something that doesn't exist ❌
**The story:** *A shopper types nonsense (`###`) into the search bar.*

What the program does:
1. Opens the home page.
2. Types the invalid text in the search bar and presses search.
3. ✅ **Checks** that the website shows an error / "no results" message instead of products.

> Test: `invalidSearch.InvalidSearchTest`

---

## 🧰 Tools used

You don't need to understand these to run the project, but here is what each one does in plain words:

| Tool | What it does (in simple words) |
|---|---|
| **Java 21** | The programming language the tests are written in |
| **Maven** | The "project manager" – downloads everything the project needs and runs the tests with one command |
| **Selenium** | The "robot hands" that control the web browser (click, type, scroll) |
| **TestNG** | Organizes the tests and checks if results are right (pass / fail) |
| **Apache POI** | Reads the test data from an Excel file |
| **Log4j2** | Writes a diary (log file) of everything the program did |
| **Allure** | Turns the results into a beautiful, clickable report |
| **Git** | Downloads ("clones") the project to your computer |

---

## 📦 What you need to install first

Install these **once** on your computer. After installing each one, **close and re-open your terminal** so it is recognized.

> 💡 The commands in this guide are written for **Windows (PowerShell)**, which is what the demo video uses.

### 1. Git
Used to download the project.
- Download: <https://git-scm.com/downloads>
- Check it worked — open a terminal and run:
  ```powershell
  git --version
  ```

### 2. Java JDK 21 ☕ (required — must be version 21)
The project is built for **Java 21**. Older versions will not work.
- Download (any JDK 21 distribution is fine, for example Temurin): <https://adoptium.net/temurin/releases/?version=21>
- During installation, enable the options **"Set JAVA_HOME variable"** and **"Add to PATH"** if you see them.
- Check it worked:
  ```powershell
  java -version
  ```
  You should see a version that starts with `21`.

### 3. Apache Maven
Downloads the project's libraries and runs the tests.
- Download: <https://maven.apache.org/download.cgi> (then follow <https://maven.apache.org/install.html> to unzip it and add its `bin` folder to your PATH)
- Check it worked:
  ```powershell
  mvn -version
  ```
  This should show the Maven version **and** a Java version of 21.

### 4. Google Chrome 🌐
The tests use Chrome by default. If you don't have it: <https://www.google.com/chrome/>
>Note: edge and firefox are also supported and you can run on them with a slight change in command

> ✅ You do **not** need to download a "ChromeDriver" manually — Selenium downloads the right one automatically the first time you run (this needs an internet connection).

### 5. Allure Command-Line (version 2.x) 📊
Builds and opens the test report.
- **Easiest way with Scoop** (a Windows installer helper — see <https://scoop.sh>):
  ```powershell
  scoop install allure
  ```
- **Or with Node.js/npm** (<https://nodejs.org>):
  ```powershell
  npm install -g allure-commandline
  ```
- **Or manually:** download the `.zip` from <https://github.com/allure-framework/allure2/releases>, unzip it, and add its `bin` folder to your PATH.
- Check it worked:
  ```powershell
  allure --version
  ```

### ✅ Quick checklist

| Tool | Command to check | Expected |
|---|---|---|
| Git | `git --version` | any version |
| Java | `java -version` | starts with **21** |
| Maven | `mvn -version` | shows a version |
| Allure | `allure --version` | **2.x** |
| Chrome or any other supported browser | open the app | installed |

---

## Step 1 — Download (clone) the project

"Cloning" simply means downloading a copy of the project to your computer.

1. Open **PowerShell** (press the Windows key, type `PowerShell`, press Enter).
2. Go to the folder where you want to keep the project, for example your Documents:
   ```powershell
   cd $HOME\Documents
   ```
3. Clone the project:
   ```powershell
   git clone https://github.com/maro-z/Noon-Automation-Test.git
   ```
4. Enter the project folder:
   ```powershell
   cd Noon-Automation-Test
   ```

---

## Step 2 — Run the tests and see the report

### 📂 First: open the terminal in the project's root folder

The **root folder** is the folder that contains the files `pom.xml` and `testng.xml`. **All the commands below must be run from inside it**, otherwise they will not work.

Pick **any one** of these ways to open a terminal there:

- **If you just did Step 1** → you are already in the right place. ✅
- **Windows File Explorer:** open the `Noon-Automation-Test` folder → click on the address bar at the top → type `powershell` → press **Enter**.
- **Right-click method:** open the `Noon-Automation-Test` folder → right-click on an empty space → **Open in Terminal**.
- **VS Code / IntelliJ:** open the folder in the editor → open the built-in **Terminal** panel.

Not sure you are in the right place? Run this command — you should see `pom.xml` in the list:
```powershell
dir
```

### ▶️ Run these 4 commands, one after the other

> Copy and paste each command, press **Enter**, and **wait for it to finish** before running the next one.

#### 1️⃣ Allow scripts to run in this window
```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```
Windows blocks some scripts by default (including the one used by Allure). This command allows them **only in the current terminal window** and only until you close it — it does not change anything permanently on your computer. If asked for confirmation, type `Y` and press Enter.

> ⚠️ If you close the terminal and open a new one later, run this command again.

#### 2️⃣ Run the tests
```powershell
mvn clean install
```
- can be changed with mvn clean install -DBROWSERNAME=your_browser_name to run on edge or firefox
- `clean` → deletes the results of any previous run so you start fresh.
- `install` → downloads what's needed, builds the project, and **runs all the tests**.

🎬 **A Chrome window will open and start moving by itself — this is normal!** Don't click inside it and don't close it; just let it work. It opens and closes the browser for every test.

The first run takes longer because Maven downloads the libraries.

> ℹ️ At the end you may see `BUILD SUCCESS` (everything passed) or `BUILD FAILURE` (at least one check failed). **Both are fine for the next step** — the report will show you exactly what happened.

#### 3️⃣ Generate the report
```powershell
allure generate
```
This takes the raw results collected during the test run and builds the report from them (into a folder called `allure-report`).

#### 4️⃣ Open the report
```powershell
allure open
```
Your web browser opens with the full report. 🎉 Press `Ctrl + C` in the terminal when you want to stop the report server.

---

## 📊 Understanding the report

When the Allure report opens, you will see:

- 🟢 **Passed** – the check worked as expected.
- 🔴 **Failed** – something on the website did not match what the test expected (for example, a product was outside the price range).
- 🟡 **Broken** – something unexpected happened (for example, a button could not be found or the page didn't load).
- 📸 **Screenshots** – if a test fails, a picture of the browser at that moment is attached automatically.
- 📝 **Full Execution Logs** – a complete step-by-step diary of the run is attached to the report. Click on the last test to read what it did.

The same diary is also saved on your computer in `logs/application.log`.

---

## 📝 Changing the test data

The values used by the tests (which category, brand, price range, shopping list, budget, invalid search text) are stored in an Excel file, so you can change them **without touching any code**:

📁 `src/main/resources/data.xlsx`

| Sheet | What's inside | Current values |
|---|---|---|
| `firstScenario` | Category, brand, min price, max price, minimum rating | Electronics, samsung, 1000, 3000, 3.7 |
| `shoppingList` | Items to buy **with shipping**, items to buy **without shipping**, and the total budget | perfume / oil / power bank / vase, blender / earbuds / makeup / charger, budget 9000 |
| `invalidSearch` | Text to search for that should give no results | `###` |

Edit and **save** the file, then run the commands again. (Keep the same sheet names and the header row — the program relies on them.)

---

## 🗂️ Project structure

```
Noon-Automation-Test/
├── pom.xml                  → Maven settings: libraries, Java 21, how to run tests
├── testng.xml               → The list of tests to run and in what order
├── src/
│   ├── main/
│   │   ├── java/pages/      → "Page" classes: one per part of the website
│   │   │   ├── BasePage.java        (shared helpers, e.g. finding elements)
│   │   │   ├── home/HomePage.java
│   │   │   ├── mainHeader/MainHeader.java   (search bar, categories, cart icon)
│   │   │   ├── category/CategoryPage.java
│   │   │   ├── search/SearchPage.java       (filters, sorting, add to cart)
│   │   │   └── cart/CartPage.java
│   │   └── resources/
│   │       ├── data.xlsx            → Test data (edit me!)
│   │       └── log4j2.properties    → Logging settings
│   └── test/java/
│       ├── baseTest/BaseTest.java       → Opens/closes the browser, takes failure screenshots
│       ├── filtersFlow/                 → Flow 1
│       ├── addingProducts/              → Flows 2 and 3
│       ├── invalidSearch/               → Flow 4
│       ├── dataProvider/                → Feeds Excel data into the tests
│       ├── driverFactory/               → Chrome / Firefox / Edge setup
│       └── util/                        → Excel reader and screenshot helper
│
│  Created when you run the project (not stored in Git):
├── logs/                    → Text log of the run
├── allure-results/          → Raw results
└── allure-report/           → The final HTML report
```

> 💡 **Why "page" classes?** Each class represents one part of the website (like "the cart"). The tests just say *what* to do ("open the cart"), and the page classes know *how* ("click this button"). If the website changes, only the page classes need fixing. This is a common automation pattern called the **Page Object Model**.

---

## 🛠️ Troubleshooting

| Problem | What to do |
|---|---|
| `'mvn' is not recognized...` | Maven isn't installed or isn't in your PATH. Re-do the Maven install, then **close and re-open** the terminal. |
| `'java' is not recognized...` or wrong Java version | Install **JDK 21** and make sure `java -version` starts with `21`. Also check that `JAVA_HOME` points to the JDK 21 folder. |
| `invalid target release: 21` / `release version 21 not supported` | Maven is using an older Java. Run `mvn -version` and check the Java version it reports is 21. |
| `allure : File ...allure.ps1 cannot be loaded because running scripts is disabled` | You skipped command 1️⃣. Run `Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass` in that same window. |
| `'allure' is not recognized...` | Allure isn't installed or isn't in your PATH. Re-do the Allure install and re-open the terminal. |
| `allure open` shows an empty report or an old one | Make sure you ran `mvn clean install` **then** `allure generate` from the project root folder, in that order. |
| Chrome opens and then immediately closes / driver errors | Update Google Chrome to the latest version, and make sure you are connected to the internet (Selenium downloads the matching driver automatically). |
| `BUILD FAILURE` | At least one test failed. This is not a mistake in your setup — continue with `allure generate` and `allure open` and look at the failed test and its screenshot. |
| The tests fail on the website itself (element not found, etc.) | See [Good to know](#-good-to-know) below — the live website may have changed. |

---

## ℹ️ Good to know

- 🌍 **An internet connection is required.** The tests run against the real, live Noon Egypt website.
- 🔄 **Websites change.** Noon can change its design, products, prices, or offers at any time. Because these tests use the live site, a test can occasionally fail simply because something on the website changed — not because of a problem on your computer.
- 🙈 **Don't touch the browser while a test is running.** Clicking inside the automated window can interfere with the test.
- 🧹 **Every `mvn clean install` wipes the previous results** (logs, `allure-results`, `allure-report`, `target`) so each run starts clean.
- 🎓 This project is for learning and demonstration purposes and is not affiliated with Noon.

---

## 👩‍💻 Author

Created by **[maro-z](https://github.com/maro-z)**.

If you found this project useful, consider giving it a ⭐ on GitHub!
