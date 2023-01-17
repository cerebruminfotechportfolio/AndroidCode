package com.android.cerekartvendorapp.views.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.HomeTopUsersAdapter
import com.android.cerekartvendorapp.constants.DateConstants
import com.android.cerekartvendorapp.databinding.FragmentHomeBinding
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.android.cerekartvendorapp.views.base.BaseFragment
import com.prolificinteractive.materialcalendarview.*
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener,
    OnRangeSelectedListener {
    private lateinit var mAdapter: HomeTopUsersAdapter
    private lateinit var mBinding: FragmentHomeBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
        initRecyclerView()
        onSetRangeMode()
    }

    private fun onSetRangeMode() {
        mBinding.calendarView.state().edit().setMaximumDate(CalendarDay.today()).commit()
        mBinding.calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_RANGE
        mBinding.calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
        mBinding.calendarView.setCurrentDate(CalendarDay.today())
    }

    private fun setClickListeners() {
        mBinding.tvSelectDate.setOnClickListener(this)
        mBinding.calendarView.setOnRangeSelectedListener(this)
    }

    private fun initRecyclerView() {
        mAdapter = HomeTopUsersAdapter()
        val layoutRecManager = LinearLayoutManager(requireActivity())
        mBinding.rvUser.apply {
            layoutManager = layoutRecManager
            adapter = mAdapter
        }
    }


    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.tvSelectDate -> {
                mBinding.calendarView.visibility = View.VISIBLE
            }

        }
    }


    override fun onRangeSelected(widget: MaterialCalendarView, dates: MutableList<CalendarDay>) {
        mBinding.calendarView.visibility = View.GONE
        val calStart = Calendar.getInstance()
        val calEnd = Calendar.getInstance()
        calStart.set(Calendar.DAY_OF_MONTH, dates.get(0).day)
        calStart.set(Calendar.MONTH, dates.get(0).month + 1)
        calStart.set(Calendar.YEAR, dates.get(0).year)

        calEnd.set(Calendar.DAY_OF_MONTH, dates.get(dates.size - 1).day)
        calEnd.set(Calendar.MONTH, dates.get(dates.size - 1).month + 1)
        calEnd.set(Calendar.YEAR, dates.get(dates.size - 1).year)

    }


}