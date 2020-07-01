package com.hendi.test_tiket.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hendi.test_tiket.App
import com.hendi.test_tiket.R
import com.hendi.test_tiket.view.adapter.UserAdapter
import com.hendi.test_tiket.view.util.hideKeyboard
import com.hendi.test_tiket.view.viewmodel.MainViewModel
import com.hendi.test_tiket.view.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: UserAdapter

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inject activity
        App.appComponent.inject(this)

        // Init view model
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        // Initialization
        initRecycleView()
        initScrollListener()
        initViewListener()
        subscribeViewModel()
    }

    private fun initRecycleView() {
        rv_users.adapter = adapter
        rv_users.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initViewListener() {
        btn_search.setOnClickListener {
            doSearch()
        }

        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun subscribeViewModel() {
        mainViewModel.userResponse.observe(this, Observer {
            if (mainViewModel.page == 1) {
                adapter.setData(it)
            } else {
                adapter.addData(it)
            }
        })

        mainViewModel.errorMessage.observe(this, Observer {
            tv_loading.text = it
            adapter.clear()
        })

        mainViewModel.errorLoadMore.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun doSearch() {
        window.decorView.hideKeyboard()
        mainViewModel.search(et_search.text.toString().trim())
    }

    private fun initScrollListener() {
        rv_users.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!mainViewModel.isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        mainViewModel.loadMore()
                    }
                }
            }
        })
    }
}
