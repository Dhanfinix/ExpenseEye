package com.dhandev.rekapin.utils

import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.CategoryItem

object CategoryUtil {
    fun getCategory(data: String):Pair<Int, Int>{
        val categoryMap = mapOf(
            TransactionCategory.Income.toString() to Pair(
                R.drawable.ic_payment,
                TransactionCategory.Income.ordinal
            ),
            TransactionCategory.Gift.toString() to Pair(
                R.drawable.ic_gift,
                TransactionCategory.Gift.ordinal
            ),
            TransactionCategory.Food.toString() to Pair(
                R.drawable.ic_food_drinks,
                TransactionCategory.Food.ordinal
            ),
            TransactionCategory.Transportation.toString() to Pair(
                R.drawable.ic_transportation,
                TransactionCategory.Transportation.ordinal
            ),
            TransactionCategory.Donation.toString() to Pair(
                R.drawable.ic_donate,
                TransactionCategory.Donation.ordinal
            ),
            TransactionCategory.Bills.toString() to Pair(
                R.drawable.ic_bill,
                TransactionCategory.Bills.ordinal
            ),
            TransactionCategory.Health.toString() to Pair(
                R.drawable.ic_health,
                TransactionCategory.Health.ordinal
            ),
            TransactionCategory.Exercise.toString() to Pair(
                R.drawable.ic_exercise,
                TransactionCategory.Exercise.ordinal
            ),
            TransactionCategory.Education.toString() to Pair(
                R.drawable.ic_education,
                TransactionCategory.Education.ordinal
            )
        )

        val (categoryImage, categoryName) = categoryMap[data] ?: Pair(
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