package com.ateeb.smartexpensetrackerzobaze.utils

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import java.util.Calendar

object ExpenseConstants {
    var TOTAL_ITEMS = "total_items"
    var TOTAL_AMOUNT = "total_amount"

    fun getMockExpenseData(): List<Expense> {
        val calendar = Calendar.getInstance()

        return listOf(
            // Today
            Expense(
                id = 1,
                title = "Team Lunch",
                amount = 850.0,
                notes = "Monthly team lunch at restaurant",
                category = "Food",
                imageUri = null,
                date = calendar.time
            ),
            Expense(
                id = 2,
                title = "Office Supplies",
                amount = 1200.0,
                notes = "Stationary and printer cartridges",
                category = "Utility",
                imageUri = null,
                date = calendar.time
            ),

            // Yesterday (1 day ago)
            Expense(
                id = 3,
                title = "Software Developer Salary",
                amount = 45000.0,
                notes = "Monthly salary payment",
                category = "Staff",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -1)
                }.time
            ),
            Expense(
                id = 4,
                title = "Client Meeting Coffee",
                amount = 320.0,
                notes = "Coffee with potential client",
                category = "Food",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -1)
                }.time
            ),

            // 2 days ago
            Expense(
                id = 5,
                title = "Uber to Airport",
                amount = 480.0,
                notes = "Business trip transportation",
                category = "Travel",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -2)
                }.time
            ),
            Expense(
                id = 6,
                title = "Internet Bill",
                amount = 2500.0,
                notes = "Monthly office internet",
                category = "Utility",
                imageUri = "file://path/to/bill.jpg",
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -2)
                }.time
            ),

            // 3 days ago
            Expense(
                id = 7,
                title = "Designer Freelance",
                amount = 15000.0,
                notes = "UI/UX design for mobile app",
                category = "Staff",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -3)
                }.time
            ),
            Expense(
                id = 8,
                title = "Business Lunch",
                amount = 1200.0,
                notes = "Lunch with investor",
                category = "Food",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -3)
                }.time
            ),
            Expense(
                id = 9,
                title = "Flight Tickets",
                amount = 8500.0,
                notes = "Round trip to Mumbai for conference",
                category = "Travel",
                imageUri = "file://path/to/ticket.pdf",
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -3)
                }.time
            ),

            // 4 days ago
            Expense(
                id = 10,
                title = "Office Cleaning",
                amount = 800.0,
                notes = "Weekly office cleaning service",
                category = "Utility",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -4)
                }.time
            ),
            Expense(
                id = 11,
                title = "Grocery Shopping",
                amount = 650.0,
                notes = "Snacks and beverages for office",
                category = "Food",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -4)
                }.time
            ),

            // 5 days ago
            Expense(
                id = 12,
                title = "Marketing Consultant",
                amount = 12000.0,
                notes = "Social media marketing strategy",
                category = "Staff",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -5)
                }.time
            ),
            Expense(
                id = 13,
                title = "Local Transportation",
                amount = 150.0,
                notes = "Auto rickshaw for bank work",
                category = "Travel",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -5)
                }.time
            ),
            Expense(
                id = 14,
                title = "Electricity Bill",
                amount = 3500.0,
                notes = "Monthly office electricity",
                category = "Utility",
                imageUri = "file://path/to/electricity_bill.jpg",
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -5)
                }.time
            ),

            // 6 days ago
            Expense(
                id = 15,
                title = "Pizza for Team",
                amount = 950.0,
                notes = "Late night work session food",
                category = "Food",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -6)
                }.time
            ),
            Expense(
                id = 16,
                title = "Hotel Stay",
                amount = 4500.0,
                notes = "One night stay for business meeting",
                category = "Travel",
                imageUri = "file://path/to/hotel_receipt.jpg",
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -6)
                }.time
            ),

            // 7 days ago
            Expense(
                id = 17,
                title = "Intern Stipend",
                amount = 8000.0,
                notes = "Monthly stipend for summer intern",
                category = "Staff",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -7)
                }.time
            ),
            Expense(
                id = 18,
                title = "Office Supplies",
                amount = 750.0,
                notes = "Notebooks, pens, and sticky notes",
                category = "Utility",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -7)
                }.time
            ),
            Expense(
                id = 19,
                title = "Breakfast Meeting",
                amount = 420.0,
                notes = "Breakfast with potential partner",
                category = "Food",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -7)
                }.time
            ),
            Expense(
                id = 20,
                title = "Taxi to Client Office",
                amount = 280.0,
                notes = "Transportation for client presentation",
                category = "Travel",
                imageUri = null,
                date = Calendar.getInstance().apply {
                    time = calendar.time
                    add(Calendar.DAY_OF_YEAR, -7)
                }.time
            )
        )
    }


}