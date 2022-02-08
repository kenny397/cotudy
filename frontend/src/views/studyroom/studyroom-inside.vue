<template>
  <div>스터디룸 내부화면</div>
  <div>{{ state.mySessionId + '번 방 스터디룸 내부화면' }}</div>
  <div id="session-header">
    <h1 id="session-title">{{ state.mySessionId }}</h1>
    <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
  </div>
  <el-row :gutter="5">
    <el-col
      :span="6"
      :xs="24" :sm="12" :md="8" :xl="6"
    >
      <div
        class="grid-content bg-purple video-sub-container isNotStudying"
        v-bind:class="{isStudying : state.isStudying}"
      >
        <user-video
          :stream-manager="state.publisher"
          :session="state.session"
          @click="updateMainVideoStreamManager(state.publisher)"
        />
        <div>내 공부 시간 : {{state.newStudyTime}}</div>
      </div>
    </el-col>
    <el-col
      :span="6"
      :xs="24" :sm="12" :md="8" :xl="6"
      v-for="sub in state.subscribers"
      :key="sub.stream.connection.connectionId"
    >
      <div
        class="grid-content bg-purple video-sub-container isNotStudying"
        v-bind:class="{isStudying : state.isStudying}"
      >
        <user-video
          :session="state.session"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
        <div :id="sub.stream.connection.connectionId"></div>
      </div>
    </el-col>
  </el-row>

  <!-- <div id="video-full-container" class="col-md-6">
    <div class="video-sub-container">
      <user-video
        :stream-manager="state.publisher"
        :session="state.session"
        v-bind:class="{isStudying : state.isStudying}"
        class="isNotStudying"
        @click.native="updateMainVideoStreamManager(state.publisher)"
      />
      <div>내 공부 시간 : {{state.newStudyTime}}</div>
    </div>
    <div
      v-for="sub in state.subscribers"
      :key="sub.stream.connection.connectionId"
      class="video-sub-container"
    >
      <user-video
        :session="state.session"
        :stream-manager="sub"
        @click.native="updateMainVideoStreamManager(sub)"
      />
      <div :id="sub.stream.connection.connectionId"></div>
    </div>
  </div> -->
  <div id="label-container1"></div>
</template>

<style>
  .isNotStudying {
    border-radius : 20px;
    border : 3px solid red;
  }
  .isStudying {
    border-radius : 20px;
    border : 3px solid green;
  }
  .video-sub-container {
    display: inline-block;
  }
  .el-row {
  margin-bottom: 20px;
  }
  .el-row:last-child {
    margin-bottom: 0;
  }
  .el-col {
    border-radius: 5px;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .grid-content {
    border-radius: 10px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>


<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from './components/UserVideo.vue';
import { reactive, onMounted, onUnmounted, onBeforeMount, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, } from 'vue-router'

// 티쳐블머신

import * as tmPose from '@teachablemachine/pose';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = 'https://' + location.hostname + ':6443';
const OPENVIDU_SERVER_SECRET = 'MY_SECRET';

export default {
	name: 'StudyRoomInside',

	components: {
		UserVideo,
	},

  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      // openvidu
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: '',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),

      // 세션 종료시 clearInterval 용도
      isLeaveSession : undefined,

      // timer
      myStudyTime: 0,
      newStudyTime: computed(() => {
        const date = new Date(null);
        date.setSeconds(state.myStudyTime / 1000);
        const utc = date.toUTCString();
        return utc.substr(utc.indexOf(':') - 2, 8);
      }),
      timer: undefined,
      isStartStopWatch : 1,


      // teachable machine
      isStudying : false,
      model : undefined,
      webcam : undefined,
      ctx : undefined,
      maxPredictions : undefined,
      URL : 'https://teachablemachine.withgoogle.com/models/tCGfolhxR/',
      labelContainer : undefined,



    })

    // life cycles
    onMounted(() => {
      init()
    })

    onUnmounted(() => {
      leaveSession()
    })

    onBeforeMount(() => {
      state.mySessionId = route.params.studyroomId
      store.commit('root/setMenuActiveMenuName', 'home')
      joinSession()
    })

    // my methods
    const timerStart = function() {
      state.timer = setInterval(() => {
        state.myStudyTime += 1000;
      }, 1000);
    }

    const timerStop = function() {
      clearInterval(state.timer);
    }

    // openvidu methods
    const sendMessage = function () {
      if (state.session) {
        state.session.signal({
          data: state.newStudyTime,
          to: [],
          type: 'my-chat'
        })
        .then(() => {
            console.log('Message successfully sent');
        })
        .catch(error => {
            console.error(error);
        })
      } else {
        window.clearInterval(state.isLeaveSession)
      }
    }

    const sendMessageLoop = function () {
      state.isLeaveSession = setInterval(sendMessage, 1000)
    }

    const sendedMessage = function() {

			state.session.on('signal:my-chat', (event) => {
        if (event.from.connectionId !== state.publisher.stream.connection.connectionId){
          const sendedData = document.getElementById(event.from.connectionId)
          sendedData.innerHTML = event.data
        }
			});
		}


    const joinSession = function () {
			// --- Get an OpenVidu object ---

			state.OV = new OpenVidu();

			// --- Init a session ---
			state.session = state.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			state.session.on('streamCreated', ({ stream }) => {
				const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					state.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			state.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// message send 위치
      sendedMessage()

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			getToken(state.mySessionId).then(token => {
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = state.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						state.mainStreamManager = publisher;
						state.publisher = publisher;

						// --- Publish your stream ---

						state.session.publish(state.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			// message sending 위치
      sendMessageLoop()

			window.addEventListener('beforeunload', leaveSession)
		}

    const leaveSession = function() {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (state.session) state.session.disconnect();
      if (state.webcam) state.webcam.stop();

			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
      state.isStudying = false;
      state.model = undefined;
      state.webcam = undefined;
      state.ctx = undefined;
      state.labelContainer = undefined;
      state.maxPredictions = undefined;

			window.removeEventListener('beforeunload', leaveSession);
		}

		const updateMainVideoStreamManager = function(stream) {
			if (state.mainStreamManager === stream) return;
			state.mainStreamManager = stream;
		}

    const getToken = function(mySessionId) {
			return createSession(mySessionId).then(sessionId => createToken(sessionId));
		}

    const createSession = function(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		}

    const createToken = function(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		}


    // teachable machine
    async function init() {
      const modelURL = state.URL + 'model.json';
      const metadataURL = state.URL + 'metadata.json';

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // Note: the pose library adds a tmPose object to your window (window.tmPose)
      state.model = Object.freeze(await tmPose.load(modelURL, metadataURL))
      state.maxPredictions = state.model.getTotalClasses();

      // Convenience function to setup a webcam
      const size = 500;
      const flip = true; // whether to flip the webcam
      state.webcam = new tmPose.Webcam(size, size, flip); // width, height, flip
      await state.webcam.setup(); // request access to the webcam
      await state.webcam.play();
      window.requestAnimationFrame(loop);

      // append/get elements to the DOM
      const canvas = state.webcam.canvas;
      canvas.width = size; canvas.height = size;
      state.ctx = canvas.getContext('2d');
      state.labelContainer = document.getElementById('label-container1');
      for (let i = 0; i < state.maxPredictions; i++) { // and class labels
          state.labelContainer.appendChild(document.createElement('div'));
      }
    }

    async function loop() {
      state.webcam.update(); // update the webcam frame
      await predict();
      window.requestAnimationFrame(loop);
    }

    async function predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { pose, posenetOutput } = await state.model.estimatePose(state.webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await state.model.predict(posenetOutput);
      if ( prediction[0].probability.toFixed(2) > 0.5) {
        if(!state.isStudying) {
          state.isStudying = true
          timerStart()
          state.isStartStopWatch -= 1
        }
      } else {
        state.isStudying = false
        timerStop()
      }
      for (let i = 0; i < state.maxPredictions; i++) {
        const classPrediction =
          prediction[i].className + ': ' + prediction[i].probability.toFixed(2);
          state.labelContainer.childNodes[i].innerHTML = classPrediction;
      }

      // finally draw the poses
      drawPose(pose);
    }

    function drawPose(pose) {
      if (state.webcam.canvas) {
        state.ctx.drawImage(state.webcam.canvas, 0, 0);
        // draw the keypoints and skeleton
        if (pose) {
          const minPartConfidence = 0.5;
          tmPose.drawKeypoints(pose.keypoints, minPartConfidence, state.ctx);
          tmPose.drawSkeleton(pose.keypoints, minPartConfidence, state.ctx);
        }
      }
    }

    return {state, updateMainVideoStreamManager, leaveSession, joinSession, timerStart, timerStop}
  }

}
</script>

