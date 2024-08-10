package com.example.pagingsample.features.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagingsample.data.database.Post
import com.example.pagingsample.domain.PostsRepository
import com.example.pagingsample.paging.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
): ViewModel() {

    private val _state = MutableStateFlow(PostsScreenUiState())
    val state = _state.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = _state.value.pageNo,
        onLoadUpdated = { status ->
            _state.update {
                it.copy(
                    isLoading = status
                )
            }
        },
        onRequest = { nextPage ->
            postsRepository.getPosts(
                limit = MAX_PAGE_SIZE,
                offset = _state.value.pageNo * MAX_PAGE_SIZE,
            )
        },
        getNextKey = {
            _state.value.pageNo + 1
        },
        onError = { throwable ->
            _state.update {
                it.copy(
                    errorMessage = throwable?.localizedMessage
                )
            }
        },
        onSuccess = { items, newKey ->
            _state.update {
                it.copy(
                    list = it.list + items,
                    pageNo = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


    init {
        loadNextItems()
        addDummyData()
    }

    private fun addDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts = postsRepository.getAllPosts()
            if (posts.isNotEmpty()) return@launch
            val list: ArrayList<Post> = java.util.ArrayList()
            for (i in 0..100) {
                list.add(
                    Post(
                        title = "Title $i",
                        description = "Description of the post: $i"
                    )
                )
            }
            postsRepository.savePosts(list)
        }
    }

    companion object {
        const val MAX_PAGE_SIZE = 40
    }
}

data class PostsScreenUiState(
    val pageNo: Int = 0,
    val list: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val endReached: Boolean = false,
)