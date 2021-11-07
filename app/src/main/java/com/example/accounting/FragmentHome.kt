package com.example.accounting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.accounting.db.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.thread

class FragmentHome : Fragment() {

    private var accountDb: AccountDb? = null
    private lateinit var btn_home_add: Button

    private var bugetDb: BugetDb? = null
    private lateinit var bugetMoneyList: MutableList<BugetData>
    private lateinit var edt_home_buget: EditText
    private lateinit var btn_home_add_buget: Button

    val tz = TimeZone.getTimeZone("Asia/Seoul")
    val gc = GregorianCalendar(tz)
    var month = gc.get(GregorianCalendar.MONTH + 1).toString()
    var day = gc.get(GregorianCalendar.DATE).toString()
    var hour= gc.get(GregorianCalendar.HOUR).toString()
    var min = gc.get(GregorianCalendar.MINUTE).toString()

    private lateinit var txt_synthesis_home_total_content: TextView
    private lateinit var txt_synthesis_home_detail_content: TextView
    private lateinit var btn_alldata_reset: Button

    private var bugetToal : Int ?= null
    private var cash : Int ?= null
    private lateinit var spendMoney : MutableList<Int>


    private var str : String = month+ "월" + day +"일"+ "\n" + hour +"시" + min + "분"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        accountDb = AccountDb.getInstance(requireContext())
        bugetDb = BugetDb.getInstance(requireContext())

        btn_home_add = view.findViewById(R.id.btn_home_add)
        btn_home_add_buget = view.findViewById(R.id.btn_home_add_buget)
        txt_synthesis_home_total_content = view.findViewById(R.id.txt_synthesis_home_total_content)
        edt_home_buget = view.findViewById(R.id.edt_home_buget)
        txt_synthesis_home_detail_content = view.findViewById(R.id.txt_synthesis_home_detail_content)
        btn_alldata_reset = view.findViewById(R.id.btn_alldata_reset)


        //DB에 데이터 넣기
        AddDbData()
        createViewSetting()
        accountMoneySetting()
        allDataReset()

        btn_home_add_buget.setOnClickListener{
            try{
                accountMoneySetting()
                bugetMoneySetting()
            }catch (e: Exception){
                Log.d("btn_home_add_buget" , "erorr $e")
            }

        }
        btn_home_add.setOnClickListener{
            AddDbData()
            accountMoneySetting()
            FragmentBillingHistory().addAccountList()
        }
        return view
    }

    fun AddDbData(){
        val addRunnable = Runnable {
            try {
                val newAccount = Account()
                newAccount.Categoty = edt_home_categoty.text.toString()
                newAccount.Date = str
                newAccount.Money = Integer.parseInt(edt_home_money.text.toString())
                newAccount.Detail = edt_home_detail.text.toString()
                accountDb?.accountDao()?.insert(newAccount)
                edt_home_categoty.setText("")
                edt_home_money.setText("")
                edt_home_detail.setText("")
            } catch (e: Exception){
                Log.d("AddDbData", "erorr $e")
            }
        }
        val addThread = Thread(addRunnable)
        addThread.start()
    }

    //총액을 받아오고 초기화한다. 그리고 잔액과 소비금액 초기화해주는 함수accountMoney()실행
    fun bugetMoneySetting(){
        val addMoneyList = Runnable {
            try{
                //만약 없다면 넣고 있다면 넣으면 안된다.
                bugetMoneyList = bugetDb?.bugetDao()?.getAll()!!
                if(bugetMoneyList.size == 0){
                    val newBuget = Buget()
                    newBuget.TotalMoney = Integer.parseInt(edt_home_buget.text.toString())
                    newBuget.TotalName = "총액"
                    bugetDb?.bugetDao()?.insert(newBuget)
                    bugetMoneyList = bugetDb?.bugetDao()?.getAll()!!
                    txt_synthesis_home_total_content.text = bugetMoneyList[0].total_money.toString()
                }

                else{
                    bugetDb?.bugetDao()?.changeBuget(Integer.parseInt(edt_home_buget.text.toString()))
                    bugetMoneyList = bugetDb?.bugetDao()?.getAll()!!
                    txt_synthesis_home_total_content.text = bugetMoneyList[0].total_money.toString()
                }
                bugetToal = bugetMoneyList[0].total_money
                accountMoneySetting()
            }catch (e:Exception){
                Log.d("addMoneyList", "erorr $e")
            }
        }
        val addThread = Thread(addMoneyList)
        addThread.start()
        accountMoneySetting()
    }
    //소비금액 가져와서 텍스트에 넣고, 잔액 값도 텍스트에 넣는다.
    //잔액은 꼭 두번눌러야 하네..왜지..
    fun accountMoneySetting(){

            val spendMoneySetting = Runnable {
                spendMoney = accountDb?.accountDao()?.sumAccountMoney()!!
                if(spendMoney.size == 0){

                }
                else{
                    try {
                        txt_synthesis_home_detail_content.text = spendMoney[0].toString()
                        cash = bugetToal?.minus(spendMoney[0])
                        txt_synthesis_home_balance_content.text = cash.toString()
                    }catch (e: Exception){

                    }
                }
            }
            val addThread = Thread(spendMoneySetting)
            addThread.start()
    }
    //뷰가 처음 생성되었을 때, 초기화가 되어있어야 하는 값들..
    fun createViewSetting(){
        val createView = Runnable {
            bugetMoneyList = bugetDb?.bugetDao()?.getAll()!!
            if(bugetMoneyList.size == 0){
                bugetToal = null
            }
            else{
                bugetToal = bugetMoneyList[0].total_money
                accountMoneySetting()
                txt_synthesis_home_total_content.text = bugetMoneyList[0].total_money.toString()
                edt_home_buget.setText(bugetMoneyList[0].total_money.toString())
            }
            }
        val addThread = Thread(createView)
        addThread.start()
    }
    fun allDataReset(){

        btn_alldata_reset.setOnClickListener{
            val dataReset = Runnable {
                bugetDb?.bugetDao()?.delete()!!
                accountDb?.accountDao()?.delete()!!
            }
            val deleteThread = Thread(dataReset)
            deleteThread.start()
        }

    }
}

