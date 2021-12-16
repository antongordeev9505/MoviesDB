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






