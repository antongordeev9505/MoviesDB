# MoviesDB
Приложение предоставляет возможность получить доступ к информации, связанной с индустрией кино. Все данные получены по api https://www.themoviedb.org/.

Используемые принципы и технологии: DaggerHilt, Room, Retrofit, Paging, Coroutines, Flow, LiveData, Glide, Clean architecture, SOLID. Используемый архитектурный паттерн: MVVM.
## Содержание

1. Иллюстрация экранов приложения
2. Короткое видео иллюстрации работы приложения
3. Примеры кода


## 1. Экраны приложения:
#### Главный экран: 

Иллюстрация Watchlist и кастомных списков. Списки можно добавлять с уникальными именем, удалять. Фильмы из WathcList можно удалять по долгому нажатию. По спискам и фильмам можно переходить.

![alt-текст](https://github.com/antongordeevextra/MoviesDB/blob/master/app/src/main/res/drawable/photo_2021_12_16_13_44_39__2_.jpg "Главный экран")

#### Экран кастомного списка фильмов:

Название списка и список фильмов, которые можно удалять из списка долгим нажатием. По фильмам можно переходить.

![alt-текст](https://github.com/antongordeevextra/MoviesDB/blob/master/app/src/main/res/drawable/photo_2021_12_16_13_44_40__2_.jpg "Кастомный список фильмов")

#### Экран с популярными фильмами и возможностью поиска фильма по query:

Иллюстрация популярных фильмов по умолчанию и возможность поиска фильмов по названию

![alt-текст](https://github.com/antongordeevextra/MoviesDB/blob/master/app/src/main/res/drawable/photo_2021_12_16_13_44_40__4_.jpg "Поиск фильма")

#### Экран с возможностью фильтрации фильмов по различным параметрам (сложный поиск):

![alt-текст](https://github.com/antongordeevextra/MoviesDB/blob/master/app/src/main/res/drawable/photo_2021_12_16_13_44_41.jpg "Фильтр фильмов")

#### Экран с подробной информацией по фильму, с возможностью добавления в списки и удаления из них:

Добавление в списки производится по нажатию кнопки, добавление возможно на выбор пользователя в любой список. Цвет и текст кнопки иллюстрируют наличие данного фильма в списке.

![alt-текст](https://github.com/antongordeevextra/MoviesDB/blob/master/app/src/main/res/drawable/photo_2021_12_16_13_47_00.jpg "Подробная информация")


## 2.Ссылка на короткое видео по иллюстрации приложения:
https://youtu.be/ztr0xEsLp00

<a href="http://www.youtube.com/watch?feature=player_embedded&v=ztr0xEsLp00" target="_blank"><img src="http://img.youtube.com/vi/ztr0xEsLp00/0.jpg" 
alt="ALT-ТЕКСТ ИЗОБРАЖЕНИЯ" width="240" height="180" border="10" /></a>


## 3. Примеры кода

Часть кода реализации пагинации 
```kotlin
class SearchMovieByQueryPagingSource(private val query: String, private val api: MoviesDbApi) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.searchMoviesByQuery(position, query)
            val movies = response.results.map { it.toMovie() }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (error: IOException) {
            LoadResult.Error(error)

        } catch (error: HttpException) {
            LoadResult.Error(error)
        }
    }
}
```

Один из запросов по api
```kotlin
@GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("primary_release_year") releaseYear: Int?,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") minVoteCount: Int,
        @Query("with_genres") withGenre: String,
        @Query("vote_average.gte") voteAverage: Int
    ): ListMoviesDto
```

Часть кода нескольких запросов в базу данных
```kotlin
    @Query("SELECT EXISTS (SELECT 1 FROM movie WHERE id = :id)")
    fun existItem(id: Int): Flow<Boolean>

    @Query("DELETE FROM movie WHERE idList = :listId")
    suspend fun deleteMoviesWithList(listId: Int)
```

Иллюстрация работы с flow в RepositoryImpl
```kotlin
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        try {
            val movies = dao.getAllMovies().map {
                it.map { movieEntity ->
                    movieEntity.toMovie()
                }
            }.map {
                Resource.Success(it)
            }.flowOn(Dispatchers.Default)

            return movies

        } catch (error: Exception) {
            return flow {
                Resource.Error(error.toString())
            }
        }
    }
```

Иллюстрация одного из UseCase
```kotlin
class DiscoverMovies(private val repository: DiscoverMoviesRepository) {

    operator fun invoke(
        releaseYear: Int?,
        sortBy: String = "popularity.desc",
        minVoteCount: Int = 0,
        withGenre: String = "",
        voteAverage: Int = 0
    ): Flow<PagingData<Movie>> =
        repository.discoverMovies(releaseYear, sortBy, minVoteCount, withGenre, voteAverage)
}
```


Иллюстрация работы liveData в ViewModel
```kotlin
fun getMovieDetails(
        movieId: Int
    ): LiveData<Resource<MovieDetails>> = liveData {
        emitSource(
            getMovieDetailsUseCase.invoke(movieId).asLiveData()
        )
    }
```

Иллюстрация работы MediatorLiveData в ViewModel
```kotlin
init {
        _searchMoviesLiveData = Transformations.switchMap(
            _searchFieldTextLiveData) {
            getMovieByQuery(it)
        }

        moviesMediatorData.addSource(_popularMoviesLiveData) {
            moviesMediatorData.value = it
        }

        moviesMediatorData.addSource(_searchMoviesLiveData) {
            moviesMediatorData.value = it
        }
    }
```

Иллюстрация работы flow в ViewModel
```kotlin
val lists: StateFlow<Resource<List<CustomList>>> = flow {
        getAllListsUseCase.invoke().collect {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading
    )
```

Иллюстрация работы flow в UI
```kotlin
viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    when(it) {
                        is Resource.Success -> {
                        //...
```

Иллюстрация фрагментов кода разметки экрана подробностей фильма
```xml
<com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/coord_layout_background"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleTextAppearance="@style/CollapsingTitle"
            app:layout_scrollFlags="scroll|snap|exitUntilCollapsed"
            app:titleCollapseMode="scale">
    //...
    
<androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".presentation.detail_movie.DetailMovieFragment">

            <androidx.constraintlayout.widget.Guideline
                android:id="@+id/content_start"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:orientation="vertical"
                app:layout_constraintGuide_begin="@dimen/dimen_8dp" />

            <androidx.constraintlayout.widget.Guideline
                android:id="@+id/content_end"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:orientation="vertical"
                app:layout_constraintGuide_end="@dimen/dimen_8dp" />

            <androidx.constraintlayout.widget.Guideline
                android:id="@+id/content_top"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:orientation="horizontal"
                app:layout_constraintGuide_begin="@dimen/dimen_16dp" />

            <androidx.cardview.widget.CardView
                android:id="@+id/card_view_poster"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:cardCornerRadius="@dimen/dimen_12dp"
                app:cardElevation="@dimen/dimen_8dp"
                app:layout_constraintEnd_toStartOf="@id/recyclerview_genre"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="@id/content_start"
                app:layout_constraintTop_toTopOf="@id/content_top">

                <ImageView
                    android:id="@+id/poster"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:contentDescription="@string/poster_image"
                    android:src="@drawable/poster_image" />

            </androidx.cardview.widget.CardView>

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recyclerview_genre"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toEndOf="@id/card_view_poster"
                app:layout_constraintTop_toTopOf="@id/content_top"
                tools:listitem="@layout/genre_item" />
    //...
    
     <androidx.constraintlayout.widget.Barrier
                android:id="@+id/cast_section_bottom_barrier"
                android:layout_width="0dp"
                android:layout_height="0dp"
                app:barrierDirection="bottom"
                app:constraint_referenced_ids="cast_text, recyclerview_cast, director" />

            <View
                android:id="@+id/selector_line_cast"
                android:layout_width="@dimen/dimen_0dp"
                android:layout_height="1dp"
                android:layout_marginTop="@dimen/dimen_8dp"
                android:background="@drawable/selector_detail_screen"
                app:layout_constraintEnd_toEndOf="@id/content_end"
                app:layout_constraintStart_toStartOf="@id/content_start"
                app:layout_constraintTop_toBottomOf="@id/cast_section_bottom_barrier" />

            <androidx.constraintlayout.widget.Group
                android:id="@+id/recommendations_group"
                android:layout_width="@dimen/dimen_0dp"
                android:layout_height="@dimen/dimen_0dp"
                app:constraint_referenced_ids="recommendations_text, recyclerview_recommendations, selector_line_recommendations"/>

    //...

```






