package com.lion.komvvm.ui.project

import CHAPTER_ID
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.recyclerview.*
import com.lion.komvvm.ui.activity.WebviewActivity
import com.lion.komvvm.utils.LineItemDecoration
import com.lion.mvvmlib.base.BaseFragment
import com.lion.mvvmlib.util.EXTRA_URL
import kotlinx.android.synthetic.main.fragment_common_sub.*

class CommonSubFragment : BaseFragment<CommonSubViewModel, ViewDataBinding>() {
    companion object{
        fun instance(cid: Int):CommonSubFragment {
            val fragment = CommonSubFragment()
            fragment.arguments = bundleOf(CHAPTER_ID to cid)
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_common_sub

    override fun initView(savedInstanceState: Bundle?) {
        rv_common.setup<ArticlesBean> {
            dataSource(mViewModel.mDatas.value)
                .adapter {
                addItemView(R.layout.item_article_list) {
                    onBindViewHolder { data, _, _ ->
                        setText(R.id.tv_project_list_article_type, data?.chapterName)
                        setText(R.id.tv_project_list_article_title, data?.title)
                        setText(R.id.tv_project_list_article_time, data?.niceDate)
                        setText(R.id.tv_project_list_article_author, data?.author)
                        setImageUrl(R.id.iv_project_list_article_ic,data?.envelopePic)
                    }
                }
                setItemClickListener { adapter, position->
                    val bean = adapter.mDatas[position]
                    val intent = Intent(context, WebviewActivity::class.java)
                    intent.putExtra(EXTRA_URL, bean.link)
                    startActivity(intent)
                }
            }
                .attach()
                .addItemDecoration(LineItemDecoration(requireContext()))
        }
    }

    override fun lazyLoadData() {
        mViewModel.apply {
            getProjectList(arguments?.getInt(CHAPTER_ID, 0) ?: 0)
            //add observer
            mDatas.observe(this@CommonSubFragment, Observer {
                (rv_common.adapter as KoAdapter<ArticlesBean>).addNewAll(it)
            })
        }
    }
}