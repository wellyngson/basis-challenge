// // Top-level build file where you can add configuration options common to all sub-projects/modules.
// plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false
//    alias(libs.plugins.realm.kotlin) apply false
// }

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}
