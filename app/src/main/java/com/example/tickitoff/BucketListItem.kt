package com.example.tickitoff

data class BucketListItem(
    var id: Int,
    var title: String,
    var description: String,
    var doneByYear: Int, // User should set a year they want the goal to be done by
    var completed : Boolean, // Boolean that tracks whether it is completed or not.
)

// For development
fun getTestBucketListItems(): List<BucketListItem> {
    return listOf<BucketListItem>(
        BucketListItem(
            1,
            "Go to Paris",
            "I need to save up at least 5000 dollars and find someone to travel with",
            2025,
            completed = false
        ),
        BucketListItem(
            2,
            "Learn to play the guitar",
            "Need to buy a guitar and find a good online course or local teacher",
            2024,
            completed = false
        ),
        BucketListItem(
            3,
            "Run a marathon",
            "Start a training regimen and sign up for a local marathon event",
            2026,
            completed = false
        ),
        BucketListItem(
            4,
            "Write a novel",
            "Develop plot and characters, set aside time each day to write",
            2027,
            completed = false
        ),
        BucketListItem(
            5,
            "Scuba dive in the Great Barrier Reef",
            "Get scuba certified and plan a trip to Australia",
            2028,
            completed = false
        ),
        BucketListItem(
            6,
            "Start a small business",
            "Develop a business plan, secure funding, and register the company",
            2026,
            completed = false
        ),
        BucketListItem(
            7,
            "Climb Mount Kilimanjaro",
            "Train for high-altitude hiking and book a guided expedition",
            2027,
            completed = true
        ),
        BucketListItem(
            8,
            "Learn to speak fluent Spanish",
            "Enroll in language classes and plan an immersion trip to Spain",
            2025,
            completed = true
        ),
        BucketListItem(
            9,
            "Complete a cross-country road trip",
            "Plan route, save for expenses, and prepare vehicle for long journey",
            2024,
            completed = true
        ),
    )
}