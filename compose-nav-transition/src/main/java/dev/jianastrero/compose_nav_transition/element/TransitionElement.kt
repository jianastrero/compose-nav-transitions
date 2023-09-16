package dev.jianastrero.compose_nav_transition.element

data class TransitionElement(
    val currentElements: List<Element> = emptyList(),
    val previousElements: List<Element> = emptyList()
)

val EmptyTransitionElement = TransitionElement()
