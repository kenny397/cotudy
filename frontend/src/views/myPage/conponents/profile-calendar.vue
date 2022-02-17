<template>
  <div class="calendar-container">
    <el-calendar v-model="state.dayNow" >
      <template #dateCell="{ data }">
        <p
          :class="data.isSelected ? 'is-selected' : ''"
          :id="data.day"
        >
          {{ data.day.split('-').slice(1).join('-') }}
        </p>
      </template>
    </el-calendar>
  </div>
</template>

<style>

  .calendar-container {
    margin: 50px 0px;
    border-radius : 20px;
  }
  .calendar-container .el-calendar-table td.is-selected{
    background: rgba(206, 248, 198, 0.479);
  }
  .calendar-container .el-calendar {
    border-radius : 20px;
    background: white;
  }
</style>

<script>
import { reactive, onBeforeMount, onUpdated} from 'vue';
import axios from 'axios';

export default {
  name: 'profile-calendar',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      dayNow: new Date(),
    })

    onUpdated (() => {
      getAttendance()
    })

    onBeforeMount (() => {
      getAttendance()
    })

    const getAttendance = function () {
      axios.get(`users/calendar?id=${props.user.id}&year=${state.dayNow.getFullYear()}&month=${state.dayNow.getMonth()+1}`)
        .then(res => {
          for (let i in res.data) {
            if(document.getElementById(res.data[i].attendanceDate)){
              document.getElementById(res.data[i].attendanceDate).innerHTML += ' ✅'
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
      // document.getElementById('2022-02-14').innerHTML += '✔️'
      // console.log(state.dayNow)
      // console.log(state.dayNow.getFullYear())
      // console.log(state.dayNow.getMonth()+1)
    }

    return {
      state,
      getAttendance
    }
  }

}
</script>


