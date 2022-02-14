<template>
  <div>
    <h2>{{state.user.nickName}}</h2>
    <div>같이 공부해요~!</div>

    <div class="desc-container">
      <div style="text-align:left;">
        <p style="margin-left: 5px;">
          <font-awesome-icon icon="clock" />
          <strong> 16:23 </strong>
          시간 공부
        </p>
        <p>
          <font-awesome-icon icon="user-group" />
          <strong> 12 </strong>
          명의 친구
        </p>
      </div>
      <div>
        <p>
          <font-awesome-icon icon="school" />
          <span style="color: #8A8F95"> SAMSUNG</span>
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
</style>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-desc',

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


