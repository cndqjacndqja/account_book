package com.example.accounting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.accounting.db.Buget
import com.example.accounting.db.BugetData
import com.example.accounting.db.BugetDb
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //이건 왜 타입을 정해주지 않았는데 변수가 선언된거지?
    private val fragmentHome = FragmentHome()
    private var fragmentBillingHistory = FragmentBillingHistory()
    private val fragmentCertification = FragmentCertification()
    private var active : Fragment = fragmentHome
    private lateinit var mainBottomNavi :BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBottomNavi = findViewById(R.id.main_bottom_navi)

        supportFragmentManager.beginTransaction().add(R.id.main_fragment_layout, active).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_fragment_layout, fragmentBillingHistory).hide(fragmentBillingHistory).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_fragment_layout, fragmentCertification).hide(fragmentCertification).commit()

        //이게 왜 이렇게 되는지 다 뜯어보기, R.id.menu_home -> 이거는 람다식이야??
        fragmentSetting()
    }
    fun fragmentSetting(){
        mainBottomNavi.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragmentHome).commit()
                    active = fragmentHome
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_billing_history -> {
                    //어떻게 해야 refresh가 될까..?
                    //현재 있는 fragment를 제거하고 새로운 fragment의 객체를 만들어서 실행시켜야 한다.
                    //그렇다면, 여기서 navi를 눌렀을 때, fragment를 초기화 하고 그걸 실행하면 안되는건가..?
                    //그러려면 transaction에 있는 객체를 삭제하고 새로 넣어야 하는데..,, 해결못함..
                    fragmentBillingHistory.addAccountList()
                    supportFragmentManager.beginTransaction().hide(active).show(fragmentBillingHistory).commit()
                    active = fragmentBillingHistory
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_certification -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragmentCertification).commit()
                    active = fragmentCertification
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }


}
