import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm


def set_config():
    # 폰트, 그래프 색상 설정
    font_list = fm.findSystemFonts(fontpaths=None, fontext="ttf")
    plt.rcParams["font.family"] = "AppleGothic"

    sns.set_palette(sns.color_palette("Spectral"))
    plt.rc("xtick", labelsize=6)


def show_store_categories_graph(dataframes, n=100):
    """
    Tutorial: 전체 음식점의 상위 `n`개 카테고리 분포를 그래프로 나타냅니다.
    """

    stores = dataframes["stores"]

    # 모든 카테고리를 1차원 리스트에 저장합니다
    categories = stores.category.apply(lambda c: c.split("|"))
    print(categories)
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

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    stores_reviews["counts"] = stores_reviews.groupby(["store", "store_name"])["score"].transform('count')
    print(stores_reviews)

    stores = stores_reviews.store_name.apply(lambda c: c.split("  "))
    print(stores)
    stores = itertools.chain.from_iterable(stores)

    stores = filter(lambda c: c != "", stores)
    stores_count = Counter(list(stores))
    best_stores = stores_count.most_common(n=200)
    df = pd.DataFrame(best_stores, columns=["store_name", "count"]).sort_values(
        by=["count"], ascending=False
    )

    chart = sns.barplot(x="store_name", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 리뷰 개수")
    plt.show()


def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """

    # stroes_reviews 는 store 정보와 reviews 정보를 합친 테이블
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    # 합친 테이블을 store 와 store_name 이 같은 것 (같은 식당) 끼리 묶는다.
    # 이때 리뷰 개수를 dataFrame에 counts 라는 열을 추가하여 저장한다.
    stores_reviews["counts"] = stores_reviews.groupby(["store", "store_name"])["score"].transform('count')
    scores_group = stores_reviews.groupby(["store", "store_name"])

    # 묶은 것들 중 score를 평균 계산한다.
    scores = scores_group.mean()

    # 여기서 counts 가 min_reviews 보다 작을 경우 해당 행 삭제한다.
    scores = scores[scores['counts'] >= 20]

    # 평균을 계산한 것들을  내림차순 정렬한다.
    score_sorted = scores.sort_values(by="score", ascending=False)
    df = pd.DataFrame(score_sorted, columns=["store_name", "score"]).sort_values(
        by=["score"], ascending=False
    )

    chart = sns.barplot(x="store_name", y="score", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 평균 평점")
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
    raise NotImplementedError


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
    # show_store_average_ratings_graph(data)


if __name__ == "__main__":
    main()
