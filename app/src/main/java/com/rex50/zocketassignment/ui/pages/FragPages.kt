package com.rex50.zocketassignment.ui.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rex50.zocketassignment.databinding.FragPagesBinding
import com.rex50.zocketassignment.ui.base.BaseFragmentWithListener
import com.rex50.zocketassignment.utils.Status
import com.rex50.zocketassignment.utils.collectLatestWithLifecycle
import com.rex50.zocketassignment.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragPages : BaseFragmentWithListener<FragPagesBinding, FragPages.OnFragFBPagesInteractionListener>() {

    companion object {
        fun newInstance() = FragPages()
    }

    private val viewModel: FragPagesViewModel by viewModels()

    private val pagesAdapter: PageListAdapter by lazy { PageListAdapter() }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragPagesBinding {
        return FragPagesBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initRecycler()

        initClicks()

        setupObservers()
    }

    override fun load() {
        viewModel.fetchPages()
    }

    private fun initRecycler() {
        binding?.recPages?.let { recyclerView ->
            recyclerView.adapter = pagesAdapter
        }
    }

    private fun initClicks() {
        pagesAdapter.onPageClicked = { page ->
            fragScope?.launch {
                viewModel.setSelectedPage(page)
                listener?.onPageSelected()
            }
        }
    }

    private fun setupObservers() {
        viewModel.pagesFlow.collectLatestWithLifecycle(this) { data ->
            binding?.tvTitle?.visibility = View.GONE
            when(data.responseType) {
                Status.LOADING -> {
                    showLoader(true)
                }

                Status.SUCCESSFUL -> {
                    showLoader(false)
                    binding?.tvTitle?.visibility = View.VISIBLE
                    data.data?.let { list ->
                        pagesAdapter.updatePages(list)
                    }
                }

                Status.ERROR -> {
                    showLoader(false)
                    showToast("Problem while getting your pages")
                }
            }
        }
    }

    private fun showLoader(show: Boolean) {
        binding?.progressBar?.visibility = if(show) View.VISIBLE else View.GONE
    }

    interface OnFragFBPagesInteractionListener {
        fun onPageSelected()
    }

}