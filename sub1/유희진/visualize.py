import itertools
from collections import Counter
from pprint import pprint
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm


def set_config():
    # 폰트, 그래프 색상 설정
    plt.rcParams['axes.unicode_minus'] = False 
    plt.rcParams['font.family'] = "AppleGothic"
    sns.set_palette(sns.color_palette("Spectral"))
    plt.rc("xtick", labelsize=6)


def show_store_categories_graph(dataframes, n=100):
    """
    Tutorial: 전체 음식점의 상위 `n`개 카테고리 분포를 그래프로 나타냅니다.
    """

    stores = dataframes["stores"]

    # 모든 카테고리를 1차원 리스트에 저장합니다
    categories = stores.category.apply(lambda c: c.split("|"))
    categories = itertools.chain.from_iterable(categories)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    categories = filter(lambda c: c != "", categories)
    categories_count = Counter(list(categories))
    best_categories = categories_count.most_common(n=n)
    df = pd.DataFrame(best_categories, columns=["category", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="category", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_store_review_distribution_graph(dataframes):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    # 음식점과 리뷰 데이터를 머지합니다.
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    # 댓글의 개수를 알기 위해 count 컬럼을 추가합니다.
    stores_reviews['count'] = stores_reviews.groupby(["store", "store_name"])["store"].transform('count')

    # 각각 count의 개수를 알기 위해 count의 count를 추가합니다.    
    stores_reviews['count_of_count'] = stores_reviews.groupby(['count'])["count"].transform('count_of_count')
    pprint(stores_reviews)
    df = pd.DataFrame(stores_reviews, columns=["count", "count_of_count"])
    pprint(df)
    
    # 그래프로 나타냅니다
    chart = sns.barplot(x="store_name", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("전체 음식점의 리뷰 개수 분포")
    plt.show()


def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    # 각 음식점별 평균 평점을 뽑아낸다. 
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    stores_reviews['count'] = stores_reviews.groupby(["store", "store_name"])["store"].transform('count')
    stores_group = stores_reviews.groupby(["store", "store_name"]).mean()
    stores_group = stores_group[stores_group['count'] >= 30]
    pprint(stores_group)
    chart = sns.barplot(x="id_x", y="score", data=stores_group)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("각 음식점의 평균 평점")
    plt.show()


def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    raise NotImplementedError


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    users = pd.DataFrame(dataframes['users'])
    user_grouped = users.groupby('gender').size().to_frame('count').reset_index()
    pprint(pd.DataFrame(user_grouped))
    chart = sns.barplot(x="gender", y="count", data=user_grouped)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("전체 유저의 성별 분포")
    plt.show()

def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    raise NotImplementedError


def main():
    set_config()
    data = load_dataframes()
    show_store_categories_graph(data)
    show_store_review_distribution_graph(data)
    show_store_average_ratings_graph(data)
    show_user_age_gender_distribution_graph(data)


if __name__ == "__main__":
    main()
