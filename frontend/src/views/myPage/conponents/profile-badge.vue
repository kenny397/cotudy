<template>
  <div class="my-badge-container">

  </div>
</template>

<style scoped>


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
      user: props.user
    })

    return {
      state
    }
  }

}
</script>


