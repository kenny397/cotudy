<template>
  <Chart v-if="state.weekStudyTime" :weekStudyTime="state.weekStudyTime" />
</template>

<script>
import Chart from './chart.vue'

export default ({
  name: 'App',
  components: {
    Chart
  },
  props: {
		weekStudyTime: Array,
	},
  setup (props) {
    const state = {
      weekStudyTime : props.weekStudyTime
    }

    return {state}
  }
})
</script>
