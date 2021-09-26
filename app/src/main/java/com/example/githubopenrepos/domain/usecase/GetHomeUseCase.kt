package com.example.githubopenrepos.domain.usecase

import com.example.githubopenrepos.data.repository.HomeRepository
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    fun getData() = homeRepository.getData()
    fun getFirstPage() = homeRepository.getFirstPage()
    fun getNextPage() = homeRepository.getNextPage()
}