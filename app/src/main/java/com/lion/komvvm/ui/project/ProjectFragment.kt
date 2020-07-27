package com.lion.komvvm.ui.project

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lion.komvvm.R
import com.lion.komvvm.entity.ArticlesBean
import com.lion.komvvm.recyclerview.*
import com.lion.komvvm.ui.activity.WebviewActivity
import com.lion.komvvm.utils.LineItemDecoration
import com.lion.mvvmlib.base.BaseFragment
import com.lion.mvvmlib.base.EventItem
import com.lion.mvvmlib.util.EXTRA_URL
import com.lion.mvvmlib.util.EventType
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment<ProjectViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.fragment_project

    override fun initView(savedInstanceState: Bundle?) {
        initTabLayout()
        rv_project.setup<ArticlesBean> {
            dataSource(mViewModel.mDatas.value)
            adapter {
                addItemView(R.layout.item_project_list) {
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
            addItemDecoration(LineItemDecoration(requireContext()))
        }
    }

    private fun initTabLayout() {
        mViewModel.mTabTitle.observe(this, Observer {
            it.forEach { title-> tl_project.addTab(tl_project.newTab().setText(title.name)) }
        })
        tl_project.addOnTabSelectedListener(mViewModel.mTabClickListener)
    }

//    @FlowPreview
//    @ExperimentalCoroutinesApi
    override fun lazyLoadData() {
        mViewModel.apply {
            getFirstData()

            //add observer
            mDatas.observe(this@ProjectFragment, Observer {
                (rv_project.adapter as KoAdapter<ArticlesBean>).addNewAll(it)
            })
        }
    }

    override fun handleEvent(event: EventItem) {
        when (event.type) {
            EventType.EVENT_MSG -> {
                val bean = event.obj as ArticlesBean
                val intent = Intent(context, WebviewActivity::class.java)
                intent.putExtra(EXTRA_URL, bean.link)
                startActivity(intent)
            }
            else -> {}
        }
    }
}