package com.example.final_project.presentation.screen.restoraunt_details

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.RestaurantMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RestaurantDetailsViewModel : ViewModel() {
    val menu = listOf(
        RestaurantMenu(1, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(2, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(3, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(4, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(5, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(6, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(7, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(8, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(9, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(10, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(11, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(12, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),

        )
    private val _restaurantMenuStateFlow = MutableStateFlow(menu)
    val restaurantMenuStateFlow = _restaurantMenuStateFlow.asStateFlow()
}