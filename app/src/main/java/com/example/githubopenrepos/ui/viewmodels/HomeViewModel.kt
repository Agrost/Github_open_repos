package com.example.githubopenrepos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubopenrepos.data.Answer
import com.example.githubopenrepos.domain.entity.GitCard
import com.example.githubopenrepos.domain.usecase.GetHomeUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _answer: MutableLiveData<Answer> = MutableLiveData(Answer.Loading)
    val answer: LiveData<Answer> get() = _answer
    private var listGitCard: MutableList<GitCard> = mutableListOf()

    init {
        getData()
    }

    fun getData() {
        setObserver(getHomeUseCase.getData())
    }

    fun getFirstPage() {
        setObserver(getHomeUseCase.getFirstPage())
    }

    private fun setObserver(observable: Single<Answer>) {
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it is Answer.Success) {
                            listGitCard = it.listGitCard.toMutableList()
                        }
                        _answer.value = it
                    },
                    {
                        _answer.value = Answer.Failure()
                    },
                )
        )
    }

    fun getNextPage() {
        if (_answer.value is Answer.Loading) return
        _answer.value = Answer.Loading
        compositeDisposable.add(
            getHomeUseCase.getNextPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { answer ->
                        if (answer is Answer.Success) {
                            addGitCardList(answer)
                        } else {
                            this._answer.value = answer
                        }
                    },
                    {
                        _answer.value = Answer.Failure(getNextPage = true)
                    },
                )
        )
    }

    private fun addGitCardList(answer: Answer.Success) {
        listGitCard.addAll(answer.listGitCard)
        this._answer.value = Answer.Success(listGitCard)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}