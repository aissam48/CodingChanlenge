package com.stories.moststarredgithub.PackageModulData

data class ModulData(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)