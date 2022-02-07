<template>
<div v-if="streamManager">
	<ov-video :stream-manager="streamManager"/>
</div>
</template>

<script>
import OvVideo from './OvVideo';
import { computed, onMounted,  watch, onBeforeMount } from "vue";

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
		session: Object,
	},

	setup (props, { emit }) {
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
