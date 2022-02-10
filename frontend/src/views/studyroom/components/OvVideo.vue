<template>
	<video :style="state.widthSize" style="height: auto; border-radius: 10px;" class="user-video" ref="el" autoplay/>
</template>


<script>
import { computed, onMounted, reactive, ref } from 'vue';
export default {

	name: 'OvVideo',

	props: {
		streamManager: Object,
    headCount: Number,
	},
  setup (props) {
    const el = ref(null)
    const state = reactive({
      widthSize : computed(()=> {
        return { width: props.headCount+'vw' }
      }),
    })

    onMounted (()=> {
      props.streamManager.addVideoElement(el.value);
    })
    return {
      el, state

    }
  }

};
</script>
