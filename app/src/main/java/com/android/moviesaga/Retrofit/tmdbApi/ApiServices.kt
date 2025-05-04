package com.example.moviesapp.Retrofit


import com.android.moviesaga.tmdbapidataclass.People.CombinedCreditsData
import com.android.moviesaga.tmdbapidataclass.People.PersonDetailData
import com.android.moviesaga.tmdbapidataclass.People.PopularPeopleResponse
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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirSeriesData

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): MovieDetailsData

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideosById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): MovieVideosData

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCreditsById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): MovieCreditsdata

    @GET("movie/{movie_id}/release_dates")
    suspend fun getMovieReleaseDatesAndCertificationsById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): MovieReleaseDateAndCertification

    @GET("movie/{movie_id}/external_ids")
    suspend fun getMovieLinksById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): MovieLinks

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData


    @GET("tv/{series_id}")
    suspend fun getSeriesDetailsById(
        @Path("series_id") seriesId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): SeriesDetailsOneData

    @GET("tv/{series_id}/videos")
    suspend fun getSeriesVideosById(
        @Path("series_id") seriesId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): SeriesVideosOneData

    @GET("tv/{series_id}/credits")
    suspend fun getSeriesCreditsById(
        @Path("series_id") seriesId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): SeriesCreditsOneData

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingSeries(
        @Path("time_window") timeWindow: String,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirSeriesData

    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirSeriesData

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirSeriesData

    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query("query") query: String,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirMoviesData

    @GET("search/tv")
    suspend fun getSearchedSeries(
        @Query("query") query: String,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PopularTopRatedTrendingOnTheAirSeriesData

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMzNhMDQxN2VhYzJjNDI1Y2NlZjQzYzQwZDU0Y2I5YSIsIm5iZiI6MTc0MDI2OTI0MC40Mywic3ViIjoiNjdiYTY2YjhhOTZkYzE4OTc5YmViNzgzIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.7Nc9X5k1oCLph2gAsBRXifwnvpDyz7pKM_0tYmJGETQ",
        @Query("page") page: Int
    ): PopularPeopleResponse


    @GET("person/{person_id}")
    suspend fun getPersonDetailsById(
        @Path("person_id") personId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): PersonDetailData

    @GET("person/{person_id}/combined_credits")
    suspend fun getPersonCombinedCredits(
        @Path("person_id") personId: Int,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): CombinedCreditsData

}