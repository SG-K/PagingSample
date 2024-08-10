package com.example.pagingsample.features.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pagingsample.data.database.Post

@Composable
fun PostsScreen(
    modifier: Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    PostsList(
        state = state,
        modifier = modifier,
        loadNextItems = viewModel::loadNextItems
    )
}


@Composable
fun PostsList(
    modifier: Modifier,
    loadNextItems: () -> Unit,
    state: PostsScreenUiState
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            count = state.list.size,
            key = { state.list[it].id?: 0 },
            contentType = { "links" }
        ) { index: Int ->
            if (index >= state.list.size - 10 && state.endReached.not() && state.isLoading.not()) {
                loadNextItems()
            }
            val post: Post? = state.list[index]
            post?.let {
                PostView(it)
            }
        }
    }
}

@Composable
fun PostView(
    post: Post
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}