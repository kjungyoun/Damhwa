from parse import load_dataframes
import pandas as pd
import shutil


def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """


    #stroes_reviews 는 store 정보와 reviews 정보를 합친 테이블
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
    scores = scores[scores['counts'] >= min_reviews]
    scores.round(2)

    # 평균을 계산한 것들을  내림차순 정렬한다.
    score_sorted = scores.sort_values(by="score", ascending=False)
    return score_sorted.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """

    # stroes_reviews 는 store 정보와 reviews 정보를 합친 테이블
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    # 합친 테이블을 store 와 store_name 이 같은 것 (같은 식당) 끼리 묶는다.
    # 이때 리뷰 개수를 dataFrame에 count 라는 열을 추가하여 저장한다.
    review_group = stores_reviews.groupby(["store", "store_name"])

    top_stores = review_group.count()

    store_sorted = top_stores.sort_values(by="score", ascending=False)
    return store_sorted.head(n=n).reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """

    # stroes_reviews 는 store 정보와 reviews 정보를 합친 테이블
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    user_group = stores_reviews.groupby(["user"])

    top_reviewed_user = user_group.count()

    user_sorted = top_reviewed_user.sort_values(by="score", ascending=False)
    return user_sorted.head(n=n).reset_index()


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    stores_most_scored = sort_stores_by_score(data)
    stores_most_reviewed = get_most_reviewed_stores(data)
    user_most_active = get_most_active_users(data)

    print("[음식점 평균 테이블]")
    print(f"{separater}\n")
    print(stores_most_scored.head())
    print(f"{separater}\n")

    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store}({score:.2f}점)".format(
                rank=i + 1, store=store.store_name, score=store.score
            )
        )
    print(f"\n{separater}\n\n")

    print("[가장 많은 리뷰 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_reviewed.iterrows():
        print(
            "{rank}위: {store}(상점번호: {store_num})".format(
                rank=i + 1, store=store.store_name, store_num=store.store
            )
        )
    print(f"\n{separater}\n\n")

    print("[가장 많은 리뷰를 남긴 유저]")
    print(f"{separater}\n")
    for i, user in user_most_active.iterrows():
        print(
            "{rank}위: {user}".format(
                rank=i+1, user=user.user
            )
        )



if __name__ == "__main__":
    main()
