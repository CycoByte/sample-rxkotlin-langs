package com.example.simplerxapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplerxapp.observables.chapter.ChapterObservable
import com.example.simplerxapp.observables.subject.SubjectObservable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel: ViewModel() {

    private val subjectObservable = SubjectObservable()
    private val chapterObservable = ChapterObservable()

    private val _subjectsUiRemoteStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val subjectsRemoteUiStateSF: StateFlow<UIState<*>>
        get() = _subjectsUiRemoteStateMSF

    private val _subjectsUiStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val subjectsUiStateSF: StateFlow<UIState<*>>
        get() = _subjectsUiStateMSF

    private val _chaptersRemoteUiStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val chaptersRemoteUiStateSF: StateFlow<UIState<*>>
        get() = _chaptersRemoteUiStateMSF

    private val _chaptersUiStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val chaptersUiStateSF: StateFlow<UIState<*>>
        get() = _chaptersUiStateMSF

    private var lastShownError: Throwable? = null

    fun loadAllSubjects() {
        viewModelScope.launch(Dispatchers.IO) {
            _subjectsUiStateMSF.value = UIState.Loading
            subjectObservable.fetchAllLocalObservable().collect {
                _subjectsUiStateMSF.value = UIState.Success(it)
            }
        }
        updateLocalSubjectsFromRemote()
    }

    private fun updateLocalSubjectsFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            _subjectsUiRemoteStateMSF.value = UIState.Loading
            subjectObservable.updateLocalFromRemote().let {
                if (it.isSuccess) {
                    it.getOrNull()?.forEach { subj ->
                        updateLocalChaptersFromRemote(subj.id)
                    }
                    _subjectsUiRemoteStateMSF.value = UIState.SuccessNoData
                } else {
                    if (lastShownError !is UnknownHostException) {
                        lastShownError = it.exceptionOrNull()
                        _subjectsUiRemoteStateMSF.value = UIState.Error(
                            it.exceptionOrNull()?.localizedMessage
                                ?: "Failed to retrieve subject data"
                        )
                    } else {
                        _subjectsUiRemoteStateMSF.value = UIState.Idle
                    }
                }
            }
        }
    }

    fun loadChaptersOfSubject(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _chaptersUiStateMSF.value = UIState.Loading
            chapterObservable.observableLocalChapters(id).collect {
                _chaptersUiStateMSF.value = UIState.Success(it)
            }
        }
    }

    private fun updateLocalChaptersFromRemote(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _chaptersRemoteUiStateMSF.value = UIState.Loading
            chapterObservable.updateLocalFromRemote(id).let {
                if (it.isSuccess) {
                    _chaptersRemoteUiStateMSF.value = UIState.SuccessNoData
                } else {
                    _chaptersRemoteUiStateMSF.value = UIState.Error(
                        it.exceptionOrNull()?.localizedMessage ?: "Failed to retrieve chapter data"
                    )
                }
            }
        }
    }
}