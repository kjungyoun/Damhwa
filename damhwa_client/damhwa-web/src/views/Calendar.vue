<template>
  <div>
    <div>
      <h1 class="title">꽃 달력</h1>
    </div>
    <DatePicker
      :attributes="this.attributes"
      v-model="this.date"
      color="yellow"
      is-expanded
    />
  </div>
  <div>
    <div>
      {{}}
    </div>
    <div v-for="(data, idx) in this.filteredHistories" :key="idx">
      <div class="list">
        <div class="image-style">
          <img
            class="image-style"
            src="https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"
          />
        </div>
        <div>
          <div class="list-title">
            <span class="flower-name">{{ data.fno }}</span>
            <span>
              <img
                v-if="data.htype"
                class="separater"
                src="~@/assets/pink.png"
                alt=""
              />
              <img v-else class="separater" src="~@/assets/green.png" alt="" />
            </span>
          </div>
          <button class="button" @click="this.routeToHistoryDetail(data)">
            서신보기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import histories from "../data/dumpData.json";
export default {
  name: "Calendar",
  data() {
    return {
      historyDates: [],
      date: new Date(),
      attributes: [
        
      ],
      filteredHistories: [],
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
      console.log(userNo)
    },
    isMatched(selectedDate, date) {
      return (
        selectedDate.getFullYear() == date.getFullYear() &&
        selectedDate.getMonth() == date.getMonth() &&
        selectedDate.getDate() == date.getDate()
      );
    },
    checkDateAndFiltering() {
      this.filteredHistories = histories.filter((history) => {
        const dateObj = new Date(history.regdate);
        const selectedDateObj = new Date(this.date);

        if (this.isMatched(selectedDateObj, dateObj)) {
          return dateObj;
        }
      });
    },
  },
  beforeMount() {
    this.historyDates = histories.map((data) => {
      const tmpDateObj = new Date(data.regdate);
      return new Date(
        tmpDateObj.getFullYear(),
        tmpDateObj.getMonth(),
        tmpDateObj.getDate()
      );
    });
    this.attributes.push({
          dot: {
            style: {
              "background-color": "#7C947D",
            },
          },
          dates: this.historyDates,
        },
        {
          dot: {
            style: {
              "background-color": "#F4AFA9",
            },
          },
          dates: this.historyDates,
        })
    window["Calendar"] = {
      components: this,
      sendUserNo: (userNo) => this.sendUserNo(userNo),
    };
  },
  watch: {
    date: function() {
      this.checkDateAndFiltering();
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
  font-size: 30px;
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
