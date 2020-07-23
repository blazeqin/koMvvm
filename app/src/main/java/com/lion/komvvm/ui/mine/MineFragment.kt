package com.lion.komvvm.ui.mine

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.komvvm.entity.UserBean
import com.lion.komvvm.recyclerview.KoAdapter
import com.lion.komvvm.recyclerview.addItemView
import com.lion.komvvm.recyclerview.setText
import com.lion.komvvm.recyclerview.setup
import com.lion.komvvm.ui.activity.WebviewActivity
import com.lion.komvvm.utils.LineItemDecoration
import com.lion.mvvmlib.base.BaseFragment
import com.lion.mvvmlib.util.EXTRA_URL
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment<MineViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.fragment_mine

    override fun initView(savedInstanceState: Bundle?) {
        rv_mine.setup<Any> {
            dataSource(mViewModel.mDatas.value)
            adapter {
                addItemView(R.layout.item_mine_title){
                    isGivenViewType{data,_-> data is String}
                    onBindViewHolder{data, _, _ ->
                        val item = data as? String
                        setText(R.id.tv_header, item)
                    }
                }

                addItemView(R.layout.item_mine_list){
                    isGivenViewType { data, _ -> data is UserBean }
                    onBindViewHolder { data, _, _ ->
                        val item = data as? UserBean
                        setText(R.id.tv_title, item?.name)
                        setText(R.id.tv_link, item?.link)
                    }
                }
                setItemClickListener { koAdapter, i ->
                    val item = koAdapter.getItem(i)
                    if (item is UserBean) {
                        val intent = Intent(context, WebviewActivity::class.java)
                        intent.putExtra(EXTRA_URL, item.link)
                        startActivity(intent)
                    }
                }
            }
            addItemDecoration(LineItemDecoration(requireContext()))
        }
        mViewModel.mDatas.observe(this, Observer {
            (rv_mine.adapter as KoAdapter<Any>).addNewAll(it)
        })
    }

    override fun lazyLoadData() {
        mViewModel.getMineData()
    }
}