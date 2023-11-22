package com.dhandev.rekapin.utils

import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.CategoryItem
import com.dhandev.rekapin.data.model.TransactionItemModel

object CategoryUtil {
    fun getCategory(data: TransactionItemModel):Pair<Int, Int>{
        val categoryMap = mapOf(
            TransactionCategory.Income.toString() to Pair(
                R.drawable.ic_payment,
                R.string.category_payment
            ),
            TransactionCategory.Gift.toString() to Pair(R.drawable.ic_gift, R.string.category_gift),
            TransactionCategory.Food.toString() to Pair(
                R.drawable.ic_food_drinks,
                R.string.category_food
            ),
            TransactionCategory.Transportation.toString() to Pair(
                R.drawable.ic_transportation,
                R.string.category_transportation
            ),
            TransactionCategory.Donation.toString() to Pair(
                R.drawable.ic_donate,
                R.string.category_donation
            ),
            TransactionCategory.Bills.toString() to Pair(R.drawable.ic_bill, R.string.category_bill),
            TransactionCategory.Health.toString() to Pair(
                R.drawable.ic_health,
                R.string.category_health
            ),
            TransactionCategory.Exercise.toString() to Pair(
                R.drawable.ic_exercise,
                R.string.category_exercise
            ),
            TransactionCategory.Education.toString() to Pair(
                R.drawable.ic_education,
                R.string.category_education
            )
        )

        val (categoryImage, categoryName) = categoryMap[data.category] ?: Pair(
            R.drawable.ic_expenseeye,
            R.string.app_name
        )
        return Pair(categoryImage, categoryName)
    }

    fun findCategoryItemByName(name: String, isOutcome: Boolean): CategoryItem? {
        if (isOutcome){
            for (categoryItem in Constants.categoryOutcomeName) {
                if (categoryItem.name == name) {
                    return categoryItem
                }
            }
        } else {
            for (categoryItem in Constants.categoryIncomeName) {
                if (categoryItem.name == name) {
                    return categoryItem
                }
            }
        }
        return null
    }
}