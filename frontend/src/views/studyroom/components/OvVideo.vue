<template>
	<video ref="el" style="width: 35vw; height: auto; border-radius: 10px 10px 0px 0px" autoplay/>
</template>

<script>
import { onMounted, ref } from 'vue';
export default {

	name: 'OvVideo',

	props: {
		streamManager: Object,
	},
  setup (props) {
    const el = ref(null)

    onMounted (()=> {
      props.streamManager.addVideoElement(el.value);
    })
    return {
      el,

    }
  }

};
</script>
