<template>
  <div>
    <div>
      <h1 class="title">꽃 달력</h1>
    </div>
      <DatePicker
        :attributes='calendarData.attributes'
        v-model="calendarData.date" 
        color="yellow"
        is-expanded
      />
  </div>  
  <div>
    <div>
      {{ }}
    </div>
    <div v-for="(data, idx) in calendarData.filteredHistories" :key="idx">
      <div class="list">
        <div class="image-style">
          <img class="image-style" src="https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"/>
        </div>
        <div>
          <div class="list-title">
            <span class="flower-name">{{data.fno}}</span>
            <span> 
              <img v-if="data.htype" class="separater" src="~@/assets/pink.png" alt="">
              <img v-else class="separater" src="~@/assets/green.png" alt="">
            </span>
          </div>
          <button class="button" @click="routeToHistoryDetail(data)"> 서신보기 </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import histories from '../data/dumpData.json'
import { reactive, watch } from 'vue'
import { useRouter } from 'vue-router'
export default {
  name: 'Calendar',
  setup() {
    const router = useRouter()
    const historyDates = histories.map(data => {
      const tmpDateObj =  new Date(data.regdate)
      return new Date(tmpDateObj.getFullYear(), tmpDateObj.getMonth(), tmpDateObj.getDate())
    })
    const calendarData = reactive({
      date: new Date(),
      attributes: [
        {
          dot: {
          style: {
            'background-color': '#7C947D',
            },
          },
          dates: historyDates,
        },
        {
          dot: {
          style: {
            'background-color': '#F4AFA9',
            },
          },
          dates: historyDates,
        },
      ],
      filteredHistories: []
      })

    const checkDateAndFiltering = () => {
        calendarData.filteredHistories = histories.filter(history => {
          const dateObj =  new Date(history.regdate)
          const selectedDateObj = new Date(calendarData.date)

          if (isMatched(selectedDateObj, dateObj)) {
            return dateObj
          }
        })
    }
    
    const isMatched = (selectedDate, date) => {
      return selectedDate.getFullYear() == date.getFullYear() 
              && selectedDate.getMonth() == date.getMonth() 
              && selectedDate.getDate() == date.getDate()
    }

    const routeToHistoryDetail = function(history) {
      if (history.htype) {
        router.push({name: 'LetterDetail', params: { historyId: history.hno }})
      } else {
        router.push({name: 'FeelingDetail', params: { historyId: history.hno }})
      }
    }

    watch(() => checkDateAndFiltering())
    
    return { calendarData, routeToHistoryDetail } 
  }
}
</script>

<style scoped>

.title {
  font-family: 'SangSangRock';
  color: black;
}

.list {
  width: 95%;
  height: 100px;
  display: flex;
  margin: 5px auto;
  padding: 10px;
  background-color: #FFFFFF85;
  border-radius: 12px;
  font-family: 'SangSangRock';
}  

.image-style {
  width: 100px;
  height: 100%;
  margin-right: 50px;
}

.button {
  border: 3px solid black;
  background-color: white;
  padding: 8px 20px;
  border-radius: 20px;
  font-size: 15px;
  margin-top: 20px;
  color: black;
  font-family: 'SangSangRock';
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