package com.example.menumanager.order.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.qfmenu.viewmodels.models.Dish
import com.example.qfmenu.viewmodels.models.Customer
import com.example.menumanager.order.OrderListAdapter
import com.example.qfmenu.viewmodels.models.Table
import com.example.qfmenu.R
import com.example.qfmenu.SCREEN_LARGE
import com.example.qfmenu.databinding.FragmentOrderListUnconfirmedBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderListUnconfirmedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderListUnconfirmedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentOrderListUnconfirmedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListUnconfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.navBar)
        val slidePaneLayout =
            requireActivity().findViewById<SlidingPaneLayout>(R.id.sliding_pane_layout)
        val width = resources.displayMetrics.widthPixels / resources.displayMetrics.density
        val recyclerView = binding.orderListUnconfirmedRecyclerView
        val spanCount = if (width < SCREEN_LARGE) 1 else 2

        val backMenu = navBar.menu.findItem(R.id.backToHome)
        val homeMenu = navBar.menu.findItem(R.id.homeMenu)
        val optionOne = navBar.menu.findItem(R.id.optionOne)
        val optionTwo = navBar.menu.findItem(R.id.optionTwo)


        optionOne.isVisible = true
        optionTwo.isVisible = true
        backMenu.isVisible = false
        homeMenu.isVisible = width < SCREEN_LARGE

        homeMenu.setIcon(R.drawable.ic_home)
        optionOne.setIcon(R.drawable.ic_search)
        optionTwo.setIcon(R.drawable.ic_approve_order)

        navBar.setOnItemSelectedListener {
            if (it.itemId == R.id.homeMenu) {
                slidePaneLayout.closePane()
                navBar.visibility = View.GONE
            }
            if (it.itemId == R.id.optionOne) {

            }
            if (it.itemId == R.id.optionTwo) {
                findNavController().navigate(R.id.action_orderListUnconfirmedFragment_to_orderQueueFragment)
            }
            true
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.adapter = OrderListAdapter(
            true,
            requireContext(),
            mutableListOf(
                Customer(
                    1,
                    Table("Online", "None"),
                    Date(2022, 12, 1),
                    listOf(
                        Dish(R.drawable.img_image_4, "title1", "something", "18,000$", 1),
                        Dish(R.drawable.img_image_4, "title2", "something", "18,000$", 1)
                    ),
                    "joaisjdof",
                    "jaoisdjf",
                    "now",
                    "0123456789",
                    "address 1",
                ),
            )
        )


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderListUnconfirmedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderListUnconfirmedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}