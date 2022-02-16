<template>
  <div class="my-badge-container">
    <img v-if="state.user.entireStudyHour > 10" :src="state.badgeImg[1]" alt="">
    <img v-if="state.user.entireStudyHour > 30" :src="state.badgeImg[2]" alt="">
    <img v-if="state.user.entireStudyHour > 50" :src="state.badgeImg[3]" alt="">
    <img v-if="state.user.entireStudyHour > 100" :src="state.badgeImg[4]" alt="">
    <img v-if="state.user.entireStudyHour > 200" :src="state.badgeImg[5]" alt="">
    <img v-if="state.user.entireStudyHour > 500" :src="state.badgeImg[6]" alt="">
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


