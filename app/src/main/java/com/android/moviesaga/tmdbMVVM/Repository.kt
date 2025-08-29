package com.example.moviesaga.tmdbMVVM

import android.util.Log
import com.android.moviesaga.tmdbapidataclass.People.CombinedCreditsData
import com.android.moviesaga.tmdbapidataclass.People.PersonDetailData
import com.android.moviesaga.tmdbapidataclass.People.PopularPeopleResponse
import com.example.moviesaga.Retrofit.tmdbApi.RetrofitBuilder
import com.example.moviesaga.Retrofit.email_check.EmailCheckApiServices
import com.example.moviesaga.Retrofit.email_check.EmailCheckRetrofitBuilder
import com.example.moviesaga.tmdbapidataclass.MailerooResponse
import com.example.moviesaga.tmdbapidataclass.Movie.MovieCreditsdata
import com.example.moviesaga.tmdbapidataclass.Movie.MovieDetailsData
import com.example.moviesaga.tmdbapidataclass.Movie.MovieLinks
import com.example.moviesaga.tmdbapidataclass.Movie.MovieReleaseDateAndCertification
import com.example.moviesaga.tmdbapidataclass.Movie.MovieVideosData
import com.example.moviesaga.tmdbapidataclass.Movie.PopularTopRatedTrendingOnTheAirMoviesData
import com.example.moviesaga.tmdbapidataclass.Series.PopularTopRatedTrendingOnTheAirSeriesData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesCreditsOneData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesDetailsOneData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesVideosOneData

class Repository {
    val maileroo_api_key = "YOUR_MAILRO_API_KEY_HERE" // website - https://app.maileroo.com/smtp-relay ( Use Bearer before key such as Bearer fn89h....)
    val tmdb_api_key = "YOUR_TMDB_API KEY_HERE" // website - https://developer.themoviedb.org/reference/intro/getting-started ( Use Bearer before key such as Bearer fn89h....)

    private val apiServices = RetrofitBuilder.getApi

    private val emailCheckApiServices = EmailCheckRetrofitBuilder.getApi

    suspend fun checkEmailAddress(email: String): MailerooResponse? {
        return try {
            val request = EmailCheckApiServices.EmailRequest(
                api_key = maileroo_api_key, // Replace with actual API key
                email_address = email
            )
            emailCheckApiServices.checkEmailAddress(request)
        } catch (e: Exception) {
            Log.e("EmailRepository", "Error verifying email", e)
            null
        }
    }


    suspend fun getPopularMovies(): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getPopularMovies(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully: movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching popular movies", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getPopularSeries(): PopularTopRatedTrendingOnTheAirSeriesData? {
        return try {
            val movies = apiServices.getPopularSeries(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching popular series", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getMovieDetailById(id: Int): MovieDetailsData? {
        return try {
            val movies = apiServices.getMovieDetailById(
                movieId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getMovieVideosById(id: Int): MovieVideosData? {
        return try {
            val movies = apiServices.getMovieVideosById(
                movieId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getMovieCreditsById(id: Int): MovieCreditsdata? {
        return try {
            val movies = apiServices.getMovieCreditsById(
                movieId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getMovieReleaseDatesAndCertificationsById(id: Int): MovieReleaseDateAndCertification? {
        return try {
            val movies = apiServices.getMovieReleaseDatesAndCertificationsById(
                movieId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getMovieLinksById(id: Int): MovieLinks? {
        return try {
            val movies = apiServices.getMovieLinksById(
                movieId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getTopRatedMovies(): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getTopRatedMovies(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getNowPlayingMovies(): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getNowPlayingMovies(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getUpcomingMovies(): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getUpcomingMovies(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getSeriesDetailsById(id: Int): SeriesDetailsOneData? {
        return try {
            val movies = apiServices.getSeriesDetailsById(
                seriesId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getSeriesVideosById(id: Int): SeriesVideosOneData? {
        return try {
            val movies = apiServices.getSeriesVideosById(
                seriesId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getSeriesCreditsById(id: Int): SeriesCreditsOneData? {
        return try {
            val movies = apiServices.getSeriesCreditsById(
                seriesId = id,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getTrendingMovies(timeWindow: String): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getTrendingMovies(
                timeWindow = timeWindow,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getTrendingSeries(timeWindow: String): PopularTopRatedTrendingOnTheAirSeriesData? {
        return try {
            val movies = apiServices.getTrendingSeries(
                timeWindow = timeWindow,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getOnTheAirSeries(): PopularTopRatedTrendingOnTheAirSeriesData? {
        return try {
            val movies = apiServices.getOnTheAirSeries(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getTopRatedSeries(): PopularTopRatedTrendingOnTheAirSeriesData? {
        return try {
            val movies = apiServices.getTopRatedSeries(
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getSearchedMovie(query: String): PopularTopRatedTrendingOnTheAirMoviesData? {
        return try {
            val movies = apiServices.getSearchedMovie(
                query = query,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getSearchedSeries(query: String): PopularTopRatedTrendingOnTheAirSeriesData? {
        return try {
            val movies = apiServices.getSearchedSeries(
                query = query,
                accept = "application/json",
                authorization = tmdb_api_key
            )
            Log.d("Repository", "Movies fetched successfully:  movies")
            movies // returing
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching Movie", e)
            null // Return null or handle it differently based on your use case
        }
    }

    suspend fun getPopularPeople(page: Int): PopularPeopleResponse {
        return try {
            apiServices.getPopularPeople(
                accept = "application/json",
                authorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMzNhMDQxN2VhYzJjNDI1Y2NlZjQzYzQwZDU0Y2I5YSIsIm5iZiI6MTc0MDI2OTI0MC40Mywic3ViIjoiNjdiYTY2YjhhOTZkYzE4OTc5YmViNzgzIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.7Nc9X5k1oCLph2gAsBRXifwnvpDyz7pKM_0tYmJGETQ",
                page = page
            )
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching popular people", e)
            PopularPeopleResponse(emptyList())
        }
    }



    suspend fun getPersonDetailsById(id: Int): PersonDetailData? {
        return try {
            apiServices.getPersonDetailsById(id, "application/json", tmdb_api_key)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching person details", e)
            null
        }
    }

    suspend fun getPersonCombinedCredits(id: Int): CombinedCreditsData? {
        return try {
            apiServices.getPersonCombinedCredits(id, "application/json", tmdb_api_key)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching credits", e)
            null
        }
    }

}
