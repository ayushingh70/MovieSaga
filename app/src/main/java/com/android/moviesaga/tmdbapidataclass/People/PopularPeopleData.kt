package com.android.moviesaga.tmdbapidataclass.People

data class PopularPeopleResponse(
    val results: List<PopularPerson>
)

data class PopularPerson(
    val id: Int,
    val name: String,
    val profile_path: String?,
    val known_for_department: String,
    val popularity: Double,
    val known_for: List<KnownFor>
)

data class KnownFor(
    val id: Int,
    val title: String?,  // Movie
    val name: String?,   // Series
    val media_type: String,
    val poster_path: String?,
    val overview: String
)

data class PersonDetailData(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String?,
    val place_of_birth: String?,
    val profile_path: String?,
    val known_for_department: String,
    val gender: Int
)

data class CombinedCreditsData(
    val cast: List<PersonCredit>,
    val crew: List<PersonCredit>
)

data class PersonCredit(
    val id: Int,
    val media_type: String,
    val title: String?,
    val name: String?,
    val poster_path: String?,
    val character: String?,
    val job: String?
)
