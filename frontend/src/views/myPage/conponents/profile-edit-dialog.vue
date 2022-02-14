<template>
  <el-button
    round
    style="background: #3AC258; float:right; color:white; height:50px; font-size:20px;"
  >
    회원정보 수정
  </el-button>
</template>

<style>

</style>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-edit-dialog',

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


