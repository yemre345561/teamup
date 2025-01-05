import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: PostRepository) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Post>>(emptyList())
    val searchResults: StateFlow<List<Post>> = _searchResults

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun performSearch(query: String, userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val results = repository.searchPosts(query)
            repository.saveSearchKeyword(userId, query)
            _searchResults.value = results
            loadSearchHistory(userId)
            _isLoading.value = false
        }
    }

    fun loadSearchHistory(userId: String) {
        viewModelScope.launch {
            _searchHistory.value = repository.getSearchHistory(userId)
        }
    }

    fun deleteSearchKeyword(userId: String, keyword: String) {
        viewModelScope.launch {
            repository.deleteSearchKeyword(userId, keyword)
            loadSearchHistory(userId)
        }
    }

}
