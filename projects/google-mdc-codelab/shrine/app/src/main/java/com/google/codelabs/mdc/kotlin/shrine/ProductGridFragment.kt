package com.google.codelabs.mdc.kotlin.shrine

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.*

class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shr_product_grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // setup toolbar
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar)
//        view.toolbar.setNavigationOnClickListener(NavigationIconClickListener(activity!!, view.product_grid))
        view.toolbar.setNavigationOnClickListener(
                NavigationIconClickListener(
                        activity!!,
                        view.product_grid,
                        AccelerateDecelerateInterpolator(),
                        ContextCompat.getDrawable(context!!, R.drawable.shr_branded_menu),
                        ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))

        // setup recycler view
        val productLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        productLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }

//        val productAdapter = ProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
        val productAdapter = StaggeredProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))

        view.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = productLayoutManager
            adapter = productAdapter
            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)
            addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            view.product_grid.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
