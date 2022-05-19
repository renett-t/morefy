package ru.itis.morefy.statistics.di

import javax.inject.Scope

@Scope
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class StatisticsScope
