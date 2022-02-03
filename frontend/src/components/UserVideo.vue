<template>
<div v-if="streamManager">
	<div style="background: black;">
		<ov-video :stream-manager="streamManager"/>
		<div>
			<p>{{ clientData }} </p>
			<input type="text" v-model="myInput">
			<button>{{myStudyTime}}</button>
		</div>
	</div>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
		session: Object,
		fromHigh: Object,
	},
	data () {
		return {
			myStudyTime: "",
			myInput: "",
		}
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	watch: {
		myInput(e) {
			this.$emit('inputUpdate',e)
		},
		
	},

	created () {
		this.sendedMessage()
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},

		sendedMessage () {
			this.session.on('signal:my-chat', (event) => {
				if (event.from.connectionId === this.streamManager.stream.connection.connectionId){
					this.myStudyTime = event.data
				}
			});
		}

		
	},
};
</script>
