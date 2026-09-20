package com.example.ui.navigation

sealed class Screen(val route: String, val title: String, val iconName: String) {
    data object Village : Screen("village", "Village", "holiday_village")
    data object Cards : Screen("cards", "Cards", "style")
    data object CoinDash : Screen("coindash", "PLAY", "sports_esports")
    data object Victory : Screen("victory", "Victory", "emoji_events")
    data object Heroes : Screen("heroes", "Heroes", "shield_person")
    data object Missions : Screen("missions", "Missions", "task_alt")
    data object Bazaar : Screen("bazaar", "Bazaar", "storefront")
    data object Settings : Screen("settings", "Settings", "settings")
}
