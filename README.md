# Password Management Tool Based on Kotlin/Compose Multiplatform

[中文文档](./README_zh.md)

## Background

As a developer, I frequently need to log in to numerous websites across various devices and operating systems. Each website has different password requirements, and using the same password for all sites poses security risks, while using different passwords makes them hard to remember.

## Solution

The solution is to remember only one private key. By combining the website URL and login account (email or phone number), a password is generated that meets the requirements of most websites (including lowercase letters, uppercase letters, numbers, and special characters). For security reasons, the password is not stored directly; instead, its SHA-256 encrypted value is saved. To view the plaintext password, the private key must be entered for verification, and upon successful verification, the plaintext password is dynamically calculated. The tool also supports copying the password.

Therefore, I decided to develop a cross-platform password tool using Kotlin/Compose Multiplatform.

> This project is more of a hands-on exercise and supports running on Android, iOS, and Desktop.

<div align="center">
  <img src="./img/HomePage.png" width="200" style="margin-right: 20px;"/>
  <img src="./img/DetailPage.png" width="200"/>
</div>


---

## Features and Interaction

- **Home Page**: Displays added password records and supports fuzzy search via a bottom search bar.
- **Add Record**: Click the plus button at the bottom to navigate to the add password page.
- **View Password**: Click on a record item to preview relevant information and enter the private key to display the plaintext password.
- **Edit Record**: Long-press a record item to navigate to the edit page.
- **Delete Record**: Swipe right on a record item to reveal the delete button.

---

## Tech Stack

- **Coil**: Image Loading - [Coil Docs](https://coil-kt.github.io/coil/compose/)
- **Ktor**: HTTP Client - [Ktor Docs](https://ktor.io/docs/client-create-multiplatform-application.html#android-activity)
- **Koin**: Dependency Injection - [Koin Docs](https://insert-koin.io/docs/quickstart/cmp/)
- **Room**: Database - [Room Docs](https://developer.android.com/kotlin/multiplatform/room?hl=zh-cn)
- **Paging**: Pagination Library - [Paging Repo](https://github.com/cashapp/multiplatform-paging)
- **Hash**: Hash Encoding Library - [Hash Repo](https://github.com/KotlinCrypto/hash/)
- **Serialization**: Serialization - [Serialization Repo](https://github.com/Kotlin/kotlinx.serialization)
- **Navigation**: Navigation - [Navigation Docs](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)

---

## Planned Features

- **Core Features**  
  - Add / Edit / Search / Delete Website Records ✔️  
  - Password Generation Algorithm Based on Private Key ✔️  
  - Automatically Copy Password to Clipboard ✔️  
  - Display Website Icon Based on URL ✔️ (Can be optimized)

- **Upcoming Features**  
  - QR Code Scanning for Adding Websites on Android/iOS  
  - Multi-device Password Synchronization (Backend support required)  
  - Code Optimization

---

## Known Issues

- **iOS Compilation Issue**: Due to a [KSP issue](https://issuetracker.google.com/issues/398414973), iOS compilation consistently fails. Expected to be resolved by 2025.3.5.

---

The restructured document is clearer and easier to read and maintain.