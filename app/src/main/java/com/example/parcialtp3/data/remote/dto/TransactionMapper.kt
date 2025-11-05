package com.example.parcialtp3.data.remote.dto

import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Locale

fun TransactionDto.toDomain(): Transaction {
    val (date, time) = parseDateTimeString(this.date)

    return Transaction(
        id = this.transaction_id.hashCode().toLong(),
        title = this.description,
        category = mapSubtypeToCategory(this.subtype),
        amount = if (this.type.equals("expense", ignoreCase = true)) -this.amount else this.amount,
        date = date,
        time = time,
        type = mapStringToTransactionType(this.type),
        iconType = mapSubtypeToIconType(this.subtype)
    )
}

private fun parseDateTimeString(dateString: String): Pair<String, String> {
    val possibleFormats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd",
        "dd/MM/yyyy",
        "MM/dd/yyyy"
    )

    for (format in possibleFormats) {
        try {
            val inputFormat = SimpleDateFormat(format, Locale.US)
            inputFormat.isLenient = false
            val parsedDate = inputFormat.parse(dateString)

            if (parsedDate != null) {
                val dateFormat = SimpleDateFormat("MMMM dd", Locale.US)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.US)

                val formattedDate = dateFormat.format(parsedDate)
                val formattedTime = timeFormat.format(parsedDate)

                return Pair(formattedDate, formattedTime)
            }
        } catch (e: Exception) {
            continue
        }
    }

    println("⚠️ Failed to parse date: '$dateString'")
    return Pair(dateString, "00:00")
}

private fun mapStringToTransactionType(type: String): TransactionType {
    return when (type.lowercase()) {
        "income" -> TransactionType.INCOME
        "expense" -> TransactionType.EXPENSE
        else -> TransactionType.EXPENSE
    }
}

private fun mapSubtypeToCategory(subtype: String): String {
    return when (subtype.lowercase()) {
        "salary" -> "Monthly"
        "groceries" -> "Pantry"
        "rent" -> "Rent"
        "transport" -> "Fuel"
        "food" -> "Dining"
        "entertainment" -> "Entertainment"
        "bills" -> "Utilities"
        "shopping" -> "Shopping"
        "transfer" -> "Transfer"
        else -> "Other"
    }
}

private fun mapSubtypeToIconType(subtype: String): TransactionIconType {
    return when (subtype.lowercase()) {
        "salary" -> TransactionIconType.SALARY
        "groceries" -> TransactionIconType.GROCERIES
        "rent" -> TransactionIconType.RENT
        "transport" -> TransactionIconType.TRANSPORT
        "food" -> TransactionIconType.FOOD
        "entertainment" -> TransactionIconType.ENTERTAINMENT
        "bills" -> TransactionIconType.BILLS
        "shopping" -> TransactionIconType.SHOPPING
        "transfer" -> TransactionIconType.TRANSFER
        else -> TransactionIconType.OTHER
    }
}
