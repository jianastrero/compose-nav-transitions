package dev.jianastrero.compose_nav_transition.element

data class TransitionElements(
    val currentElements: List<Element> = emptyList(),
    val previousElements: List<Element> = emptyList()
)

val EmptyTransitionElements = TransitionElements()
