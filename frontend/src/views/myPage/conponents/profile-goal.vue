<template>
  <div class="my-goal-container">
    <font-awesome-icon icon="pen-to-square" style="float:right; font-size:30px;"/>

    <div class="my-studytime-area">
      <strong>오늘 나의 공부한 시간</strong>
      <div class="studytime-wrapper">
        <div>03시간 27분</div>
        <div>05시간 00분</div>
      </div>

      <div></div>

    </div>
    <div class="my-resolution-area">

    </div>
  </div>
</template>

<style scoped>
  .my-goal-container {
    background: gray;
    height:250px;
    width:50vw;
    padding: 40px 20px;
    border-radius: 20px;
    text-align: left;
  }
  .my-studytime-area {
    width:45vw;
  }

  .my-studytime-area .studytime-wrapper {
    display: flex;
    justify-content : space-between;
    flex-direction: row;

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


