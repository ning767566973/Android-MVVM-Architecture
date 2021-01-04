package com.cuining.mvvm.ui.article

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cuining.mvvm.R
import com.cuining.mvvm.bean.ArticlesBean
import com.cuining.mvvm.databinding.ItemArticleListBinding
import com.cuining.mvvm.utils.load

/**
 * @author: cuining
 * @date: 2020/4/26
 */
class ArticleListAdapter :
    BaseQuickAdapter<ArticlesBean, BaseDataBindingHolder<ItemArticleListBinding>>(R.layout.item_article_list) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemArticleListBinding>,
        item: ArticlesBean
    ) {
        holder.dataBinding?.let {
            it.article = item
            it.executePendingBindings()
        }
        val imageView = holder.getView<ImageView>(R.id.iv_article_ic)
        imageView.load(item.envelopePic)
    }

}