<script>
import { Bar } from 'vue3-chart-v2'

export default ({
  name: 'Chart',
  props: {
    weekStudyTime : Array
  },
  extends: Bar,
  mounted () {
    // Overwriting base render method with actual data.
    let temp = new Date()

    let weekDatas = {}
    for (let k=6; k>=0; k--){
      let day = temp.getDate()-k
      weekDatas[temp.getFullYear()+'-'+(temp.getMonth()+1+'').padStart(2,'0')+'-'+ day] = 0
    }

    for (let i in this.weekStudyTime) {
      weekDatas[this.weekStudyTime[i][0]] = this.weekStudyTime[i][1]
    }
    let weekResult = [[],[],[],[],[],[],[]]
    let tmp = 0
    for (let j in weekDatas) {
      weekResult[tmp].push(j.slice(5,10))
      weekResult[tmp].push(weekDatas[j])
      tmp= tmp+1
    }

    this.renderChart({
      labels: [`${weekResult[0][0]}`, `${weekResult[1][0]}`, `${weekResult[2][0]}`, `${weekResult[3][0]}`, `${weekResult[4][0]}`, `${weekResult[5][0]}`, `${weekResult[6][0]}`],
      datasets: [
        {
          label: '나의 공부 시간',
          backgroundColor: '#f87979',
          data: [`${Math.floor(weekResult[0][1]/60)}`,`${Math.floor(weekResult[1][1]/60)}`,`${Math.floor(weekResult[2][1]/60)}`,`${Math.floor(weekResult[3][1]/60)}`,`${Math.floor(weekResult[4][1]/60)}`,`${Math.floor(weekResult[5][1]/60)}`,`${Math.floor(weekResult[6][1]/60)}`]
        }
      ]
    })
  }
})
</script>
