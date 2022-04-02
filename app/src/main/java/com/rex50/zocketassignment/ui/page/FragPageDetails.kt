package com.rex50.zocketassignment.ui.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.databinding.FragPageDetailsBinding
import com.rex50.zocketassignment.ui.base.BaseFragmentWithListener
import com.rex50.zocketassignment.utils.Status
import com.rex50.zocketassignment.utils.collectLatestWithLifecycle
import com.rex50.zocketassignment.utils.extensions.loadImage
import com.rex50.zocketassignment.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragPageDetails : BaseFragmentWithListener<FragPageDetailsBinding, FragPageDetails.OnFragHomeInteractionListener>() {

    companion object {
        fun newInstance() = FragPageDetails()
    }

    private val viewModel: FragPageDetailsViewModel by viewModels()

    private val detailsAdapter by lazy { PageDetailsAdapter() }

    private var currentPageData: PageData? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragPageDetailsBinding {
        return FragPageDetailsBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initClicks()
        initRecycler()
        setupObservers()
    }

    override fun load() {
        viewModel.getPageInfo()
    }

    private fun initClicks() {
        binding?.btnUpdate?.setOnClickListener {
            currentPageData?.let { page ->
                showToast("Updating details...")
                viewModel.updatePageInfo(page)
            } ?: showToast("Problem while updating page")
        }

        binding?.btnChangePage?.setOnClickListener {
            listener?.onChangePage()
        }
    }

    private fun initRecycler() {
        binding?.recOtherDetails?.adapter = detailsAdapter
    }

    private fun setupObservers() {
        viewModel.pageData.collectLatestWithLifecycle(this) { data ->
            when(data.responseType) {
                Status.LOADING -> {
                    // TODO: Show loader
                }

                Status.SUCCESSFUL -> {
                    data.data?.let { page ->
                        currentPageData = page
                        updateUI(page)
                        sendDataToServer(page)
                    }
                }

                Status.ERROR -> {
                    // TODO: Update UI
                }
            }
        }
    }

    private fun sendDataToServer(pageData: PageData) {
        // TODO: send data to zocket server
    }

    private fun updateUI(currentPageData: PageData) {
        // TODO: Update UI
        binding?.apply {
            tvPageName.text = currentPageData.name
            ivPicture.loadImage(currentPageData.picture.data.url, animate = true)
            val details = viewModel.createDetailsList(currentPageData)
            detailsAdapter.update(details)
        }
    }

    interface OnFragHomeInteractionListener {
        fun onChangePage()
    }

}