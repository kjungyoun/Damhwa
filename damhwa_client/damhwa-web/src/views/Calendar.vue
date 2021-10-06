<template>
  <div class="container">
    <div>
      <div>
        <h1 class="title mt-4">꽃 달력</h1>
      </div>
      <DatePicker
        :attributes="this.attributes"
        v-model="this.date"
        color="yellow"
        is-expanded
      />
      <p class="title">날짜를 누르시오</p>
    </div>
    <div>
      <div class="d-flex m-4 justify-content-start">
        <h2 class="title">
          {{ date.getMonth() + 1 }}월 {{ date.getDate() }}일
        </h2>
      </div>
      <div v-for="(data, idx) in this.filteredHistories" :key="idx">
        <div class="list">
          <div class="image-style">
            <img
              style="border-radius: 250px"
              class="image-style"
              :src="data.flower.watercolor_img"
            />
          </div>
          <div>
            <div class="list-title">
              <span class="flower-name">{{ data.flower.fname_KR }}</span>
              <span>
                <img
                  v-if="!data.htype"
                  class="separater"
                  src="~@/assets/pink.png"
                  alt=""
                />

                <img
                  v-else
                  class="separater"
                  src="~@/assets/green.png"
                  alt=""
                />
              </span>
            </div>
            <button v-if="data.htype"
              class="button d-flex justify-content-start"
              @click="this.routeToHistoryDetail(data)"
            >
              서신보기
            </button>
            <button v-else
              class="button d-flex justify-content-start"
              @click="this.routeToHistoryDetail(data)"
            >
              감정보기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../util/constants";

export default {
  name: "Calendar",
  data() {
    return {
      histories: [],
      hFeelingDates: [],
      hLetterDates: [],
      date: new Date(),
      day: null,
      attributes: [],
      filteredHistories: [],
      localUserNo: null,
    };
  },
  methods: {
    routeToHistoryDetail(history) {
      if (history.htype) {
        this.$router.push({
          name: "LetterDetail",
          params: { historyId: history.hno },
        });
      } else {
        this.$router.push({
          name: "FeelingDetail",
          params: { historyId: history.hno },
        });
      }
    },
    sendUserNo(userNo) {
      this.getHistoryData(userNo);
      localStorage.setItem("userNo", userNo);
    },
    isMatched(selectedDate, date) {
      return (
        selectedDate.getFullYear() == date.getFullYear() &&
        selectedDate.getMonth() == date.getMonth() &&
        selectedDate.getDate() == date.getDate()
      );
    },
    getHistoryData(userNo) {
      axios
        .get(constants.BASE_URL, {
          params: {
            userno: userNo,
          },
        })
        .then((res) => {
          this.histories = res.data;
          this.filterInTwoType(res.data);
          this.checkDateAndFiltering();
        });
    },
    filterInTwoType(histories) {
      this.hFeelingDates = histories
        .filter((it) => it.htype)
        .map((history) => {
          const tmpDateObj = new Date(history.regdate);
          return new Date(
            tmpDateObj.getFullYear(),
            tmpDateObj.getMonth(),
            tmpDateObj.getDate()
          );
        });
      this.hLetterDates = histories
        .filter((it) => !it.htype)
        .map((history) => {
          const tmpDateObj = new Date(history.regdate);
          return new Date(
            tmpDateObj.getFullYear(),
            tmpDateObj.getMonth(),
            tmpDateObj.getDate() 
          );
        });
      this.setDatesInCalendar();
    },
    setDatesInCalendar() {
      this.attributes = [
        {
          dot: {
            style: {
              "background-color": "#7C947D",
            },
          },
          dates: this.hLetterDates,
        },
        {
          dot: {
            style: {
              "background-color": "#F4AFA9",
            },
          },
          dates: this.hFeelingDates,
        }
      ];
    },
    checkDateAndFiltering() {
      this.filteredHistories = this.histories.filter((history) => {
        const dateObj = new Date(history.regdate);
        const selectedDateObj = new Date(this.date);

        if (this.isMatched(selectedDateObj, dateObj)) {
          return dateObj;
        }
      });
    },
  },
  beforeMount() {
<<<<<<< HEAD
=======
    // this.sendUserNo(1927960578)
>>>>>>> 6d0cab8ff4ad167eb652d40b4e2f88d93baa811e
    window["Calendar"] = {
      components: this,
      sendUserNo: (userNo) => this.sendUserNo(userNo),
    };
    if (localStorage.getItem("userNo") !== null) {
      this.localUserNo = localStorage.getItem("userNo");
      this.getHistoryData(Number(this.localUserNo));
    }
  },
  mounted() {
    if (localStorage.getItem("day") !== null) {
      const backDate = localStorage.getItem("day");
      this.date = new Date(backDate);
    }
  },
  watch: {
    date: function () {
      this.checkDateAndFiltering();
      localStorage.setItem("day", this.date);
    },
  },
};
</script>

<style scoped>
.calendar-bg {
  height: 100vh;
  background-image: url("~@/assets/calender_bg.png");
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
}

.list {
  width: 95%;
  height: 100px;
  display: flex;
  margin: 5px auto;
  padding: 10px;
  background-color: #ffffff85;
  border-radius: 12px;
  font-family: "SangSangRock";
}

.image-style {
  width: 100px;
  height: 100%;
  margin-right: 50px;
}

.flower-name {
  color: black;
  font-size: 20px;
}

.separater {
  width: 10px;
  height: 10px;
  margin-left: 10px;
}

.list-title {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
