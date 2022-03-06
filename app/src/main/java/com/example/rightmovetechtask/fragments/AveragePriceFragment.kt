package com.example.rightmovetechtask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.rightmovetechtask.R
import com.example.rightmovetechtask.viewmodels.PropertyAveragePriceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AveragePriceFragment : Fragment() {
    private val propertyAveragePriceViewModel: PropertyAveragePriceViewModel by viewModel()
    private val averagePriceView: TextView? get() = view?.findViewById(R.id.average_price_value)
    private val progressSpinner: ProgressBar? get() = view?.findViewById(R.id.progress_loader)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_average_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertyAveragePriceViewModel.getListOfProperties()

        propertyAveragePriceViewModel.averagePriceOfProperties.observe(viewLifecycleOwner) { averagePrice ->
            averagePrice?.let {
                averagePriceView?.text = it.toString()
                progressSpinner?.visibility = View.GONE
            }
        }

        propertyAveragePriceViewModel.errorWithPropertiesListOrPrices.observe(viewLifecycleOwner) { errorText ->
            errorText?.let {
                averagePriceView?.text = errorText
                progressSpinner?.visibility = View.GONE
            }
        }
    }
}