<template>
  <div class="my-goal-container">
    <font-awesome-icon icon="pen-to-square" style="float:right; font-size:30px;"/>

    <div class="my-studytime-area">
      <div>오늘 나의 공부한 시간</div>
      <div class="studytime-wrapper">
        <div>{{state.user.todayStudyTime}}</div>
        <div>05시간 00분</div>
      </div>
      <el-progress
        :text-inside="true"
        :stroke-width="26"
        :percentage="70"
        status="success"
      />

    </div>
    <div class="my-resolution-area">
      <div>나의 다짐</div>
      <div>오늘의 내가 놀면 내일의 내가 그만큼 일한다.</div>
    </div>
  </div>
</template>

<style scoped>
  .my-goal-container {
    background: rgb(255, 255, 255);
    height:250px;
    width:45vw;
    padding: 40px 20px;
    border-radius: 20px;
    text-align: left;
  }
  .my-studytime-area {
    width:45vw;
  }
  @media (max-width: 1150px) {
    .my-studytime-area{
      width:75vw;
    }
  }
  .my-resolution-area {
    margin-top: 40px;
  }

  .my-resolution-area div:first-child{
    font-size: 20px;
    font-weight: 900;
    margin-bottom: 10px;
  }
  .my-studytime-area div:first-child {
    font-size: 17px;
    font-weight: 900;
    margin-bottom: 10px;
  }
  .my-studytime-area .studytime-wrapper {
    display: flex;
    justify-content : space-between;
    flex-direction: row;

  }
  .my-studytime-area .studytime-wrapper div {
    font-size: 23px;
    font-weight: 900;
  }
  .my-studytime-area .studytime-wrapper div:last-child {
    color: var(--el-border-color-lighter);
    font-weight: 200;
  }
</style>
<style>
  :root {
    --el-color-success: #3AC258;
    --el-border-color-lighter: #cccccc;
  }
</style>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-goal',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user
    })

    return {
      state
    }
  }

}
</script>


