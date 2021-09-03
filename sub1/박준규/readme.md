test![음식점 분포](../박준규/img/음식점 분포.png)

```python
def show_store_review_distribution_graph(dataframes):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다.
    """

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    scores_group = stores_reviews.groupby(["store", "store_name"])
    df = pd.DataFrame(scores_group.size().reset_index(name='counts'))
    print(df["counts"])
    # 그래프 생성
    chart = sns.barplot(x="store_name", y="counts", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 리뷰분포")
    plt.show()
```

