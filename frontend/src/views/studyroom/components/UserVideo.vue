<template>
<div v-if="streamManager">
	<ov-video
    v-if="headCount"
    :stream-manager="streamManager"
    :head-count="headCount"
    v-bind:class="{isStudying : state.isStudying}"
  />
</div>
</template>

<script>
import OvVideo from './OvVideo';
import { computed, onBeforeMount } from 'vue';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
		session: Object,
    headCount: Number,
	},

	setup (props) {
		const state = {

      clientData : computed(() => getConnectionData())

		}

    onBeforeMount(() => {
    });

    const getConnectionData = function() {
			const { connection } = props.streamManager.stream;
			return JSON.parse(connection.data);
		}


    return { state, }
	},
};
</script>
