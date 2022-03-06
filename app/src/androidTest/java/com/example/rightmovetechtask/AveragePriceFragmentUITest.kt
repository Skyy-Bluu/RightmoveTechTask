package com.example.rightmovetechtask

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class AveragePriceFragmentUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun validate_AveragePriceFragment_heading_question() {
        onView(withText("What is the average property price?")).check(matches(isDisplayed()))
    }

    @Test
    fun validate_averge_price_displayed() {
        // this works for now but is probably better implemented with Idling Resources
        onView(withText("410280.77777777775")).check(matches(isDisplayed()))
    }
}