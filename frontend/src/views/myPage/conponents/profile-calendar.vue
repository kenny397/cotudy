<template>
  <div>
    <el-calendar v-model="state.dayNow" />
  </div>
</template>

<style scoped>

</style>

<script>
import { reactive,} from 'vue';
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

    return {
      state
    }
  }

}
</script>


