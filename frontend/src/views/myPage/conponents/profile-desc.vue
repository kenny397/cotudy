<template>
  <div>
    <div class="nickname-area">{{state.user.nickName}}</div>
    <div class="tier-container">
      <img class="tier-image" :src="state.tier[state.user.tier]" alt="">
      <span>{{state.user.tier}}</span>
    </div>
    <div class="desc-container">
      <div style="text-align:left;">
        <p class="margin-left-5px">
          <font-awesome-icon icon="clock" />
          <strong class="margin-left-5px"> {{state.user.entireStudyTime}} </strong>
          공부
        </p>
        <p>
          <font-awesome-icon icon="user-group" />
          <strong class="margin-left-5px">{{state.user.rivalCount}}</strong>
          명의 친구
        </p>
      </div>
      <div>
        <p>
          <font-awesome-icon icon="school" />
          <span class="font-color-light margin-left-5px">{{state.department[state.user.departmentId]}}</span>
        </p>
      </div>
      <div style="margin-top: 30px;">
        <span style="background: #3AC258; padding:10px; border-radius: 15px; color: white; font-size:20px;">
          <font-awesome-icon icon="user-plus" />
        </span>
      </div>
    </div>
  </div>
</template>

<style>
  .desc-container {
    display: flex;
    justify-content : space-between;
    flex-direction: row;

  }
  .desc-container div {
    width: 200px;
  }
  .margin-left-5px {
    margin-left: 5px;
  }
  .font-color-light {
    color: #8A8F95;
  }
  .tier-image {
    width: 50px;
  }
  .tier-container {
    height:50px;
    line-height: 50px;
  }
  .tier-container *{
    vertical-align: middle;
  }
  .nickname-area {
    font-size: 25px;
    font-weight: 900;
    margin-bottom: 10px;
  }
</style>

<script>
import { reactive} from 'vue';
export default {
  name: 'profile-desc',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      department : {
        1: 'SSAFY',
        2: '멀티캠퍼스',
        3: '삼성전자',
        4: '삼성생명',
        5: '삼성SDS',
        10: '소속없음',
      },
      tier: {
        'Iron': require('../../../assets/images/tier/iron.png'),
        'Bronze': require('../../../assets/images/tier/bronze.png'),
        'Silver': require('../../../assets/images/tier/silver.png'),
        'Gold': require('../../../assets/images/tier/gold.png'),
        'Diamond': require('../../../assets/images/tier/diamond.png'),
      }
    })

    return {
      state
    }
  }

}
</script>


