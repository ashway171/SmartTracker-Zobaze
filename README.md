# SmartTracker

SmartTracker is a lightweight app designed to support small business owners in digitizing their daily expense tracking. By replacing lost notes on paper or informal WhatsApp messages, SmartTracker helps you gain clear visibility into cash flow and spending patterns.

---

## Table of Contents

- [Key Components](#key-components)  
- [Screenshots](#screenshots)  
- [Salient Technicalities](#salient-technicalities)  

---

## Key Components

- **ExpenseEntryActivity**  
  Quickly add expenses with essential details:
  - **Title:** Describe the expense  
  - **Amount (₹):** Enter the cost  
  - **Category:** Choose from predefined categories — Staff, Travel, Food, Utility  
  - **Optional Notes:** Add extra info (up to 100 characters)  
  - **Optional Receipt Image:** Upload a photo of your receipt  
  - Real-time display of **Total Spent Today** to keep track as you add entries  
  - Submit button confirms addition and displays a toast message  

- **ExpenseDetailsFragment**  
  View and manage your expenses with ease:  
  - Default view shows today's expenses  
  - Filter by previous dates using a calendar or custom filter  
  - Group expenses by category for better insight  
  - Displays total number of expenses and total amount spent  
  - Empty state UI for days with no expenses  

- **ExpenseReportFragment**  
  Get a quick snapshot of your spending trends over the past week:  
  - Daily total expenses (visualised via bar chart)  
  - Category-wise spending summary    

---

## Screenshots

*Relevant screenshots showing each screen — ExpenseEntryActivity, ExpenseDetailsFragment, ExpenseReportFragment.*

<img width="370" height="813" alt="ExpenseEntryActivity" src="https://github.com/user-attachments/assets/225a659c-65e7-45fa-aa0b-7a464f881884" />
<img width="362" height="816" alt="ExpenseDetailsFragment" src="https://github.com/user-attachments/assets/a54a52ef-d2ad-4168-ae7c-6361c2d477db" />
<img width="366" height="816" alt="ExpenseReportFragment" src="https://github.com/user-attachments/assets/cb058173-5c6a-4999-b0f2-3e6447e8a601" />

---

## Salient Technicalities

1. **ViewModels and Domain Layer** are free from `android.jar` imports, ensuring they are unit-test compliant and easily testable without Android framework dependencies.

2. The codebase has been developed with **SOLID principles** in mind, promoting maintainability, extensibility, and clean architecture.

3. **Redundant Room queries have been avoided.**  
   For instance, instead of querying today's expenses again in ExpenseEntryActivity and then collecting the StateFlow, we keep track of today's total expenses within ExpenseDetailsFragment since it performs the initial query for today. The ExpenseEntryActivity then updates the total locally, optimizing database access and improving app performance.

4. Avid use of Dagger2 for dependency management and implementation of the Dependency Inversion principle.

5. Extensive validation checks when entering an expense.

6. 

---
