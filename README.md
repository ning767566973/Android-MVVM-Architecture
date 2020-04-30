# Android-MVVM-Architecture 简介
该项目是采用kotlin编写，结合Jetpack架构组件(Databinding 、ViewModel 、LiveData)+Retrofit+协程搭建的项目框架。

## 特点
主要是封装网络请求模块（过滤服务器返回异常）
只需要在viewModel中调用launchOnlyResult()方法
```
class ArticleListViewModel : BaseViewModel() {

    var articleList = MutableLiveData<HomeListBean>()

    private val articleRepository by lazy { InjectorUtil.getArticleRepository() }

    fun getArticleList(page: Int) {
        launchOnlyResult(
            {
                articleRepository.getArticleList(page)
            },
            {
                articleList.value = it
            },
            complete = {
                defUI.refreshFinishEvent.call()
            },
            isShowDialog = false
        )
    }
}
```
