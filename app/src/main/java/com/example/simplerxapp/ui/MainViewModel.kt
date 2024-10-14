package com.example.simplerxapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.simplerxapp.observables.chapter.ChapterObservable
import com.example.simplerxapp.observables.subject.SubjectObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val subjectObservable = SubjectObservable()
    private val chapterObservable = ChapterObservable()

    private val _subjectsUiStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val subjectsUiStateSF: StateFlow<UIState<*>>
        get() = _subjectsUiStateMSF

    private val _chaptersUiStateMSF = MutableStateFlow<UIState<*>>(UIState.Idle)
    val chaptersUiStateSF: StateFlow<UIState<*>>
        get() = _chaptersUiStateMSF

    private var lastShownError: Throwable? = null

    fun loadAllSubjects() {
        _subjectsUiStateMSF.value = UIState.Loading
        compositeDisposable.add(
            subjectObservable.fetchAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { subjects ->
                    subjects.forEach {
                        compositeDisposable.add(
                            chapterObservable.fetchForSubject(it.id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                        )
                    }
                }
                .doOnError {
                    compositeDisposable.add(
                        subjectObservable.fetchAllLocal()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .delay(100, TimeUnit.MILLISECONDS)
                            .subscribe( //potential race condition with setting ui state with the below subscribe
                                {
                                    _subjectsUiStateMSF.value = UIState.Success(it)
                                },
                                { error ->
                                    _subjectsUiStateMSF.value = UIState.Error(error.localizedMessage ?: "Error!")
                                }
                            )
                    )
                }
                .subscribe(
                    { data ->
                        lastShownError = null
                        _subjectsUiStateMSF.value = UIState.Success(data)
                    },
                    { error ->
                        if (lastShownError !is UnknownHostException) {
                            lastShownError = error
                            _subjectsUiStateMSF.value =
                                UIState.Error(error.cause?.localizedMessage ?: "Error!")
                            Log.e("TAG", "Got Error ${error.stackTraceToString()}")
                        }
                    }
                )
        )
    }

    fun loadChaptersOfSubject(id: String) {
        _chaptersUiStateMSF.value = UIState.Loading
        compositeDisposable.add(
            chapterObservable.fetchAllChaptersLocal(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { chapters ->
                        _chaptersUiStateMSF.value = UIState.Success(chapters)
                    },
                    { error ->
                        _chaptersUiStateMSF.value = UIState.Error(error.cause?.localizedMessage ?: "Error!")
                        Log.e("TAG", "Got Error ${error.stackTraceToString()}")
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()  // Clear disposables
    }
}