# 📚 Quantum Book Store - Fawry Internship Challenge

This is a console-based Java application simulating an online bookstore, developed for the Fawry Internship challenge.

---

## 🔍 Description

The application allows managing an inventory of different types of books with the ability to add, remove, or buy a book. It simulates real-world bookstore behaviors such as shipping, email delivery, and inventory updates.

The system is **extensible**, making it easy to add more book types in the future without changing existing code logic.

---

## ✅ Features

### 🧾 Book Types

- **📦 Paper Book**
  - Has stock.
  - Can be shipped.
- **📥 EBook**
  - Has a file type (PDF, EPUB, MOBI).
  - Sent via email.
- **🖼️ Showcase Book**
  - Not purchasable (demo only).

### 📚 Book Management

- Add a book (ISBN, title, author, year, price, and type-specific data).
- Remove a book by ISBN.
- Remove outdated books older than a certain number of years.
- View all books in the inventory.

### 🛒 Purchase System

- Buy a book using ISBN, quantity, email, and address.
- EBooks are sent via `MailService`.
- Paper books are shipped via `ShippingService`.
- Quantity validation and stock reduction included.

### 🧠 Smart Behavior

- Books are saved/loaded to/from `books.json`.
- Type-specific fields like `stock` and `fileType` are serialized properly.
- The author is stored and displayed for each book.
- All system messages are prefixed with:

---

## 📂 File Structure

src/
├── data/
│   └── books.json
├── enums/
│   ├── BookType.java
│   └── FileType.java
├── extensions/
│   ├── MailService.java
│   └── ShippingService.java
├── model/
│   └── Book.java
├── repository/
│   └── BookRepository.java
├── repositoryContract/
│   └── IBookRepository.java
├── service/
│   ├── BookManager.java
│   ├── EBookService.java
│   ├── PaperBookService.java
│   └── ShowcaseBookService.java
├── serviceContract/
│   ├── IBookManager.java
│   └── IBookService.java
├── utils/
│   └── JsonHelper.java
├── BookStoreApp.java
└── Main.java


---

## 📌 Assumptions & Extras

- `author` field added to all book types.
- Books are loaded dynamically from JSON with type detection.
- Used a `BookManager` to abstract logic between repository and services.
- Used `delayedPrint()` for all UI messages for better UX.
- System is built on SOLID principles and open for extension.

---

## 🛠 Tech Stack

- Java 17+
- Gson (for JSON parsing)
- Console-based UI

---

## ▶️ How to Run

1. Clone the repo.
2. Ensure `books.json` exists in `src/data/`.
3. Run `Main.java` → which calls `BookStoreApp.run()`.

---

## 👨‍💻 Developed By

**Ibrahim Hassan**  
Fawry Internship Candidate
