package com.example.moviesaga.tmdbapidataclass

data class MailerooResponse(
    val success: Boolean,
    val error_code: String?,
    val message: String?,
    val data: MailerooData?
)

data class MailerooData(
    val email: String,
    val format_valid: Boolean,
    val mx_found: Boolean,
    val disposable: Boolean,
    val role: Boolean,
    val free: Boolean,
    val domain_suggestion: String?
)
