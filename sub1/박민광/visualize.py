import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import statistics

pd.set_option('display.max_columns', 20)
pd.set_option('display.width', 2000)
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
    stores = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    print(stores)
    # 모든 카테고리를 1차원 리스트에 저장합니다
    store_names = stores.store_name.apply(lambda c: c.split("|"))
    store_names = itertools.chain.from_iterable(store_names)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    categories = filter(lambda c: c != "", store_names)
    categories_count = Counter(list(categories))
    best_categories = categories_count.most_common(n=100)
    df = pd.DataFrame(best_categories, columns=["store_name", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="store_name", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 리뷰개수 분포")
    plt.show()


def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    stores = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    stores = stores.groupby(["store", "store_name"])
    stores = stores.mean()

    print(stores)
    # 모든 카테고리를 1차원 리스트에 저장합니다
    store_names = stores.store_name.apply(lambda c: c.split("|"))
    store_names = itertools.chain.from_iterable(store_names)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    categories = filter(lambda c: c != "", store_names)
    categories_count = Counter(list(categories))
    # print(categories_count)
    best_categories = categories_count.most_common(n=100)
    df = pd.DataFrame(best_categories, columns=["store_name", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="store_name", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 리뷰개수 분포")
    plt.show()


def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    stores = dataframes["reviews"]
    # 모든 카테고리를 1차원 리스트에 저장합니다
    users = stores.user.apply(lambda c: str(c))
    # users = itertools.chain.from_iterable(users)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    users = filter(lambda c: c != "", users)
    users_count = Counter(list(users))

    best_users = users_count.most_common(n=20)
    df = pd.DataFrame(best_users, columns=["user", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="user", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("유저 리뷰개수 분포")
    plt.show()


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    stores = dataframes["reviews"]
    print(stores)
    # 모든 카테고리를 1차원 리스트에 저장합니다
    users = stores.user.apply(lambda c: str(c))
    # users = itertools.chain.from_iterable(users)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    users = filter(lambda c: c != "", users)
    users_count = Counter(list(users))

    best_users = users_count.most_common(n=20)
    df = pd.DataFrame(best_users, columns=["user", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="user", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("유저 나이대 분포")
    plt.show()

def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    stores = dataframes["stores"]

    # 모든 카테고리를 1차원 리스트에 저장합니다
    areas = stores.area.apply(lambda c: str(c))
    # areas = itertools.chain.from_iterable(areas)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    areas = filter(lambda c: c != "None", areas)
    areas_count = Counter(list(areas))
    best_areas = areas_count.most_common(n=20)
    df = pd.DataFrame(best_areas, columns=["area", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="area", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 위치 분포")
    plt.show()

def main():
    set_config()
    data = load_dataframes()
    # show_store_categories_graph(data)
    # show_store_review_distribution_graph(data)
    # show_store_average_ratings_graph(data)
    # show_user_review_distribution_graph(data)
    # show_user_age_gender_distribution_graph(data)
    show_stores_distribution_graph(data)

if __name__ == "__main__":
    main()
