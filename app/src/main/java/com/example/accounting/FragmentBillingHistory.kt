package com.example.accounting

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accounting.Rv.RvAccountAdapter
import com.example.accounting.db.AccountData
import com.example.accounting.db.AccountDb
import java.lang.Exception


class FragmentBillingHistory : Fragment() {

    private var categoryList = mutableListOf<String>()
    private var accountDb: AccountDb? = null
    private lateinit var rvBillingView: RecyclerView
    private lateinit var rvBillingAdapter : RvAccountAdapter
    private lateinit var BillingHistoryView: View

    private var categoryDataList = mutableListOf<AccountData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BillingHistoryView = inflater.inflate(R.layout.fragment_billing_history, container, false)

        //내부디비 객체 생성
        accountDb = AccountDb.getInstance(requireContext())

        setRecyclerView()

        addAccountList()
        return BillingHistoryView
    }

    fun addAccountList(){
        val r = Runnable {
            try {
                categoryList = accountDb?.accountDao()?.getCategory()!!
                Log.d("categoey", categoryList.toString())
                rvBillingAdapter.data.clear()
                (0 until categoryList.size).forEach {
                    //이 카테고리에 해당하는 카테고리 배열을 categoryDataList에 저장한다.
                    categoryDataList = accountDb?.accountDao()?.getAccountListByCategory(categoryList[it])!!
                    (0 until categoryDataList.size).forEach {
                        //해당카테고리에 맞는 카테고리 배열의 사이즈 만큼 넣는다
                        rvBillingAdapter.data.add(categoryDataList[it])
                    }
                    Log.d("RvDataList", accountDb?.accountDao()?.getAccountListByCategory(categoryList[0])!!.toString())
                    rvBillingAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception){
                Log.d("categoryList", "Error - $e")
            }
        }
        //바로 위에 카테고리 받아오는거 실행문
        val thread = Thread(r)
        thread.start()
    }

    fun setRecyclerView(){
        //리싸이클러뷰 어뎁터
        rvBillingView = BillingHistoryView.findViewById(R.id.rv_billing_history)

        rvBillingAdapter = RvAccountAdapter(requireContext())

        rvBillingView.adapter = rvBillingAdapter
        rvBillingView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
    }


}
