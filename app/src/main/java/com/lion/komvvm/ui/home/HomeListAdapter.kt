package com.lion.komvvm.ui.home

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lion.komvvm.R
import com.lion.komvvm.entity.ArticlesBean

class HomeListAdapter:BaseQuickAdapter<ArticlesBean,BaseViewHolder>(R.layout.item_article_list) {
    override fun convert(helper: BaseViewHolder?, item: ArticlesBean) {
        helper?.apply {
            setText(R.id.tv_project_list_article_type, item.chapterName)
            setText(R.id.tv_project_list_article_title, item.title)
            setText(R.id.tv_project_list_article_time, item.niceDate)
            setText(R.id.tv_project_list_article_author, item.author)
            val imageView = helper.getView<ImageView>(R.id.iv_project_list_article_ic)
            if (item.envelopePic?.isNotEmpty() ?:false) {
                Glide.with(imageView).load(item.envelopePic).into(imageView)
            }
        }
    }
}