package ru.zakoulov.githubkotlintrends.data

interface Mapper<I, O> {
    fun map(input: I): O
}
