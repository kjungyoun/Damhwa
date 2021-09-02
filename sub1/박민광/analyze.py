from parse import load_dataframes
import pandas as pd
import shutil

pd.set_option('display.max_columns', 20)
pd.set_option('display.width', 2000)
def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    stores_reviews['counts'] = stores_reviews.groupby(['store','store_name'])['score'].transform('count')
    scores_group = stores_reviews.groupby(["store","store_name"])


    scores = scores_group.mean()

    scores = scores[scores['counts'] >= min_reviews]

    scores_sort = scores.sort_values(by=['score'], ascending=False)

    return scores_sort.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])
    scores = scores_group.count()

    scores_sort = scores.sort_values(by=['score'], ascending=False)

    return scores_sort.head(n=n).reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["user"])
    scores = scores_group.count()

    scores_sort = scores.sort_values(by=['score'], ascending=False)

    return scores_sort.head(n=n).reset_index()



def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    stores_most_scored = sort_stores_by_score(data)
    stores_most_reviewd = get_most_reviewed_stores(data)
    users_most_reviewd = get_most_active_users(data)


    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store}({score}점)".format(
                rank=i + 1, store=store.store_name, score=round(store.score,2)
            )
        )
    print(f"\n{separater}\n\n")

    print("[리뷰 많은 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_reviewd.iterrows():
        print(
            "{rank}위: {store}({score}개)".format(
                rank=i + 1, store=store.store_name, score=store.score
            )
        )
    print(f"\n{separater}\n\n")

    print("[리뷰 많이 남긴 유저]")
    print(f"{separater}\n")
    for i, store in users_most_reviewd.iterrows():
        print(
            "{rank}위: USERID {user}({score}개)".format(
                rank=i + 1, user=store.user, score=store.score
            )
        )
    print(f"\n{separater}\n\n")

if __name__ == "__main__":
    main()
