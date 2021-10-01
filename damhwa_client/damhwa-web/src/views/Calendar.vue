<template>
  <div>
      <v-date-picker 
        :attributes='calendarData.attributes'
        v-model="calendarData.date" 
      />
  </div>  
  <div>
    <div v-for="(data, idx) in calendarData.filteredHistories" :key="idx">
      <div>
        <div>{{ data.receiver }}</div>
        <div>{{ data.contents}}</div>
        <button @click="routeToHistoryDetail(data)"> 서신보기 </button>
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
            dot: 'red',
            dates: historyDates,
          },
          {
            dot: 'blue',
            dates: historyDates,
          }
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

    // router methods 말고 setup안에서 해결했습니다.
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
