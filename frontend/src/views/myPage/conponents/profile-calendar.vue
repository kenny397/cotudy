<template>
  <div>
    <button @click="test">123123</button>
    <el-calendar v-model="state.dayNow" >
      <template #dateCell="{ data }">
        <p
          :class="data.isSelected ? 'is-selected' : ''"
          :id="data.day"
        >
          {{ data.day.split('-').slice(1).join('-') }}
        </p>
      </template>
    </el-calendar>
  </div>
</template>

<style scoped>

</style>

<script>
import { reactive} from 'vue';
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

    const test = function () {
      document.getElementById('2022-02-14').innerHTML += '✔️'
    }

    return {
      state,
      test
    }
  }

}
</script>


