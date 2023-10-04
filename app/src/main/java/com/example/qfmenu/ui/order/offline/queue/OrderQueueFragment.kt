package com.example.qfmenu.ui.order.offline.queue

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.qfmenu.R
import com.example.qfmenu.SCREEN_LARGE
import com.example.qfmenu.databinding.FragmentOrderQueueBinding
import com.example.qfmenu.util.OrderQueueAdapter
import com.example.qfmenu.util.OrderQueueBillAdapter
import com.example.qfmenu.viewmodels.CustomerOrderQueue
import com.example.qfmenu.viewmodels.SaveStateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderQueueFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderQueueFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val saveStateViewModel: SaveStateViewModel by activityViewModels()

    private var _binding: FragmentOrderQueueBinding? = null
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
        _binding = FragmentOrderQueueBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val slidingPaneLayout =
            requireActivity().findViewById<SlidingPaneLayout>(R.id.sliding_pane_layout)
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.navBar)
        val width = resources.displayMetrics.widthPixels / resources.displayMetrics.density
        val spanCount = if (width < SCREEN_LARGE) 1 else 2
        val recyclerViewQueue = binding.horizontalListOrderQueue
        val recyclerViewDish = binding.recyclerViewItemConfirmOrderView

        val orderQueueList: MutableList<CustomerOrderQueue> =
            saveStateViewModel.stateCustomerOrderQueues

        if (orderQueueList.size <= 0) {
            findNavController().popBackStack()
        } else {

            orderQueueList[0].isSelected = true
            val adapterOrderQueueBill = OrderQueueBillAdapter(
                requireContext(),
                orderQueueList[0].dishesAmountDb
            )
            var position = 0

            val adapterOrderQueue = OrderQueueAdapter(
                {
                    position = it[0]
                    adapterOrderQueueBill.setDataset(
                        orderQueueList[it[0]].dishesAmountDb
                    )
                    adapterOrderQueueBill.notifyDataSetChanged()
                },
                requireContext(),
                orderQueueList
            )

            recyclerViewQueue.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerViewDish.layoutManager = GridLayoutManager(requireContext(), spanCount)

            recyclerViewQueue.adapter = adapterOrderQueue
            recyclerViewDish.adapter = adapterOrderQueueBill


            navBar.setOnItemSelectedListener { menuItem ->
                if (menuItem.itemId == R.id.homeMenu) {
                    slidingPaneLayout.closePane()
                    navBar.visibility = View.GONE
                    saveStateViewModel.stateCustomerOrderQueues = mutableListOf()
                }
                if (menuItem.itemId == R.id.backToHome) {
//                saveStateViewModel.stateCustomerWithSelectDishesToBill = mutableListOf()
                    findNavController().popBackStack()
                    saveStateViewModel.stateCustomerOrderQueues = mutableListOf()
                }
                if (menuItem.itemId == R.id.optionOne) {
                    val pNext = (position + 1) % adapterOrderQueue.getDataset.size
                    adapterOrderQueue.getDataset[pNext].isSelected = true
                    adapterOrderQueue.getDataset[position].isSelected = false
                    adapterOrderQueue.notifyItemChanged(pNext)
                    adapterOrderQueue.notifyItemChanged(position)
                    adapterOrderQueueBill.setDataset(
                        orderQueueList[pNext].dishesAmountDb
                    )
                    adapterOrderQueueBill.notifyDataSetChanged()
                    position = pNext
                }
                if (menuItem.itemId == R.id.optionTwo) {
                    saveStateViewModel.setStateCustomer(adapterOrderQueue.getDataset[position].customerDb)
                    saveStateViewModel.posCusCurrentQueue = position
                    findNavController().navigate(R.id.action_orderQueueFragment_to_prepareBillFragment)
                }
                true
            }


            val homeMenu = navBar.menu.findItem(R.id.homeMenu)
            val backMenu = navBar.menu.findItem(R.id.backToHome)
            val optionOne = navBar.menu.findItem(R.id.optionOne)
            val optionTwo = navBar.menu.findItem(R.id.optionTwo)

            homeMenu.isVisible = width < SCREEN_LARGE
            backMenu.isVisible = true
            optionOne.isVisible = true
            optionTwo.isVisible = true

            homeMenu.setIcon(R.drawable.ic_home)
            backMenu.setIcon(R.drawable.ic_arrow_back)
            optionOne.setIcon(R.drawable.ic_skip_next)
            optionTwo.setIcon(R.drawable.ic_approve_order)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderQueueFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderQueueFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}