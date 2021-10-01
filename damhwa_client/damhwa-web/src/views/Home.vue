<template>
  <div class="home">
      <v-date-picker 
        :attributes='calendarData.attributes'
        v-model="calendarData.date" 
      />
  </div>  
</template>

<script>
import dumpData from '../constants/dumpData.json'
import { reactive, watch } from 'vue'
export default {
  name: 'Home',
  setup() {
    var filteredData = []
    const dateData = dumpData.map(data => {
      const tmpDateObj =  new Date(data.regdate)
      return new Date(tmpDateObj.getFullYear(), tmpDateObj.getMonth(), tmpDateObj.getDate())
    })
    
    const calendarData = reactive({
      date: new Date(),
      attributes: [
          {
            dot: 'red',
            dates: dateData,
          },
          {
            dot: 'blue',
            dates: dateData,
          }
        ]
      })

    const checkDateAndFiltering = () => {
        filteredData = dumpData.filter((date) => {
          const dateObj =  new Date(date.regdate)
          const selectedDateObj = new Date(calendarData.date)

          if (isMatched(selectedDateObj, dateObj)) {
            return dateObj
          }
        })
        console.log(filteredData)
    }
    
    const isMatched = (selectedDate, date) => {
      return selectedDate.getFullYear() == date.getFullYear() 
              && selectedDate.getMonth() == date.getMonth() 
              && selectedDate.getDate() == date.getDate()
    }

    watch(() => checkDateAndFiltering())
    
    return { checkDateAndFiltering, calendarData } 
  },
}
</script>
