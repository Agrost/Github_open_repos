package com.example.githubopenrepos.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubopenrepos.R
import com.example.githubopenrepos.appComponent
import com.example.githubopenrepos.data.Answer
import com.example.githubopenrepos.databinding.HomeFragmentBinding
import com.example.githubopenrepos.di.DaggerViewModelFactory
import com.example.githubopenrepos.ui.recycler.RecyclerAdapter
import com.example.githubopenrepos.ui.viewmodels.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.home_fragment) {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    private val viewBinding by viewBinding(HomeFragmentBinding::bind)
    private var firstLaunch = true
    private var recyclerAdapter: RecyclerAdapter? = null
    private val homeViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        addBindings()
    }

    private fun addBindings() {
        with(viewBinding) {
            gitRecycler.apply {
                adapter = getRecycler()
                addOnScrollListener(getScrollListener())
            }
            swipeRefreshLayout.setOnRefreshListener {
                homeViewModel.getFirstPage()
                swipeRefreshLayout.isRefreshing = false
            }
            retryBtn.setOnClickListener { homeViewModel.getFirstPage() }
        }
    }

    private fun initDagger() {
        requireActivity().appComponent
            .registerHomeComponent()
            .create()
            .inject(this)
    }

    private fun getScrollListener(): RecyclerView.OnScrollListener {
        return object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    homeViewModel.getNextPage()
                }
            }
        }
    }

    private fun getRecycler(): RecyclerAdapter {
        recyclerAdapter = RecyclerAdapter(
            openWebPage = { openWebPage(it) },
            sendUrl = { sendUrl(it) }
        )
        homeViewModel.answer.observe(viewLifecycleOwner) {
            showView(it)
        }
        return recyclerAdapter as RecyclerAdapter
    }

    private fun showView(answer: Answer) {
        hideAllViews()
        with(viewBinding) {
            when (answer) {
                is Answer.Loading -> {
                    recyclerShimmer.isVisible = firstLaunch
                    recyclerProgressBar.isVisible = true
                    swipeRefreshLayout.isVisible = true
                    firstLaunch = false
                }
                is Answer.Success -> {
                    recyclerAdapter?.items = answer.listGitCard.toMutableList()
                    swipeRefreshLayout.isVisible = true
                }
                is Answer.Failure -> {
                    if (answer.getNextPage) {
                        showToast(R.string.toast_error_message)
                        swipeRefreshLayout.isVisible = true
                    } else {
                        errorContainer.isVisible = true
                    }

                }
            }
        }
    }

    private fun hideAllViews() {
        with(viewBinding) {
            recyclerProgressBar.isVisible = false
            recyclerShimmer.isVisible = false
            swipeRefreshLayout.isVisible = false
            errorContainer.isVisible = false
        }
    }

    private fun showToast(errorMessageResource: Int) {
        Toast.makeText(
            activity,
            getString(errorMessageResource),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    private fun sendUrl(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}