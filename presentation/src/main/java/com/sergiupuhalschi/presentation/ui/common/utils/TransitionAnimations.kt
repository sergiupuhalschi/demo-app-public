package com.sergiupuhalschi.presentation.ui.common.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry

val springSpec by lazy { spring<IntOffset>(dampingRatio = Spring.DampingRatioNoBouncy) }

fun slideInFromRightToLeft(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
}

fun slideInFromLeftToRight(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
}

fun slideOutFromRightToLeft(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
}

fun slideOutFromLeftToRight(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
}

fun slideInFromBottomToTop(): EnterTransition {
    return slideInVertically(initialOffsetY = { 3000 }, animationSpec = springSpec)
}

fun slideInFromTopToBottom(): EnterTransition {
    return slideInVertically(initialOffsetY = { -3000 }, animationSpec = springSpec)
}

fun slideOutFromTopToBottom(): ExitTransition {
    return slideOutVertically(targetOffsetY = { 3000 }, animationSpec = springSpec)
}

fun slideOutFromBottomToTop(): ExitTransition {
    return slideOutVertically(targetOffsetY = { 3000 }, animationSpec = springSpec)
}

val screenEnterHorizontalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = { slideInFromRightToLeft() }

val screenExitHorizontalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = { slideOutFromRightToLeft() }

val screenPopEnterHorizontalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = { slideInFromLeftToRight() }

val screenPopExitHorizontalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = { slideOutFromLeftToRight() }

val screenEnterVerticalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = { slideInFromBottomToTop() }

val screenExitVerticalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = { slideOutFromTopToBottom() }

val screenPopEnterVerticalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = { slideInFromTopToBottom() }

val screenPopExitVerticalTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = { slideOutFromBottomToTop() }