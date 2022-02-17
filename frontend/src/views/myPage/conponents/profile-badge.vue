<template>
  <div class="my-badge-container">
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 10" :src="state.badgeImg[1]" alt="">
        <span class="tooltiptext">{{ state.badgename['1'] }}</span>
      </div>
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 30" :src="state.badgeImg[2]" alt="">
        <span class="tooltiptext">{{ state.badgename['2'] }}</span>
      </div>
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 50" :src="state.badgeImg[3]" alt="">
        <span class="tooltiptext">{{ state.badgename['3'] }}</span>
      </div>
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 100" :src="state.badgeImg[4]" alt="">
        <span class="tooltiptext">{{ state.badgename['4'] }}</span>
      </div>
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 200" :src="state.badgeImg[5]" alt="">
        <span class="tooltiptext">{{ state.badgename['5'] }}</span>
      </div>
      <div class="tooltip">
        <img v-if="state.user.entireStudyHour > 500" :src="state.badgeImg[6]" alt="">
        <span class="tooltiptext">{{ state.badgename['6'] }}</span>
      </div>
  </div>
</template>

<style scoped>
  @media (max-width: 1150px) {
    .my-badge-container {
      display: none;
    }
  }

  .my-badge-container {
    width: 35vw;
    height:300px;
    background : #362222;
    padding: 15px 10px;
    border-radius: 20px;
  }

.tooltip {
  position: relative;
  display: inline-block;
  border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
  font-size: 0.8rem;
  visibility: hidden;
  width: 120px;
  background-color: black;
  color: #fff;
  text-align: center;
  padding: 5px 0;
  border-radius: 6px;

  position: absolute;
  z-index: 1;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}

.tooltip .tooltiptext {
  width: 120px;
  top: 100%;
  left: 50%;
  margin-left: -60px; /* Use half of the width (120/2 = 60), to center the tooltip */
}
</style>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-badge',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      badgename: {
        '1': '10시간 공부 달성',
        '2': '30시간 공부 달성',
        '3': '50시간 공부 달성',
        '4': '100시간 공부 달성',
        '5': '200시간 공부 달성',
        '6': '500시간 공부 달성',
      },
      badgeImg: {
        '1' : require('../../../assets/images/badge/badge1.png'),
        '2' : require('../../../assets/images/badge/badge2.png'),
        '3' : require('../../../assets/images/badge/badge3.png'),
        '4' : require('../../../assets/images/badge/badge4.png'),
        '5' : require('../../../assets/images/badge/badge5.png'),
        '6' : require('../../../assets/images/badge/badge6.png'),
      }
    })

    return {
      state
    }
  }

}
</script>


