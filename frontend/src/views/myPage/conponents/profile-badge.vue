<template>
  <div>

  </div>
</template>

<style scoped>

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
      user: props.user
    })

    return {
      state
    }
  }

}
</script>


