package com.example.moviesaga

import MyAppTheme
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.moviesaga.screens.PremiumTabScreen
import com.example.moviesaga.screens.AllMovieVideosScreen
import com.example.moviesaga.screens.AllSeriesVideosScreen
import com.example.moviesaga.screens.FavouriteScreen
import com.example.moviesaga.screens.HomeScreen
import com.example.moviesaga.screens.LoginScreen
import com.example.moviesaga.screens.MovieDescScreen
import com.example.moviesaga.screens.MovieTabScreen
import com.example.moviesaga.screens.OnBoardingScreen
import com.example.moviesaga.screens.Profile
import com.example.moviesaga.screens.SearchingScreen
import com.example.moviesaga.screens.SeriesDescScreen
import com.example.moviesaga.screens.SeriesTabScreen
import com.example.moviesaga.screens.SettingTabScreen
import com.example.moviesaga.screens.SignUpDetailsScreen
import com.example.moviesaga.screens.SignUpScreen
import com.example.moviesaga.screens.WatchlistScreen
import com.example.moviesaga.tmdbMVVM.Repository
import com.example.moviesaga.tmdbMVVM.ViewModalFactory
import com.example.moviesaga.tmdbMVVM.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.listOf
import kotlin.getValue
import com.google.firebase.firestore.FirebaseFirestore
import android.Manifest
import com.android.moviesaga.screens.PersonDetailScreen
import com.android.moviesaga.screens.PopularCast

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val repository by lazy { Repository() }
                val viewModel: ViewModel by viewModels { ViewModalFactory(repository) }
                MyApp(viewModel)
            }
        }
    }
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(viewModel: ViewModel) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val databaseReference = FirebaseDatabase.getInstance().reference
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            GoogleSignInUtils.Companion.doGoogleSignIn(
                context = context,
                scope = scope,
                launcher = null,
                login = {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                }
            )
        }
    var startDes = "onboarding"
    if (auth.currentUser != null) {
        startDes = "homescreen"
    }
    NavHost(
        navController = navController,
        startDestination = startDes,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        composable("onboarding",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }
        ) {
            OnBoardingScreen(goToLogInScreen = {
                navController.navigate("loginscreen")
            },
                goToSignUpScreen = {
                    navController.navigate("signupscreen")
                },
                goToHomeScreen = {
                    navController.navigate("homescreen")
                })
        }

        composable("loginscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            LoginScreen(databaseReference,auth, openHomeScreen = {
                navController.navigate("homescreen") {
                    popUpTo(0)

                }
            }, scope, launcher)
        }

        composable("signupscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            SignUpScreen(databaseReference,auth,
                goToHomeScreen = {
                    navController.navigate("homescreen") {
                        popUpTo(0)
                    }
                },
                goToLoginScreen = {
                    navController.navigate("loginscreen")
                },
                goToSignUpDetails = {
                    navController.navigate("signupdetailsscreen")
                },
                scope,
                launcher
            )
        }

        composable("signupdetailsscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            SignUpDetailsScreen(viewModel,databaseReference,auth, signSuccessGoToLoginScreen = {
                navController.navigate("loginscreen")
            })
        }

        composable("homescreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }

        ) {
            HomeScreen(navController,viewModel = viewModel,
                goToSeriesDescScreen = { id ->
                    navController.navigate("seriesdescscreen/$id")

                },
                goToMovieDescScreen = { id ->
                    navController.navigate("moviedescscreen/$id")
                },
                goToHomeScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "homescreen") {
                        navController.navigate("homescreen")
                    }
                },
                goToMovieTabScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "movietabscreen") {
                        navController.navigate("movietabscreen")
                    }
                },
                goToSeriesTabScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "seriestabscreen") {
                        navController.navigate("seriestabscreen")
                    }
                },
                goToSearchScreen = {
                    navController.navigate("searchscreen")
                },
                goToProfileScreen = {
                    navController.navigate("profilescreen")
                },
                goToPopularCast = {
                    navController.navigate("popularcast")
                },
                goToSettingTabScreen = {
                    navController.navigate("settingtabscreen")
                })
        }

        composable("moviedescscreen/{id}",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() },
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val navBackStackEntry = it
            val id = navBackStackEntry.arguments?.getInt("id") ?: -1
            MovieDescScreen(databaseReference,auth,viewModel = viewModel,id= id, goToBackStack = {
                navController.popBackStack()
            },
                goToAllMovieVideosScreen = {
                    navController.navigate("allmovievideosscreen/$it")
                })
        }

        composable("seriesdescscreen/{id}",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() },
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val navBackStackEntry = it
            val id = navBackStackEntry.arguments?.getInt("id") ?: -1
            SeriesDescScreen(databaseReference,auth,viewModel = viewModel, id, goToBackStack = {
                navController.popBackStack()
            }, goToAllVideosScreen = {
                navController.navigate("allseriesvideosscreen/$it")
            })
        }

        composable("person_detail/{id}",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() },
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            PersonDetailScreen(
                personId = id,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable("allseriesvideosscreen/{id}",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() },
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val navBackStackEntry = it
            val id = navBackStackEntry.arguments?.getInt("id") ?: -1
            AllSeriesVideosScreen(viewModel, id, goBack = {
                navController.popBackStack()
            })
        }

        composable("allmovievideosscreen/{id}",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() },
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val navBackStackEntry = it
            val id = navBackStackEntry.arguments?.getInt("id") ?: -1
            AllMovieVideosScreen(viewModel, id, goBack = {
                navController.popBackStack()
            })
        }

        composable("movietabscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            MovieTabScreen(navController,viewModel, goToHomeScreen = {
                val currentDestination = navController.currentBackStackEntry?.destination?.route
                if (currentDestination != "homescreen") {
                    navController.navigate("homescreen")
                }
            },
                goToMovieTabScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "movietabscreen") {
                        navController.navigate("movietabscreen")
                    }
                },
                goToMovieDescScreen = { id ->
                    navController.navigate("moviedescscreen/$id")
                },
                goToSeriesTabScreen = {
                    navController.navigate("seriestabscreen")
                },
                goToSearchScreen = {
                    navController.navigate("searchscreen")
                },
                goToProfileScreen = {
                    navController.navigate("profilescreen")
                },
                goToPopularCast = {
                    navController.navigate("popularcast")
                },
                goToSettingTabScreen = {
                    navController.navigate("settingtabscreen")
                })
        }

        composable("seriestabscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            SeriesTabScreen(navController,viewModel, goToHomeScreen = {
                val currentDestination = navController.currentBackStackEntry?.destination?.route
                if (currentDestination != "homescreen") {
                    navController.navigate("homescreen")
                }
            },
                goToMovieTabScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "movietabscreen") {
                        navController.navigate("movietabscreen")
                    }
                },
                goToSeriesDescScreen = { id ->
                    navController.navigate("seriesdescscreen/$id")
                },
                goToSeriesTabScreen = {
                    val currentDestination =
                        navController.currentBackStackEntry?.destination?.route
                    if (currentDestination != "seriestabscreen") {
                        navController.navigate("seriestabscreen")
                    }
                },
                goToSearchScreen = {
                    navController.navigate("searchscreen")
                },
                goToProfileScreen = {
                    navController.navigate("profilescreen")
                },
                goToPopularCast = {
                    navController.navigate("popularcast")
                },
                goToSettingTabScreen = {
                    navController.navigate("settingtabscreen")
                })
        }

        composable(
            "popularcast",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }
        ) {
            PopularCast(
                navController = navController,
                viewModel = viewModel,
                goToHomeScreen = { navController.navigate("homescreen") },
                goToMovieTabScreen = { navController.navigate("movietabscreen") },
                goToSeriesTabScreen = { navController.navigate("seriestabscreen") },
                goToSearchScreen = { navController.navigate("searchscreen") },
                goToProfileScreen = { navController.navigate("profilescreen") },
                goToSettingTabScreen = { navController.navigate("settingtabscreen") },
                goToPopularCast = { navController.navigate("popularcast") }
            )
        }

        composable(
            "settingtabscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }
        ) {
            SettingTabScreen(
                navController = navController,
                goToHomeScreen = { navController.navigate("homescreen") },
                goToMovieTabScreen = { navController.navigate("movietabscreen") },
                goToSeriesTabScreen = { navController.navigate("seriestabscreen") },
                goToSearchScreen = { navController.navigate("searchscreen") },
                goToPopularCast = { navController.navigate("popularcast")},
                goToProfileScreen = { navController.navigate("profilescreen") },
            )
        }

        composable("searchscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }) {
            SearchingScreen(viewModel, goToMovieDescScreen = { id ->
                navController.navigate("moviedescscreen/$id")
            },
                goToSeriesDescScreen = { id ->
                    navController.navigate("seriesdescscreen/$id")
                },
                goToProfileScreen = {
                    navController.navigate("profilescreen")
                })
        }

        composable("profilescreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }){
            Profile(databaseReference,context,auth, goToOnBoardingScreen = {
                navController.navigate("onboarding"){
                    popUpTo(0)
                }
            },
                goToBackStack = {
                    navController.popBackStack()
                },
                goToFavouriteScreen = {
                    navController.navigate("favouritescreen")
                },
                goToWatchlistScreen = {
                    navController.navigate("watchlist")
                },
                goToPremiumTabScreen = {
                    navController.navigate("premiumtabscreen")
                })

        }


        composable("favouritescreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }){
            FavouriteScreen(viewModel,auth,databaseReference,goToBackStack={
                navController.popBackStack()
            },goToMovieDescScreen = { id ->
                navController.navigate("moviedescscreen/$id")
            },
                goToSeriesDescScreen = {id->
                    navController.navigate("seriesdescscreen/$id")
                })
        }

        composable("watchlist", enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }){
            WatchlistScreen(auth,databaseReference,goToBackStack={
                navController.popBackStack()
            },goToMovieDescScreen = { id ->
                navController.navigate("moviedescscreen/$id")
            },
                goToSeriesDescScreen = {id->
                    navController.navigate("seriesdescscreen/$id")
                })
        }
        composable(
            "premiumtabscreen",
            enterTransition = { fasterEnterTransition() },
            exitTransition = { fasterExitTransition() },
            popEnterTransition = { fasterEnterTransition() },
            popExitTransition = { fasterExitTransition() }
        ) {
            PremiumTabScreen(
                navController = navController,
                goBack = { navController.popBackStack()
                }
            )
        }


    }
}
fun AnimatedContentTransitionScope<*>.fasterEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(200)) // Adjust duration (200ms)
}
fun AnimatedContentTransitionScope<*>.fasterExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(200)) // Adjust duration (200ms)
}
}