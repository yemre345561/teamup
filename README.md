# TeamUp

Android prototype that connects students and investors so they can share ideas and find collaborators.

Built with [Özlem Demir](https://github.com/ozlem-demir) as a course project (ACM 431, package `com.acm431.teamup`). On the default branch, each of us has 9 commits. The UI is Kotlin with Jetpack Compose. Models and repositories are Kotlin, and the backend is Firebase.

## What the app includes

- Email and password sign-up and login. Sign-up starts with a Student or Investor choice. Students enter a university and department. Investors enter a company and position. Both forms can attach a PDF CV.
- One home feed of posts stored in Firebase Realtime Database. Card color follows the author's user type. `StudentHomeScreen` and `InvestorHomeScreen` are still in the project, but `MainActivity` does not navigate to them, and those screens only show hardcoded sample posts.
- Profiles for the signed-in user and for other users. A CV selected at sign-up is uploaded to Firebase Storage, and the download URL is saved on the user record.
- Creating posts (text, tags, photos, and files), saving posts, comments, likes, and search over post title, caption, and tags. Search keywords are stored per user.
- Notifications, settings, change password, terms, and log out.
- Bottom navigation (home, search, new post, notifications, profile) and a top bar on the main screens.

Navigation is declared in `MainActivity.kt`: login, user-type selection, student and investor sign-up, home, profile (own profile and `profile/{userId}`), search, share post, saved posts, notifications, settings, change password, terms, and log out.

## Tech stack

- Kotlin, Android (`minSdk` 26, `compileSdk` and `targetSdk` 34), Jetpack Compose, Navigation Compose
- Firebase Authentication (email and password), Realtime Database, and Storage. Firebase Analytics is also on the classpath.
- Coil for images
- Source layout: `data/model`, `data/repository`, `data/storage`, `viewmodel`, and `ui` screens

The app module compiles with Java 17. Android Gradle Plugin 8.7.3 and the Gradle 8.9 wrapper run on JDK 17 or 21.

## How to open the project

1. Clone the repository and open it in Android Studio.
2. Use JDK 17 or JDK 21. The app module's source and target compatibility are Java 17.
3. Add or confirm a valid `app/google-services.json` for a Firebase project you control (Android package `com.acm431.teamup`). Enable Authentication with the email/password provider, Realtime Database, and Storage.
4. Sync Gradle and run the `app` configuration.

This repository already contains an `app/google-services.json` from the course Firebase project `teamup-acm431`. For a new run, point the app at a project you control. Review the API key restrictions on the committed file, and do not commit new secret material.

Passwords are stored only in Firebase Authentication. Sign-up and change password do not write the password into Realtime Database. User records created before that change may still have a `password` field under `users/{uid}`. Delete those values in the Firebase console. Realtime Database rules for this prototype are not in the repository, so confirm that clients cannot read other users' private fields.

## Status

This is a course prototype, not a production app. It demonstrates Android and Firebase work, and it should not be deployed as-is. The checked-in Firebase config, any leftover password fields in existing database records, and the unwired sample home screens are the main reasons.
